package com.nice.sboot.scheduler.job.demo;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.nice.sboot.scheduler.consumers.demo.DemoConsumer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试定时任务
 * @author luoyong
 * @date 2019/8/6 20:46
 */
public class DemoJob implements SimpleJob {

	@Autowired
	private DemoConsumer demoConsumer;

	@Override
	public void execute(final ShardingContext shardingContext) {
		demoConsumer.findAllWithParam();
	}
}
