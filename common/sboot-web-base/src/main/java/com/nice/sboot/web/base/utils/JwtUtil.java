package com.nice.sboot.web.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nice.sboot.base.exception.ExceptionFactory;
import com.nice.sboot.web.base.comm.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT工具类
 * @author: 罗勇
 * @date: 2022-04-21 0:08
 */
@Slf4j
public class JwtUtil {

	public static JSONObject getJwtPayload() {
		JSONObject jsonObject = null;
		try {
			String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getHeader(AuthConstant.JWT_PAYLOAD_KEY);
			if (StringUtils.isNotBlank(payload)) {
				jsonObject = JSON.parseObject(URLDecoder.decode(payload, StandardCharsets.UTF_8.name()));
			}
		} catch (Exception e) {
			log.error("jwt 获取 payload 失败", e);
		}
		return jsonObject;
	}

	public static String getUsername() {
		JSONObject jwtPayload = getJwtPayload();
		if (jwtPayload != null) {
			return jwtPayload.getString(AuthConstant.USERNAME);
		}
		return null;
	}

	public static Integer getUserId() {
		JSONObject jwtPayload = getJwtPayload();
		if (jwtPayload != null) {
			return jwtPayload.getInteger("userId");
		}
		return null;
	}

	public static String getOpenId() {
		JSONObject jwtPayload = getJwtPayload();
		if (jwtPayload != null) {
			String authType = jwtPayload.getString(AuthConstant.AUTH_TYPE);
			if (!"openId".equals(authType)) {
				throw ExceptionFactory.create("非小程序用户");
			}
			// 小程序登录时，token 中的 username 放的是 openId
			return jwtPayload.getString(AuthConstant.USERNAME);
		}
		return null;
	}
}
