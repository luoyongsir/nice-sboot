package com.nice.sboot.demo.provider;

import com.nice.sboot.demo.pojo.bo.PageParamBO;
import com.nice.sboot.result.Result;

/**
 * 测试接口
 * @author luoyong
 * @date 2019/6/27 10:16
 */
public interface DemoProvider {

	/**
	 * 分页查询全部数据
	 * @param param
	 * @return
	 */
	Result findAllWithParam(PageParamBO param);
}
