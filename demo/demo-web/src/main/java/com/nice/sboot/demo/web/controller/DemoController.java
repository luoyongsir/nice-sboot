package com.nice.sboot.demo.web.controller;

import com.nice.sboot.base.comm.MediaTypes;
import com.nice.sboot.demo.web.consumers.DemoConsumer;
import com.nice.sboot.result.Result;
import com.nice.sboot.swagger.module.constant.SwaggerConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoyong
 * @date 2019/6/25 12:10
 */
@RestController
@RequestMapping(value = "/demo", produces = MediaTypes.JSON_UTF_8)
@Api(tags = "测试类")
public class DemoController {

	@Autowired
	private DemoConsumer demoConsumer;

	@GetMapping("/test")
	@ApiOperation(value = "测试接口" + SwaggerConst.AUTHOR_LUOYONG, notes = "测试接口notes")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码数，从1开始", dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页数量", dataType = "int")})
	public Result test(@RequestParam(name = "pageNum", required = false) Integer pageNum,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return demoConsumer.test(pageNum, pageSize);
	}
}
