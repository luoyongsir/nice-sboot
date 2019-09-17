package com.nice.sboot.scheduler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 服务启动类 *
 * @author luoyong
 * @date 2019/9/17 21:03
 */
@SpringBootApplication
@ImportResource({"xml/scheduler-config.xml"})
public class BootSchedulerStrap {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BootSchedulerStrap.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
