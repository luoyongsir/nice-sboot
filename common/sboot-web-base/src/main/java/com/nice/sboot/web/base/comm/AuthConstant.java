package com.nice.sboot.web.base.comm;

/**
 * @author: 罗勇
 * @date: 2022-04-17 11:51
 */
public interface AuthConstant {

	/**
	 * 认证请求头key
	 */
	String AUTHORIZATION_KEY = "Authorization";

	/**
	 * JWT令牌前缀
	 */
	String JWT_PREFIX = "Bearer ";

	/**
	 * Basic认证前缀
	 */
	String BASIC_PREFIX = "Basic ";

	/**
	 * JWT载体key
	 */
	String JWT_PAYLOAD_KEY = "payload";

	/**
	 * JWT ID 唯一标识
	 */
	String JWT_JTI = "jti";

	/**
	 * JWT ID 唯一标识
	 */
	String JWT_EXP = "exp";

	/**
	 * 黑名单token前缀
	 */
	String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

	String CLIENT_ID_KEY = "client_id";

	/**
	 * JWT存储权限前缀
	 */
	String AUTHORITY_PREFIX = "ROLE_";

	/**
	 * JWT存储权限属性
	 */
	String JWT_AUTHORITIES_KEY = "authorities";

	String GRANT_TYPE_KEY = "grant_type";

	String REFRESH_TOKEN_KEY = "refresh_token";

	/**
	 * 认证身份标识
	 */
	String AUTH_TYPE = "authType";

	String USERNAME = "username";

	String API_WXAPP_PATTERN = "/api/wxapp/**";
	String API_PATTERN = "/api/**";

	/**
	 * 验证码key前缀
	 */
	String VALIDATE_CODE_PRE = "validate:code:";

	/**
	 * 短信验证码key前缀
	 */
	String SMS_CODE_PRE = "sms:code:";

	/**
	 * 超级管理员角色编码
	 */
	String ROOT_ROLE_CODE = "ROOT";

	/**
	 * [ {接口路径:[角色编码]},...]
	 */
	String URL_PERM_ROLES_KEY = "user:perm_roles:url";

	/**
	 * [{按钮权限:[角色编码]},...]
	 */
	String BTN_PERM_ROLES_KEY = "user:perm_roles:btn";

}
