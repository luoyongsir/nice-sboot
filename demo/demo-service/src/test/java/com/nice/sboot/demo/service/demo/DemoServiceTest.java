package com.nice.sboot.demo.service.demo;

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
		demoService.findAllWithParam();
	}
}
