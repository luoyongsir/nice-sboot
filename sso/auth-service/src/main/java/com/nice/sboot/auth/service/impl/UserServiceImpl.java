package com.nice.sboot.auth.service.impl;

import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.mapper.SysUserMapper;
import com.nice.sboot.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 * @author luoyong
 * @date 2019/7/25 10:54
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser getByUsername(String username) {
		return sysUserMapper.findByUsername(username);
	}
}
