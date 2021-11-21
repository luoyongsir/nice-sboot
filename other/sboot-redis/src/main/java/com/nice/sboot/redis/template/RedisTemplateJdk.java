package com.nice.sboot.redis.template;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * jdk 自带序列化缓存对象
 * @author luoyong
 * @date 2019/6/26 10:39
 */
public class RedisTemplateJdk extends AbstractTemplate {

	public RedisTemplateJdk(LettuceConnectionFactory lettuceConnectionFactory) {
		super();
		template.setConnectionFactory(lettuceConnectionFactory);
		template.setKeySerializer(template.getStringSerializer());
		template.afterPropertiesSet();
		serializer = (RedisSerializer<Object>) template.getDefaultSerializer();
	}
}
