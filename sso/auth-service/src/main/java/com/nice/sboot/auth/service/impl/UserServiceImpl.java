package com.nice.sboot.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.mapper.SysUserMapper;
import com.nice.sboot.auth.pojo.dto.UserDTO;
import com.nice.sboot.auth.service.PermissionService;
import com.nice.sboot.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务
 * @author luoyong
 * @date 2019/7/25 10:54
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private PermissionService permissionService;

	@Override
	public SysUser getByUsername(String username) {
		return sysUserMapper.findByUsername(username);
	}

	@Override
	public UserDTO loadUserByUsername(String username) {
		SysUser sysUser = getByUsername(username);
		if (null == sysUser) {
			LOG.warn("用户{}不存在", username);
			return null;
		}
		List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(sysUser, userDTO);
		userDTO.setPermissionList(permissionList);
		LOG.info("登录成功！用户: {}", JSON.toJSONString(userDTO));
		return userDTO;
	}
}
