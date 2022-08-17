package com.nice.sboot.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.base.result.CodeMsgEnum;
import com.nice.sboot.base.result.ResultUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author: 罗勇
 * @date: 2022-04-17 17:56
 */
public class ResponseUtil {

	public static Mono<Void> writeError(ServerHttpResponse response, CodeMsgEnum codeMsgEnum) {
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.getHeaders().set("Access-Control-Allow-Origin", "*");
		response.getHeaders().set("Cache-Control", "no-cache");
		String body = JSON.toJSONString(ResultUtil.result(codeMsgEnum));
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
	}

}
