package com.nice.sboot.demo.web.consumers;

import com.nice.sboot.result.Result;
import com.nice.sboot.result.ResultUtil;
import org.springframework.stereotype.Component;

/**
 * @author luoyong
 * @date 2019/6/25 12:15
 */
@Component
public class DemoConsumer {

	/**
	 * 测试
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Result test(Integer pageNum, Integer pageSize) {
		return ResultUtil.ok("请求pageNum是" + pageNum + " ，请求pageSize是" + pageSize);
	}

}
