package com.nice.sboot.demo.service;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.result.Result;

/**
 * 测试
 * @author luoyong-014
 * @date 2019/7/3 14:30
 */
public interface DemoService {

	/**
	 * 分页查找
	 * @author luoyong-014
	 * @date 2019/7/3 14:30
	 * @param param
	 * @return
	 */
	Result findAllWithParam(PageParamBO param);

	/**
	 * 测试MongoDB
	 * @author luoyong-014
	 * @date 2019/7/3 14:30
	 */
	void testMongo();
}
