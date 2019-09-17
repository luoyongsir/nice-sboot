package com.nice.sboot.scheduler.comm;

import com.dangdang.ddframe.job.api.ShardingContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 记录定时任务执行日志
 *
 * @author luoyong
 * @date 2019/7/11 10:26
 */
@Aspect
@Component
public class JobLogAspect {

	private static final Logger LOG = LoggerFactory.getLogger(JobLogAspect.class.getName());

	@Pointcut("execution(* com.nice.sboot.scheduler.job..*.*(..))")
	public void log() {
	}

	@Before("log()")
	public void beforeJob(JoinPoint joinPoint) {
		ShardingContext sc = (ShardingContext) joinPoint.getArgs()[0];
		LOG.info(" 定时任务开始 TaskId： " + sc.getTaskId());
	}

	@After("log()")
	public void afterJob(JoinPoint joinPoint) {
		ShardingContext sc = (ShardingContext) joinPoint.getArgs()[0];
		LOG.info(" 定时任务结束 TaskId： " + sc.getTaskId());
	}
}
