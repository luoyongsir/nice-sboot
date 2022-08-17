package com.nice.sboot.gateway.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Kaptcha 验证码配置
 * @author: 罗勇
 * @date: 2022-04-17 18:54
 */
@Configuration
public class CaptchaConfig {

	private static final String KAPTCHA_BORDER = "kaptcha.border";
	private static final String KAPTCHA_TEXTPRODUCER_FONT_COLOR = "kaptcha.textproducer.font.color";
	private static final String KAPTCHA_TEXTPRODUCER_CHAR_SPACE = "kaptcha.textproducer.char.space";
	private static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";
	private static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";
	private static final String KAPTCHA_TEXTPRODUCER_CHAR_LENGTH = "kaptcha.textproducer.char.length";
	private static final String KAPTCHA_IMAGE_FONT_SIZE = "kaptcha.textproducer.font.size";

	private static final String DEFAULT_IMAGE_WIDTH = "100";
	private static final String DEFAULT_IMAGE_HEIGHT = "40";
	private static final String DEFAULT_IMAGE_LENGTH = "4";
	private static final String DEFAULT_COLOR_FONT = "black";
	private static final String DEFAULT_IMAGE_BORDER = "no";
	private static final String DEFAULT_CHAR_SPACE = "5";
	private static final String DEFAULT_IMAGE_FONT_SIZE = "30";

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put(KAPTCHA_BORDER, DEFAULT_IMAGE_BORDER);
		properties.put(KAPTCHA_TEXTPRODUCER_FONT_COLOR, DEFAULT_COLOR_FONT);
		properties.put(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, DEFAULT_CHAR_SPACE);
		properties.put(KAPTCHA_IMAGE_WIDTH, DEFAULT_IMAGE_WIDTH);
		properties.put(KAPTCHA_IMAGE_HEIGHT, DEFAULT_IMAGE_HEIGHT);
		properties.put(KAPTCHA_IMAGE_FONT_SIZE, DEFAULT_IMAGE_FONT_SIZE);
		properties.put(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, DEFAULT_IMAGE_LENGTH);
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
