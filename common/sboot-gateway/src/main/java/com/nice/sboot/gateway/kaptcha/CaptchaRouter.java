package com.nice.sboot.gateway.kaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 图片验证码路由
 * @author: 罗勇
 * @date: 2022-04-17 18:37
 */
@Configuration
public class CaptchaRouter {

	@Value("${server.servlet.context-path:/api/}")
	private String contextPath;

	@Bean
	public RouterFunction<ServerResponse> routeFunction(CaptchaHandler captchaHandler) {
		return RouterFunctions.route(
				RequestPredicates.GET(contextPath + "captcha").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
				captchaHandler::handle);
	}

}
