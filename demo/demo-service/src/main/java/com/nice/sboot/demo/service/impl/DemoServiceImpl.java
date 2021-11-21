package com.nice.sboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.sboot.demo.entity.Coffee;
import com.nice.sboot.demo.mapper.CoffeeMapper;
import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.demo.service.DemoService;
import com.nice.sboot.result.Result;
import com.nice.sboot.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试
 * @author luoyong
 * @date 2019/6/2 17:22
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

	@Autowired
	private CoffeeMapper coffeeMapper;

	@Override
	public Result findAllWithParam(PageParamBO param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<Coffee> list = coffeeMapper.findAllWithParam(param.getPageNum(), param.getPageSize());
		list.forEach(c -> log.info("Page(1) Coffee {}", c));
		PageInfo page = new PageInfo(list);
		log.info("PageInfo: {}", page);
		return ResultUtil.success(page);
	}

}
