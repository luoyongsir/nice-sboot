package com.nice.sboot.auth.example.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luoyong
 * @date 2019/7/26 17:53
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {// @formatter:off

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl("http://localhost:8080/logout")
				// 其余所有请求全部需要鉴权认证
				.and().authorizeRequests().anyRequest().authenticated()
				// 由于使用的是JWT，我们这里不需要csrf
				.and().csrf().disable();
    }
}// @formatter:on
