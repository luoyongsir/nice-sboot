package com.nice.sboot.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author luoyong
 * @date 2019/6/2 14:33
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class BaseTest {

	@Test
	public void baseTest() {
		log.info(" SpringRunner BaseTest ");
	}
}
