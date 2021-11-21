package com.nice.sboot.demo.service.demo;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.service.BaseTest;
import com.nice.sboot.demo.service.DemoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luoyong
 * @date 2019/6/2 17:49
 */
public class DemoServiceTest extends BaseTest {

	@Autowired
	private DemoService demoService;

	@Test
	public void findAllWithParam() {
		PageParamBO bo = new PageParamBO(1, 3);
		demoService.findAllWithParam(bo);
	}

//	@Test
//	public void testMongo() {
//		demoService.testMongo();
//	}
}
