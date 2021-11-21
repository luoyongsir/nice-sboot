package com.nice.sboot.redis.template;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * String序列化缓存对象
 * @author luoyong
 * @date 2019/6/26 10:39
 */
public class RedisTemplateString extends AbstractTemplate {

	public RedisTemplateString(LettuceConnectionFactory lettuceConnectionFactory) {
		super();
		template.setConnectionFactory(lettuceConnectionFactory);
		template.setDefaultSerializer(template.getStringSerializer());
		template.afterPropertiesSet();
		serializer = (RedisSerializer<Object>) template.getDefaultSerializer();
	}
}
