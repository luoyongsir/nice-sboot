package com.nice.sboot.auth.service.impl;

import com.nice.sboot.auth.entity.SysPermission;
import com.nice.sboot.auth.entity.SysRolePermission;
import com.nice.sboot.auth.entity.SysUserRole;
import com.nice.sboot.auth.mapper.SysPermissionMapper;
import com.nice.sboot.auth.mapper.SysRolePermissionMapper;
import com.nice.sboot.auth.mapper.SysUserRoleMapper;
import com.nice.sboot.auth.service.PermissionService;
import com.nice.sboot.base.utils.collect.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务
 * @author luoyong
 * @date 2019/7/25 10:50
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class.getName());

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;
	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Override
	public List<SysPermission> findByUserId(Integer userId) {
		// 获取用户角色列表
		List<SysUserRole> sysUserRoleList = sysUserRoleMapper.findByUserId(userId);
		if (ListUtil.isEmpty(sysUserRoleList)) {
			return null;
		}
		List<Integer> roleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
		// 获取角色权限列表
		List<SysRolePermission> rolePermissionList = sysRolePermissionMapper.findByRoleIds(roleIdList);
		if (ListUtil.isEmpty(rolePermissionList)) {
			return null;
		}
		List<Integer> permissionIdList = rolePermissionList.stream().map(SysRolePermission::getPermissionId).distinct()
				.collect(Collectors.toList());
		// 获取权限列表
		return sysPermissionMapper.findByIds(permissionIdList);
	}
}
