package com.nice.sboot.auth.pojo.bo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * 大部分时候直接用User，不必扩展
 * @author luoyong
 * @date 2019/7/25 11:01
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserBO extends User implements Serializable {

	private static final long serialVersionUID = 6903341955087107114L;
	/** 举个例子，用户昵称 */
	private String nickname;

	/** 举个例子，假设我们想增加一个字段，这里我们增加一个mobile表示手机号 */
	private String mobile;

	public UserBO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public UserBO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}
