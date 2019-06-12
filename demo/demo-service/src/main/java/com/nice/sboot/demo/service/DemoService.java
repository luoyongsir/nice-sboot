package com.nice.sboot.demo.service;

import com.github.pagehelper.PageInfo;
import com.nice.sboot.demo.entity.Coffee;
import com.nice.sboot.demo.mapper.CoffeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试
 * @author luoyong
 * @date 2019/6/2 17:22
 */
@Service
public class DemoService {

	private static final Logger LOG = LoggerFactory.getLogger(DemoService.class.getName());

	@Autowired
	private CoffeeMapper coffeeMapper;

	public void findAllWithParam() {
		List<Coffee> list = coffeeMapper.findAllWithParam(1, 3);
		list.forEach(c -> LOG.info("Page(1) Coffee {}", c));

		list = coffeeMapper.findAllWithParam(2, 3);
		PageInfo page = new PageInfo(list);
		LOG.info("PageInfo: {}", page);
	}

}
