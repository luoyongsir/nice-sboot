package com.nice.sboot.auth.service;

import com.nice.sboot.auth.entity.SysPermission;

import java.util.List;

/**
 * 权限
 * @author luoyong
 * @date 2019/7/25 10:46
 */
public interface PermissionService {

	/**
	 * 根据用户id获取用户权限列表
	 * @author luoyong
	 * @date 2019/7/25 10:46
	 * @param userId
	 * @return
	 */
	List<SysPermission> findByUserId(Integer userId);
}
