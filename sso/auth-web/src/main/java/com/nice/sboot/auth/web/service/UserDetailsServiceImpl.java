package com.nice.sboot.auth.web.service;

import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.pojo.bo.UserBO;
import com.nice.sboot.auth.pojo.dto.UserDTO;
import com.nice.sboot.auth.web.consumers.AuthConsumer;
import com.nice.sboot.base.utils.collect.ListUtil;
import com.nice.sboot.result.CodeMsgEnum;
import com.nice.sboot.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class.getName());

	@Autowired
	private AuthConsumer authConsumer;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Result<UserDTO> result = authConsumer.loadUserByUsername(username);
		if (result.getCode() != CodeMsgEnum.SUCCESS.getCode() || result.getData() == null) {
			LOG.warn("用户{}不存在", username);
			throw new UsernameNotFoundException(username);
		}
		UserDTO data = result.getData();

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
