package com.nice.sboot.demo.provider.impl;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.provider.DemoProvider;
import com.nice.sboot.demo.service.DemoService;
import com.nice.sboot.result.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 示例
 * @author luoyong
 * @date 2019/6/27 10:28
 */
@Service
public class DemoProviderImpl implements DemoProvider {

	@Autowired
	private DemoService demoService;

	@Override
	public Result findAllWithParam(PageParamBO param) {
		return demoService.findAllWithParam(param);
	}
}
