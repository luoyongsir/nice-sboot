package com.nice.sboot.redis.comm;

import com.nice.sboot.redis.template.RedisTemplateJdk;
import com.nice.sboot.redis.template.RedisTemplateJson;
import com.nice.sboot.redis.template.RedisTemplateString;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.StringUtils.commaDelimitedListToSet;

/**
 * Redis 初始化
 * @author luoyong
 * @date 2019/6/26 10:39
 */
@Configuration
public class InitRedis {

	/**
	 * redis的节点，必填
	 */
	@Value("${redis.nodes}")
	private String nodes;

	@Value("${redis.maxRedirects:}")
	private Integer maxRedirects;

	@Value("${redis.minIdle:}")
	private Integer minIdle;

	@Value("${redis.maxIdle:}")
	private Integer maxIdle;

	@Value("${redis.maxTotal:}")
	private Integer maxTotal;

	@Value("${redis.maxWaitMillis:}")
	private Integer maxWaitMillis;

	@Value("${redis.testOnBorrow:}")
	private Boolean testOnBorrow;

	@Value("${redis.password:}")
	private String password;

	/**
	 * 初始化 redis cluster 配置，注入bean到Spring
	 *
	 * @return
	 */
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		notNull(getNodes(), " redis.nodes must not null. ");
		Set<String> hostAndPorts = commaDelimitedListToSet(getNodes());
		RedisClusterConfiguration configuration = new RedisClusterConfiguration(hostAndPorts);
		Integer maxRedirects = getMaxRedirects();
		if (maxRedirects != null) {
			configuration.setMaxRedirects(maxRedirects);
		}
		String password = getPassword();
		if (password != null && !StringUtils.isEmpty(password.trim())) {
			configuration.setPassword(RedisPassword.of(password.trim()));
		}
		return configuration;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	public LettuceClientConfiguration lettuceClientConfiguration() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		Integer minIdle = getMinIdle();
		if (minIdle != null) {
			poolConfig.setMinIdle(minIdle);
		}
		Integer maxIdle = getMaxIdle();
		if (maxIdle != null) {
			poolConfig.setMaxIdle(maxIdle);
		}
		Integer maxTotal = getMaxTotal();
		if (maxTotal != null) {
			poolConfig.setMaxTotal(maxTotal);
		}
		Integer maxWaitMillis = getMaxWaitMillis();
		if (maxWaitMillis != null) {
			poolConfig.setMaxWaitMillis(maxWaitMillis);
		}
		Boolean testOnBorrow = getTestOnBorrow();
		if (testOnBorrow != null) {
			poolConfig.setTestOnBorrow(testOnBorrow);
		}

		return LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
	}

	/**
	 * 连接工厂
	 *
	 * @return
	 */
	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory(
			@Qualifier("redisClusterConfiguration") RedisClusterConfiguration redisClusterConfiguration,
			@Qualifier("lettuceClientConfiguration") LettuceClientConfiguration lettuceClientConfiguration) {
		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
	}

	/**
	 * JDK 序列化 Key 和 Value，不做任何限制
	 *
	 * @param lettuceConnectionFactory
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	@Bean
	public <K, V> RedisTemplate<K, V> redisTemplate(
			@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，JDK 序列化 Value
	 *
	 * @param lettuceConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplateJdk redisTemplateJdk(
			@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
		return new RedisTemplateJdk(lettuceConnectionFactory);
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，FastJson 序列化 Value
	 *
	 * @param lettuceConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplateJson redisTemplateJson(
			@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
		return new RedisTemplateJson(lettuceConnectionFactory);
	}

	/**
	 * 限制 Key 只能是 String
	 * String 序列化 Key，String 序列化 Value
	 * 与 StringRedisTemplate 类似
	 *
	 * @param lettuceConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplateString redisTemplateString(
			@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
		return new RedisTemplateString(lettuceConnectionFactory);
	}

	public String getNodes() {
		return nodes;
	}

	public Integer getMaxRedirects() {
		return maxRedirects;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public String getPassword() {
		return password;
	}
}
