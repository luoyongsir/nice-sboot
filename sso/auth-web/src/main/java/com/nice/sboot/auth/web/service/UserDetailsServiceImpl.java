package com.nice.sboot.auth.web.service;

import com.nice.sboot.auth.pojo.bo.UserBO;
import com.nice.sboot.auth.web.consumers.AuthConsumer;
import com.nice.sboot.result.CodeMsgEnum;
import com.nice.sboot.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Result<UserBO> result = authConsumer.loadUserByUsername(username);
		if (result.getCode() != CodeMsgEnum.SUCCESS.getCode() || result.getData() == null) {
			LOG.warn("用户{}不存在", username);
			throw new UsernameNotFoundException(username);
		}
		return result.getData();
	}
}
