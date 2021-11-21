package com.nice.sboot.auth.service.user;

import com.alibaba.fastjson.JSON;
import com.nice.sboot.auth.entity.SysUser;
import com.nice.sboot.auth.service.BaseTest;
import com.nice.sboot.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luoyong
 * @date 2019/7/25 11:53
 */
@Slf4j
public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Test
	public void findByUsername() {
		String username = "admin";
		SysUser sysUser = userService.getByUsername(username);
		if (null == sysUser) {
			log.warn("用户{}不存在", username);
		}
		log.info("用户:{}", JSON.toJSONString(sysUser));
	}

}
