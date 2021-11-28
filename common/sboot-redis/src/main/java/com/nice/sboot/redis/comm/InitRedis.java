package com.nice.sboot.redis.comm;

import com.nice.sboot.redis.template.RedisTemplateJdk;
import com.nice.sboot.redis.template.RedisTemplateJson;
import com.nice.sboot.redis.template.RedisTemplateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 初始化
 * @author luoyong
 * @date 2019/6/26 10:39
 */
@Configuration
public class InitRedis {

	@Autowired
	private LettuceConnectionFactory lettuceConnectionFactory;

	/**
	 * JDK 序列化 Key 和 Value，不做任何限制
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	@Bean
	public <K, V> RedisTemplate<K, V> redisTemplate() {
		RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，JDK 序列化 Value
	 *
	 * @return
	 */
	@Bean
	public RedisTemplateJdk redisTemplateJdk() {
		return new RedisTemplateJdk(lettuceConnectionFactory);
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，FastJson 序列化 Value
	 *
	 * @return
	 */
	@Bean
	public RedisTemplateJson redisTemplateJson() {
		return new RedisTemplateJson(lettuceConnectionFactory);
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，String 序列化 Value
	 * 与 StringRedisTemplate 类似
	 *
	 * @return
	 */
	@Bean
	public RedisTemplateString redisTemplateString() {
		return new RedisTemplateString(lettuceConnectionFactory);
	}
}
