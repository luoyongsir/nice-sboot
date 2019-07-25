package com.nice.sboot.auth.service;

import com.nice.sboot.auth.entity.SysUser;

/**
 * 用户服务
 * @author luoyong
 * @date 2019/7/25 10:54
 */
public interface UserService {

	SysUser getByUsername(String username);
}
