package com.nice.sboot.scheduler.consumers.demo;

import com.nice.sboot.scheduler.BootSchedulerStrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author luoyong
 * @date 2019/4/8 16:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootSchedulerStrap.class)
public class DemoConsumerTest {

	@Autowired
	private DemoConsumer demoConsumer;

	@Test
	public void findAllWithParam() {
		demoConsumer.findAllWithParam();
	}
}
