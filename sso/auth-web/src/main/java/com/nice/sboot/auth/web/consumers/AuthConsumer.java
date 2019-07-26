package com.nice.sboot.auth.web.consumers;

import com.nice.sboot.auth.provider.AuthProvider;
import com.nice.sboot.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * 调用用户认证服务
 * @author luoyong
 * @date 2019/7/25 16:18
 */
@Component
public class AuthConsumer {

	@Reference
	private AuthProvider authProvider;

	/**
	 * 根据用户名称查找用户
	 * @author luoyong
	 * @date 2019/7/25 16:18
	 * @param username
	 * @return
	 */
	public Result loadUserByUsername(String username) {
		return authProvider.loadUserByUsername(username);
	}

	/**
	 * 根据客户端id，查询客户端详情
	 * @author luoyong
	 * @date 2019/7/25 16:57
	 * @param clientId
	 * @return
	 */
	public Result loadClientByClientId(String clientId) {
		return authProvider.loadClientByClientId(clientId);
	}

}
