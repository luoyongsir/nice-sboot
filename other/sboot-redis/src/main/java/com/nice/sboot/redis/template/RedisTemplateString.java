package com.nice.sboot.redis.template;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Objects;

/**
 * String序列化缓存对象
 * @author luoyong
 * @date 2019/6/26 10:39
 */
public class RedisTemplateString extends AbstractTemplate {

	private static final String OK = "OK";
	private static final String ONE = "1";
	/**
	 * 锁的默认过期时间，单位毫秒
	 */
	private static final long DEFAULT_LOCK_TIME_OUT = 30 * 1000L;
	private static final long DEFAULT_TRY_LOCK_TIME_OUT = 1000L;
	public RedisTemplateString(LettuceConnectionFactory lettuceConnectionFactory) {
		super();
		template.setConnectionFactory(lettuceConnectionFactory);
		template.setDefaultSerializer(template.getStringSerializer());
		template.afterPropertiesSet();
		serializer = (RedisSerializer<Object>) template.getDefaultSerializer();
	}

	/**
	 * 尝试获取分布式锁，默认过期时间30秒
	 *
	 * @param lockKey      锁
	 * @return 是否获取成功
	 */
	public boolean lock(String lockKey) {
		return lock(lockKey, null);
	}

	/**
	 * 尝试获取分布式锁
	 *
	 * @param lockKey      锁
	 * @param expireTimeMs 过期时间
	 * @return 是否获取成功
	 */
	public boolean lock(String lockKey, Long expireTimeMs) {
		return lock(lockKey, ONE, expireTimeMs);
	}

	/**
	 * 尝试获取分布式锁
	 *
	 * @param lockKey          锁
	 * @param expireTimeMs     过期时间
	 * @param tryLockTimeoutMs 尝试获取锁的时间，如果该时间段内未获取到锁，则返回false
	 * @return 是否获取成功
	 */
	public boolean lock(String lockKey, Long expireTimeMs, long tryLockTimeoutMs) {
		return lock(lockKey, ONE, expireTimeMs, tryLockTimeoutMs);
	}

	/**
	 * 尝试获取分布式锁
	 *
	 * @param lockKey      锁
	 * @param lockValue    能区分请求的唯一标识
	 * @param expireTimeMs 过期时间
	 * @return 是否获取成功
	 */
	public boolean lock(String lockKey, String lockValue, Long expireTimeMs) {
		if (expireTimeMs == null || expireTimeMs <= 0L) {
			expireTimeMs = DEFAULT_LOCK_TIME_OUT;
		}
		final long expire = expireTimeMs;
		final byte[] keyBytes = template.getStringSerializer().serialize(lockKey);
		final byte[] valBytes = template.getStringSerializer().serialize(lockValue);
		String result = template.execute((RedisCallback<String>) con -> {
			Object nativeConnection = con.getNativeConnection();
			if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
				RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
				return clusterAsyncCommands.getStatefulConnection().sync()
						.set(keyBytes, valBytes, SetArgs.Builder.nx().px(expire));
			} else if (nativeConnection instanceof RedisAsyncCommands) {
				RedisAsyncCommands redisAsyncCommands = (RedisAsyncCommands) nativeConnection;
				return redisAsyncCommands.getStatefulConnection().sync()
						.set(keyBytes, valBytes, SetArgs.Builder.nx().px(expire));
			} else {
				RedisCommands commands = (RedisCommands) nativeConnection;
				return commands.set(keyBytes, valBytes, SetArgs.Builder.nx().px(expire));
			}
		});
		return OK.equals(result);
	}

	/**
	 * 尝试获取分布式锁
	 *
	 * @param lockKey          锁
	 * @param lockValue        能区分请求的唯一标识
	 * @param expireTimeMs     过期时间
	 * @param tryLockTimeoutMs 尝试获取锁的时间，如果该时间段内未获取到锁，则返回false
	 * @return 是否获取成功
	 */
	public boolean lock(String lockKey, String lockValue, Long expireTimeMs, long tryLockTimeoutMs) {
		if (tryLockTimeoutMs <= 0L) {
			tryLockTimeoutMs = DEFAULT_TRY_LOCK_TIME_OUT;
		}
		long timeOut = System.currentTimeMillis() + tryLockTimeoutMs;
		// 在超时之前，循环尝试拿锁
		while (System.currentTimeMillis() < timeOut) {
			if (lock(lockKey, lockValue, expireTimeMs)) {
				return true;
			} else {
				// 等待50ms后继续尝试获取锁
				try {
					Thread.sleep(50L);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return false;
	}

	/**
	 * 释放分布式锁
	 *
	 * @param lockKey    锁
	 * @return 是否释放成功
	 */
	public boolean unLock(String lockKey) {
		Long res = del(lockKey);
		return Objects.equals(res, 1L);
	}

	/**
	 * 释放分布式锁
	 *
	 * @param lockKey    锁
	 * @param lockValue  能区分请求的唯一标识
	 * @return 是否释放成功
	 */
	public boolean unLock(String lockKey, String lockValue) {
		String val = get(lockKey);
		if (val != null && val.equals(lockValue)) {
			Long res = del(lockKey);
			return Objects.equals(res, 1L);
		}
		return false;
	}
}
