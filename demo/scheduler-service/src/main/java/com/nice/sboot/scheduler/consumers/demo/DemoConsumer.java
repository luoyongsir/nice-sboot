package com.nice.sboot.scheduler.consumers.demo;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.provider.DemoProvider;
import com.nice.sboot.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Demo 定时任务
 * @author luoyong
 * @date 2019/8/4 15:28
 */
@Service
public class DemoConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(DemoConsumer.class.getName());
	@Reference
	private DemoProvider demoProvider;

	/**
	 * 分页查询全部数据
	 */
	public void findAllWithParam() {
		Result result = demoProvider.findAllWithParam(new PageParamBO(1, 10));
		if (result != null && result.getData() != null) {
			LOG.warn("定時任務处理结束，处理数据：" + result.getData());
		}
	}

}
