package com.nice.sboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.sboot.demo.entity.Coffee;
import com.nice.sboot.demo.mapper.CoffeeMapper;
import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.service.DemoService;
import com.nice.sboot.result.Result;
import com.nice.sboot.result.ResultUtil;
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
public class DemoServiceImpl implements DemoService {

	private static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class.getName());

	@Autowired
	private CoffeeMapper coffeeMapper;

	@Override
	public Result findAllWithParam(PageParamBO param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<Coffee> list = coffeeMapper.findAllWithParam(param.getPageNum(), param.getPageSize());
		list.forEach(c -> LOG.info("Page(1) Coffee {}", c));
		PageInfo page = new PageInfo(list);
		LOG.info("PageInfo: {}", page);
		return ResultUtil.ok(page);
	}

}
