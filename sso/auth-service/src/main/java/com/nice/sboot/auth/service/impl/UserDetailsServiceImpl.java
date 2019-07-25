package com.nice.sboot.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.pojo.bo.UserBO;
import com.nice.sboot.auth.service.PermissionService;
import com.nice.sboot.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户详情服务
 * @author luoyong
 * @date 2019/7/25 10:53
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.getByUsername(username);
		if (null == sysUser) {
			LOG.warn("用户{}不存在", username);
			throw new UsernameNotFoundException(username);
		}
		List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(permissionList)) {
			for (SysPermission sysPermission : permissionList) {
				authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
			}
		}

		UserBO userBO = new UserBO(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()), authorityList);
		userBO.setNickname(sysUser.getNickname());

		LOG.info("登录成功！用户: {}", JSON.toJSONString(userBO));

		return userBO;
	}
}
