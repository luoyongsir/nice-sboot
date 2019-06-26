package com.nice.sboot.redis.template;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * fastJson 序列化缓存对象
 * @author luoyong
 * @date 2019/6/26 10:39
 */
public class RedisTemplateJson extends AbstractTemplate {

	public RedisTemplateJson(LettuceConnectionFactory lettuceConnectionFactory) {
		super();
		template.setConnectionFactory(lettuceConnectionFactory);
		// key只用String序列化
		template.setKeySerializer(template.getStringSerializer());
		// fastJson 序列化
		template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
		template.afterPropertiesSet();
		serializer = (RedisSerializer<Object>) template.getDefaultSerializer();
	}

}
