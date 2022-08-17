package com.nice.sboot.gateway.kaptcha;

import com.google.code.kaptcha.Producer;
import com.nice.sboot.web.base.comm.AuthConstant;
import com.nice.sboot.base.result.ResultUtil;
import com.nice.sboot.gateway.config.SbootConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 罗勇
 * @date: 2022-04-17 17:42
 */
@Component
@RequiredArgsConstructor
public class CaptchaHandler implements HandlerFunction<ServerResponse> {

	private final Producer producer;
	private final SbootConfig sbootConfig;
	private final StringRedisTemplate redisTemplate;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		java.util.Map resultMap = new HashMap<String, String>();
		String type = serverRequest.queryParam("type").orElse(StringUtils.EMPTY);
		if (!Objects.equals(type, "wechat")) {
			// 生成验证码
			String code = producer.createText();
			BufferedImage image = producer.createImage(code);
			// 缓存验证码至Redis
			String uuid = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
			redisTemplate.opsForValue().set(AuthConstant.VALIDATE_CODE_PRE + uuid, code, 60, TimeUnit.SECONDS);
			// 转换流信息写出
			String base64 = "data:image/jpg;base64,";
			try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
				ImageIO.write(image, "jpg", os);
				base64 += Base64.getEncoder().encodeToString(os.toByteArray());
			} catch (IOException e) {
				return Mono.error(e);
			}

			resultMap.put("uuid", uuid);
			resultMap.put("img", base64);
		}
		resultMap.put("Authorization", sbootConfig.getBasic());
		return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ResultUtil.success(resultMap)));
	}
}
