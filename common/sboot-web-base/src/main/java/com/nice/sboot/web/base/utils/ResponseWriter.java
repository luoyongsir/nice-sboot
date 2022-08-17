package com.nice.sboot.web.base.utils;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.base.result.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author: 罗勇
 * @date: 2022-04-16 11:52
 */
@Slf4j
public final class ResponseWriter {

	/**
	 * Write.
	 *
	 * @param result  the result
	 * @param response the response
	 * @throws IOException the io exception
	 */
	public static void write(Result result, HttpServletResponse response) throws IOException {
		write(result, response, HttpServletResponse.SC_OK);
	}

	/**
	 * Write.
	 *
	 * @param result  the result
	 * @param response the response
	 * @param status the status
	 * @throws IOException the io exception
	 */
	public static void write(Result result, HttpServletResponse response, int status) throws IOException {
		if (response.isCommitted()) {
			log.info("Did not write to response since already committed");
			return;
		}
		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(result));
		writer.flush();
		writer.close();
	}

}
