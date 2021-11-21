package com.nice.sboot.auth.service.impl;

import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.pojo.bo.UserBO;
import com.nice.sboot.auth.pojo.dto.UserDTO;
import com.nice.sboot.auth.service.UserService;
import com.nice.sboot.base.utils.collect.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户详情服务
 * @author luoyong
 * @date 2019/7/25 10:53
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO data = userService.loadUserByUsername(username);

		Set<GrantedAuthority> authoritySet = new HashSet<>();
		if (ListUtil.isNotEmpty(data.getPermissionList())) {
			for (SysPermission sysPermission : data.getPermissionList()) {
				authoritySet.add(new SimpleGrantedAuthority(sysPermission.getCode()));
			}
		}

		UserBO userBO = new UserBO(data.getUsername(), passwordEncoder.encode(data.getPassword()), authoritySet);
		userBO.setNickname(data.getNickname());
		userBO.setMobile(data.getMobile());
		return userBO;
	}
}
