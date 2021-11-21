package com.nice.sboot.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.mapper.SysUserMapper;
import com.nice.sboot.auth.pojo.dto.UserDTO;
import com.nice.sboot.auth.service.PermissionService;
import com.nice.sboot.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 * @author luoyong
 * @date 2019/7/25 10:54
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private PermissionService permissionService;

	@Override
	public SysUser getByUsername(String username) {
		// 如果数据库里手机号、邮箱地址都是唯一的，可以支持通过手机号、邮箱地址登录
		return sysUserMapper.findByUsername(username);
	}

	@Override
	public UserDTO loadUserByUsername(String username) {
		SysUser sysUser = getByUsername(username);
		if (null == sysUser) {
			log.warn("用户{}不存在", username);
			throw new UsernameNotFoundException(username);
		}
		List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(sysUser, userDTO);
		userDTO.setPermissionList(permissionList);
		log.info("登录成功！用户: {}", JSON.toJSONString(userDTO));
		return userDTO;
	}
}
