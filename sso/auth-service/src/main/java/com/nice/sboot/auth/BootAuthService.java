package com.nice.sboot.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/7/24 18:48
 */
@SpringBootApplication
@ComponentScan("com.nice")
@MapperScan("com.nice.sboot.auth.mapper")
public class BootAuthService {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootAuthService.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
