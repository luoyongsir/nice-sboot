package com.nice.sboot.demo.service;

import com.nice.sboot.demo.BootDemoStrap;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author luoyong-014
 * @date 2018/8/1 16:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootDemoStrap.class)
public class BaseTest {
    protected static final Logger LOG = LoggerFactory.getLogger(BaseTest.class.getName());
}
