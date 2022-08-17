package com.nice.sboot.gateway.security;

import com.nice.sboot.web.base.comm.AuthConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 网关自定义鉴权管理器
 * @author: 罗勇
 * @date: 2022-04-17 18:18
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	private final RedisTemplate redisTemplate;

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
		ServerHttpRequest request = authorizationContext.getExchange().getRequest();
		// 预检请求放行
		if (request.getMethod() == HttpMethod.OPTIONS) {
			return Mono.just(new AuthorizationDecision(true));
		}
		PathMatcher pathMatcher = new AntPathMatcher();
		String method = request.getMethodValue();
		String path = request.getURI().getPath();
		// RESTFul接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html
		String restfulPath = method + ":" + path;

		// 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证
		String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_KEY);
		if (StringUtils.isBlank(token) || !StringUtils.startsWithIgnoreCase(token, AuthConstant.JWT_PREFIX)) {
			return Mono.just(new AuthorizationDecision(false));
		}
		if (pathMatcher.match(AuthConstant.API_PATTERN, path)) {
			// 移动端请求需认证暂不鉴权，直接放行
			return Mono.just(new AuthorizationDecision(true));
		}

		/**
		 * 鉴权开始
		 *
		 * 缓存取 [URL权限-角色集合] 规则数据
		 * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
		 */
		Map<String, Set<String>> urlPermRolesRules = redisTemplate.opsForHash()
				.entries(AuthConstant.URL_PERM_ROLES_KEY);

		// 根据请求路径获取有访问权限的角色列表

		// 拥有访问权限的角色
		Set<String> authorizedRoles = new HashSet<>();
		// 是否需要鉴权，默认未设置拦截规则不需鉴权
		boolean requireCheck = false;

		for (Map.Entry<String, Set<String>> permRoles : urlPermRolesRules.entrySet()) {
			String perm = permRoles.getKey();
			if (pathMatcher.match(perm, restfulPath)) {
				authorizedRoles.addAll(permRoles.getValue());
				if (requireCheck == false) {
					requireCheck = true;
				}
			}
		}
		// 没有设置拦截规则放行
		if (requireCheck == false) {
			return Mono.just(new AuthorizationDecision(true));
		}

		// 判断JWT中携带的用户角色是否有权限访问
		Mono<AuthorizationDecision> authorizationDecisionMono = mono.filter(Authentication::isAuthenticated)
				.flatMapIterable(Authentication::getAuthorities).map(GrantedAuthority::getAuthority).any(authority -> {
					String roleCode = authority.substring(AuthConstant.AUTHORITY_PREFIX.length()); // 用户的角色
					if (AuthConstant.ROOT_ROLE_CODE.equals(roleCode)) {
						return true; // 如果是超级管理员则放行
					}
					boolean hasAuthorized =
							!CollectionUtils.isEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
					return hasAuthorized;
				}).map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
		return authorizationDecisionMono;
	}
}
