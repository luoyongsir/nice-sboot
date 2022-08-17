package com.nice.sboot.gateway.config;

import com.nice.sboot.web.base.comm.AuthConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

/**
 * @author: 罗勇
 * @date: 2022-04-18 21:05
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sboot")
public class SbootConfig {

	private String basic;
	private String clientId;
	private String clientSecret;

	public void setClientId(String clientId) {
		this.clientId = clientId;
		this.basic = null;
	}

	public String getBasic() {
		if (this.basic == null) {
			String auth = String.join(":", clientId, clientSecret);
			this.basic = AuthConstant.BASIC_PREFIX + Base64.getEncoder().encodeToString(auth.getBytes());
		}
		return basic;
	}
}
