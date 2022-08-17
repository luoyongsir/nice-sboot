package com.nice.sboot.gateway.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nice.sboot.web.base.comm.AuthConstant;
import com.nice.sboot.base.result.CodeMsgEnum;
import com.nice.sboot.gateway.config.SbootConfig;
import com.nice.sboot.gateway.utils.ResponseUtil;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

/**
 * 安全拦截全局过滤器
 * <p>
 * 善后一些无关紧要的工作，在 ResourceServerManager#check 鉴权之后执行
 * @author: 罗勇
 * @date: 2022-04-17 17:52
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

	private final StringRedisTemplate stringRedisTemplate;
	private final SbootConfig sbootConfig;

	@SneakyThrows
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		// 不是正确的的JWT不做解析处理
		String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_KEY);
		if (StringUtils.isBlank(token)) {
			request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION_KEY, sbootConfig.getBasic())
					.build();
			exchange = exchange.mutate().request(request).build();
			return chain.filter(exchange);
		} else if (!StringUtils.startsWithIgnoreCase(token, AuthConstant.JWT_PREFIX)) {
			return chain.filter(exchange);
		}

		// 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
		token = StringUtils.replaceIgnoreCase(token, AuthConstant.JWT_PREFIX, Strings.EMPTY);
		String payload = String.valueOf(JWSObject.parse(token).getPayload());
		JSONObject jsonObject = JSON.parseObject(payload);
		String jti = jsonObject.getString(AuthConstant.JWT_JTI);
		Boolean isBlack = stringRedisTemplate.hasKey(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
		if (isBlack) {
			return ResponseUtil.writeError(response, CodeMsgEnum.TOKEN_ACCESS_FORBIDDEN);
		}

		// TODO 移动端的接口，临时验证
		String path = request.getURI().getPath();
		PathMatcher pathMatcher = new AntPathMatcher();
		if (pathMatcher.match(AuthConstant.API_WXAPP_PATTERN, path)) {
			// 验证userId
			Integer userId = jsonObject.getInteger("userId");
			if (userId == null) {
				return ResponseUtil.writeError(response, CodeMsgEnum.HTTP_403);
			}
		}

		// 存在token且不是黑名单，request写入JWT的载体信息
		request = exchange.getRequest().mutate()
				.header(AuthConstant.JWT_PAYLOAD_KEY, URLEncoder.encode(payload, "UTF-8")).build();
		exchange = exchange.mutate().request(request).build();
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
