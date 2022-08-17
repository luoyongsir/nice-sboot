package com.nice.sboot.gateway.security;

import com.nice.sboot.web.base.comm.AuthConstant;
import com.nice.sboot.base.result.CodeMsgEnum;
import com.nice.sboot.gateway.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

/**
 * 资源服务器配置
 * @author: 罗勇
 * @date: 2022-04-17 17:57
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "sboot.security")
public class ResourceServerConfig {

	private final ResourceServerManager resourceServerManager;

	@Setter
	private List<String> ignoreUrls;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
				// 本地获取公钥
				.publicKey(rsaPublicKey());
		http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
		http.authorizeExchange().pathMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()])).permitAll()
				.anyExchange().access(resourceServerManager).and().exceptionHandling()
				// 处理未授权
				.accessDeniedHandler(accessDeniedHandler())
				// 处理未认证
				.authenticationEntryPoint(authenticationEntryPoint()).and().csrf().disable();
		return http.build();
	}

	/**
	 * 自定义未授权响应
	 */
	@Bean
	ServerAccessDeniedHandler accessDeniedHandler() {
		return (exchange, denied) -> {
			Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
					.flatMap(response -> ResponseUtil.writeError(response, CodeMsgEnum.HTTP_401));
			return mono;
		};
	}

	/**
	 * token无效或者已过期自定义响应
	 */
	@Bean
	ServerAuthenticationEntryPoint authenticationEntryPoint() {
		return (exchange, e) -> {
			Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
					.flatMap(response -> ResponseUtil.writeError(response, CodeMsgEnum.TOKEN_INVALID_OR_EXPIRED));
			return mono;
		};
	}

	/**
	 * @link https://blog.csdn.net/qq_24230139/article/details/105091273
	 * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
	 * 需要把jwt的Claim中的authorities加入
	 * 方案：重新定义权限管理器，默认转换器JwtGrantedAuthoritiesConverter
	 */
	@Bean
	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.JWT_AUTHORITIES_KEY);

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

	/**
	 * 本地获取JWT验签公钥
	 */
	@SneakyThrows
	@Bean
	public RSAPublicKey rsaPublicKey() {
		StringBuilder bud = new StringBuilder();
		Resource resource = new ClassPathResource("public.key");
		try (InputStream is = resource.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			String str;
			while ((str = reader.readLine()) != null) {
				bud.append(str);
			}
		}

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(bud.toString()));

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		return rsaPublicKey;
	}

}
