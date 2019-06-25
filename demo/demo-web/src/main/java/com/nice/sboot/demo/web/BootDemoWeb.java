package com.nice.sboot.demo.web;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/6/25 12:09
 */
@SpringBootApplication
@ComponentScan("com.nice")
public class BootDemoWeb {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootDemoWeb.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
