package com.nice.sboot.scheduler.comm;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zookeeper操作工具类
 *
 * @author Luo Yong
 * @date 2017-03-12
 */
public class ZkClient {

	private static final Logger LOG = LoggerFactory.getLogger(ZkClient.class.getName());
	private ZkConfig zkConfig;
	private CuratorFramework curator;

	public ZkClient(ZkConfig zkConfig) {
		this.zkConfig = zkConfig;
	}

	public void init() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMs(), zkConfig.getMaxRetries());
		Builder builder = CuratorFrameworkFactory.builder().connectString(zkConfig.getAddress());
		if (zkConfig.getSessionTimeoutMs() != -1) {
			builder.sessionTimeoutMs(zkConfig.getSessionTimeoutMs());
		}
		if (zkConfig.getConnectionTimeoutMs() != -1) {
			builder.connectionTimeoutMs(zkConfig.getConnectionTimeoutMs());
		}
		curator = builder.retryPolicy(retryPolicy).build();
		// 连接状态监听
		curator.getConnectionStateListenable().addListener((client, newState) -> {
			if (newState == ConnectionState.CONNECTED) {
				// 连接新建
				LOG.warn(" connected with zookeeper ");
			} else if (newState == ConnectionState.LOST) {
				// 连接丢失
				LOG.warn(" lost session with zookeeper ");
			} else if (newState == ConnectionState.RECONNECTED) {
				LOG.warn("reconnected with zookeeper");
				// 连接重连
			}
		});
		curator.start();
	}

	public void stop() {
		curator.close();
	}

	/**
	 * 删除节点，并递归删除其所有子节点
	 *
	 * @param path 节点路径
	 */
	public void delete(String path) {
		delete(path, false);
	}

	/**
	 * 删除节点，并递归删除其所有子节点
	 *
	 * @param path  节点路径
	 * @param guaranteed 是否保证删除
	 */
	public void delete(String path, boolean guaranteed) {
		try {
			if (guaranteed) {
				curator.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
			} else {
				curator.delete().deletingChildrenIfNeeded().forPath(path);
			}
		} catch (Exception e) {
			LOG.error("删除节点出错", e);
		}
	}
}
