package com.nice.sboot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/6/2 16:53
 */
@SpringBootApplication
@ComponentScan("com.nice")
@MapperScan("com.nice.sboot.demo.mapper")
public class BootDemoService {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootDemoService.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
