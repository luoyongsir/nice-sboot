package com.nice.sboot.auth.service;

import com.nice.sboot.auth.entity.OauthClientDetails;

/**
 * 客户端详情
 * @author luoyong
 * @date 2019/7/25 17:01
 */
public interface OauthClientDetailsService {

	/**
	 * 根据客户端id，查询客户端详情
	 * @author luoyong
	 * @date 2019/7/25 17:05
	 * @param clientId
	 * @return
	 */
	OauthClientDetails loadClientByClientId(String clientId);
}
