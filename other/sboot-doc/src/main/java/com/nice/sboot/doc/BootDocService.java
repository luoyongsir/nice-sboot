package com.nice.sboot.doc;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务启动类
 * @author luoyong
 * @date 2019/6/2 16:53
 */
@SpringBootApplication
public class BootDocService {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootDocService.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
