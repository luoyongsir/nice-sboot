package com.nice.sboot.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/7/24 18:48
 */
@SpringBootApplication
@MapperScan("com.nice.sboot.auth.mapper")
public class BootAuthStrap {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootAuthStrap.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}