package com.nice.sboot.demo.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

/**
 * @author luoyong
 * @date 2019/6/25 16:05
 */
@Configuration
public class SpringWebConfig {

	public static final String DEFAULT_URL_MAPPINGS = "/*";

	@Bean
	public FilterRegistrationBean encodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setForceEncoding(true);
		encodingFilter.setEncoding(StandardCharsets.UTF_8.displayName());
		FilterRegistrationBean encodingFilterBean = new FilterRegistrationBean();
		encodingFilterBean.setFilter(encodingFilter);
		encodingFilterBean.addUrlPatterns(DEFAULT_URL_MAPPINGS);
		return encodingFilterBean;
	}
}
