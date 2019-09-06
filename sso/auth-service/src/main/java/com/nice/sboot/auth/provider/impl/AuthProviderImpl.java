package com.nice.sboot.auth.provider.impl;

import com.nice.sboot.auth.provider.AuthProvider;
import com.nice.sboot.auth.service.OauthClientDetailsService;
import com.nice.sboot.auth.service.UserService;
import com.nice.sboot.result.Result;
import com.nice.sboot.result.ResultUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证服务
 * @author luoyong
 * @date 2019/6/27 10:28
 */
@Service
public class AuthProviderImpl implements AuthProvider {

	@Autowired
	private UserService userService;
	@Autowired
	private OauthClientDetailsService oauthClientDetailsService;

	@Override
	public Result loadUserByUsername(String username) {
		return ResultUtil.success(userService.loadUserByUsername(username));
	}

	@Override
	public Result loadClientByClientId(String clientId) {
		return ResultUtil.success(oauthClientDetailsService.loadClientByClientId(clientId));
	}
}
