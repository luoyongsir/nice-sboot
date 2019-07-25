package com.nice.sboot.auth.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luoyong
 * @date 2019/6/2 14:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseTest.class.getName());

	@Test
	public void baseTest() {
		LOG.info(" SpringRunner BaseTest ");
	}
}
