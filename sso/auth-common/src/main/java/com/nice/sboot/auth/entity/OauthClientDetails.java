package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class corresponds to the database table oauth_client_details
 * @author luoyong
 * @date 2019/07/25 18:38
 */
@Getter
@Setter
@ToString
public class OauthClientDetails implements Serializable {
    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoApprove;

    private static final long serialVersionUID = 755880017521443341L;

}
