package com.nice.sboot.demo.web.controller;

import com.nice.sboot.base.comm.MediaTypes;
import com.nice.sboot.demo.entity.Coffee;
import com.nice.sboot.demo.web.consumers.DemoConsumer;
import com.nice.sboot.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "测试类")
public class DemoController {

	@Autowired
	private DemoConsumer demoConsumer;

	@GetMapping("/test")
	@Operation(summary = "测试接口 @author: 罗勇", description = "测试接口description")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaTypes.JSON_UTF_8, schema = @Schema(implementation = Coffee.class)))})
	public Result test(
			@Parameter(description = "页码数，从1开始", example = "1") @RequestParam(name = "pageNum", required = false) Integer pageNum,
			@Parameter(description = "每页数量", example = "10") @RequestParam(name = "pageSize", required = false) Integer pageSize) {
		return demoConsumer.test(pageNum, pageSize);
	}
}
