package com.nice.sboot.scheduler.comm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author luoyong
 * @date 2019/2/23 12:16
 */
@Component
public class SystemInit {

	@Value("${elastic.job.namespace}")
	private String path;

	@Autowired
	private ZkClient zkClient;

	@PostConstruct
	private void init() {
		// 启动前先清空节点配置
		if (StringUtils.startsWith(path, "/")) {
			zkClient.delete(path);
		} else {
			zkClient.delete("/" + path);
		}
	}
}
