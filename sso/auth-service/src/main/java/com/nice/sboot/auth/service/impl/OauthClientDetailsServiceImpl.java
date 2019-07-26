package com.nice.sboot.auth.service.impl;

import com.nice.sboot.auth.entity.OauthClientDetails;
import com.nice.sboot.auth.mapper.OauthClientDetailsMapper;
import com.nice.sboot.auth.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端详情
 * @author luoyong
 * @date 2019/7/25 17:02
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

	@Autowired
	private OauthClientDetailsMapper oauthClientDetailsMapper;

	@Override
	public OauthClientDetails loadClientByClientId(String clientId) {
		return oauthClientDetailsMapper.selectByPrimaryKey(clientId);
	}
}
