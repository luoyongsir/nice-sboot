package com.nice.sboot.auth.service.impl;

import com.nice.sboot.auth.entity.OauthClientDetails;
import com.nice.sboot.auth.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 客户端细节服务
 * @author luoyong
 * @date 2019/7/25 10:53
 */
@Service
@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Autowired
	private OauthClientDetailsService oauthClientDetailsService;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OauthClientDetails data = oauthClientDetailsService.loadClientByClientId(clientId);
		if (data == null) {
			log.warn("客户端{}不存在", clientId);
			throw new ClientRegistrationException(clientId);
		}
		BaseClientDetails details = new BaseClientDetails(clientId, data.getResourceIds(), data.getScope(),
				data.getAuthorizedGrantTypes(), data.getAuthorities(), data.getWebServerRedirectUri());
		details.setClientSecret(data.getClientSecret());
		details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(data.getAutoApprove()));
		details.setAccessTokenValiditySeconds(data.getAccessTokenValidity());
		details.setRefreshTokenValiditySeconds(data.getRefreshTokenValidity());
		return details;
	}
}
