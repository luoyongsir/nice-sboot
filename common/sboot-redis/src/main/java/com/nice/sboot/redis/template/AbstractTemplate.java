package com.nice.sboot.redis.template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 缓存基类<br />
 * key全部限定为 String 类型 <br/>
 * @author luoyong
 * @date 2019/6/26 10:39
 */
public abstract class AbstractTemplate {

	protected RedisTemplate<String, Object> template = new RedisTemplate<>();
	protected RedisSerializer<Object> serializer;

	/**
	 * 通过key获取缓存
	 *
	 * @param key
	 * @return
	 */
	public <T> T get(final String key) {
		return (T) template.execute((RedisCallback<Object>) con -> {
			byte[] value = con.get(template.getStringSerializer().serialize(key));
			return serializer.deserialize(value);
		});
	}

	/**
	 * 通过key获取缓存，Optional封装
	 *
	 * @param key
	 * @return
	 */
	public <T> Optional<T> getOptional(final String key) {
		return Optional.ofNullable(get(key));
	}

	/**
	 * 保存缓存
	 *
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Object value) {
		template.execute((RedisCallback<Object>) con -> {
			con.set(template.getStringSerializer().serialize(key), serializer.serialize(value));
			return null;
		});
	}

	/**
	 * 保存缓存
	 *
	 * @param key
	 * @param value
	 * @param seconds 缓存时间（秒）
	 */
	public void set(final String key, final Object value, final long seconds) {
		template.execute((RedisCallback<Object>) con -> {
			con.setEx(template.getStringSerializer().serialize(key), seconds, serializer.serialize(value));
			return null;
		});
	}

	/**
	 * 保存缓存
	 *
	 * @param key
	 * @param value
	 * @param duration 缓存时长
	 * @param unit     时长单位
	 */
	public void set(final String key, final Object value, final long duration, final TimeUnit unit) {
		set(key, value, unit.toSeconds(duration));
	}

	/**
	 * Redis map specific operations working on a hash.
	 *
	 * @return
	 */
	public <HK, HV> HashOperations<String, HK, HV> opsForHash() {
		return template.opsForHash();
	}

	/**
	 * 删除缓存
	 *
	 * @param keys
	 * @return The number of keys that were removed.
	 */
	public Long del(final String... keys) {
		if (keys == null || keys.length == 0) {
			return 0L;
		}
		final byte[][] byteKeys = new byte[keys.length][];
		for (int i = 0; i < keys.length; i++) {
			byteKeys[i] = template.getStringSerializer().serialize(keys[i]);
		}
		return template.execute((RedisCallback<Long>) con -> con.del(byteKeys));
	}

	/**
	 * 删除缓存
	 *
	 * @param pattern
	 * @return The number of keys that were removed.
	 */
	public Long delByPattern(final String pattern) {
		Set<String> keys = keys(pattern);
		if (CollectionUtils.isEmpty(keys)) {
			return 0L;
		}
		final byte[][] byteKeys = new byte[keys.size()][];
		int i = 0;
		for (String key : keys) {
			byteKeys[i++] = template.getStringSerializer().serialize(key);
		}
		return template.execute((RedisCallback<Long>) con -> con.del(byteKeys));
	}

	/**
	 * 通过pattern获得keys
	 *
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(final String pattern) {
		if (pattern != null && !"".equals(pattern)) {
			return template.keys(pattern + "*");
		}
		return new HashSet<>();
	}

	/**
	 * 设置缓存key的有效时间
	 *
	 * @param key     缓存key
	 * @param timeout 缓存时长
	 * @param unit    时间单位
	 * @return
	 */
	public Boolean expire(String key, final long timeout, final TimeUnit unit) {
		return template.expire(key, timeout, unit);
	}

	/**
	 * 设置缓存key的过期时间
	 *
	 * @param key
	 * @param date
	 * @return
	 */
	public Boolean expireAt(String key, final Date date) {
		return template.expireAt(key, date);
	}

	public RedisConnectionFactory getConnectionFactory() {
		return template.getConnectionFactory();
	}

	public RedisTemplate<String, Object> getTemplate() {
		return template;
	}
}
