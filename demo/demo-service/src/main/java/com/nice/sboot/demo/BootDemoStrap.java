package com.nice.sboot.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/6/2 16:53
 */
@SpringBootApplication
@MapperScan("com.nice.sboot.demo.mapper")
public class BootDemoStrap {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootDemoStrap.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
