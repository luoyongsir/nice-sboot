package com.nice.sboot.demo.web.consumers;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.provider.DemoProvider;
import com.nice.sboot.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author luoyong
 * @date 2019/6/25 12:15
 */
@Component
public class DemoConsumer {

	@Reference
	private DemoProvider demoProvider;

	/**
	 * 测试
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Result test(Integer pageNum, Integer pageSize) {
		return demoProvider.findAllWithParam(new PageParamBO(pageNum, pageSize));
	}

}
