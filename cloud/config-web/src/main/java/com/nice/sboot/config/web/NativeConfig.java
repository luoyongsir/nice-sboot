package com.nice.sboot.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.environment.NativeEnvironmentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyong
 * @date 2019/5/30 11:45
 */
@Component
public class NativeConfig {

	private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	@Autowired
	private NativeEnvironmentRepository nativeEnvironmentRepository;

	@PostConstruct
	private void init() throws IOException {
		String[] arr = nativeEnvironmentRepository.getSearchLocations();
		List<String> list = new ArrayList<>();
		for (String path : arr) {
			// 配置的目录
			list.add(path);
			// 配置目录的子目录
			Resource[] resources = this.resolver.getResources(path + "*");
			for (Resource resource : resources) {
				list.add(path + resource.getFilename() + "/");
			}
		}
		nativeEnvironmentRepository.setSearchLocations(list.toArray(new String[list.size()]));
	}
}
