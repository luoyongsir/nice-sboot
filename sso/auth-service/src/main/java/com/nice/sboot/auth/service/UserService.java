package com.nice.sboot.auth.service;

import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.pojo.bo.UserBO;

/**
 * 用户服务
 * @author luoyong
 * @date 2019/7/25 10:54
 */
public interface UserService {

	/**
	 * 根据名称获取SysUser
	 * @param username
	 * @return
	 */
	SysUser getByUsername(String username);

	/**
	 * 根据名称获取UserBO
	 * @param username
	 * @return
	 */
	UserBO loadUserByUsername(String username);
}
