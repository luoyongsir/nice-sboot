package com.nice.sboot.auth.provider.impl;

import com.nice.sboot.auth.provider.AuthProvider;
import com.nice.sboot.auth.service.impl.UserDetailsServiceImpl;
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
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public Result loadUserByUsername(String username) {
		return ResultUtil.ok(userDetailsService.loadUserByUsername(username));
	}
}
