package com.nice.sboot.scheduler.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过spring 读取配置文件
 *
 * @author Luo Yong
 * @date 2017-03-12
 */
@Configuration
public class ZkConfig {

	@Value("${zookeeper.address}")
	private String address;
	@Value("${zookeeper.maxRetries:3}")
	private int maxRetries;
	@Value("${zookeeper.baseSleepTimeMs:1000}")
	private int baseSleepTimeMs;
	@Value(("${zookeeper.sessionTimeoutMs:-1}"))
	private int sessionTimeoutMs;
	@Value("${zookeeper.connectionTimeoutMs:-1}")
	private int connectionTimeoutMs;

	@Bean(initMethod = "init", destroyMethod = "stop")
	public ZkClient zkClient() {
		return new ZkClient(this);
	}

	public String getAddress() {
		return address;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public int getBaseSleepTimeMs() {
		return baseSleepTimeMs;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}
}
