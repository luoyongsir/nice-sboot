package com.nice.sboot.auth.provider;

import com.nice.sboot.result.Result;

/**
 * 用户认证服务
 * @author luoyong
 * @date 2019/6/27 10:16
 */
public interface AuthProvider {

	/**
	 * 根据用户名称查找用户
	 * @param username
	 * @return
	 */
	Result loadUserByUsername(String username);

	/**
	 * 根据客户端id，查询客户端详情
	 * @param clientId
	 * @return
	 */
	Result loadClientByClientId(String clientId);
}
