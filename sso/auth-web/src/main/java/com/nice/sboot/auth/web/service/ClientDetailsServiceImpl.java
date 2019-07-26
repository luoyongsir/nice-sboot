package com.nice.sboot.auth.web.service;

import com.nice.sboot.auth.entity.OauthClientDetails;
import com.nice.sboot.auth.web.consumers.AuthConsumer;
import com.nice.sboot.result.CodeMsgEnum;
import com.nice.sboot.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ClientDetailsServiceImpl implements ClientDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(ClientDetailsServiceImpl.class.getName());

	@Autowired
	private AuthConsumer authConsumer;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Result<OauthClientDetails> result = authConsumer.loadClientByClientId(clientId);
		if (result.getCode() != CodeMsgEnum.SUCCESS.getCode() || result.getData() == null) {
			LOG.warn("客户端{}不存在", clientId);
			throw new ClientRegistrationException(clientId);
		}
		OauthClientDetails data = result.getData();
		BaseClientDetails details = new BaseClientDetails(clientId, data.getResourceIds(), data.getScope(),
				data.getAuthorizedGrantTypes(), data.getAuthorities(), data.getWebServerRedirectUri());
		details.setClientSecret(data.getClientSecret());
		details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(data.getAutoApprove()));
		details.setAccessTokenValiditySeconds(data.getAccessTokenValidity());
		details.setRefreshTokenValiditySeconds(data.getRefreshTokenValidity());
		return details;
	}
}
