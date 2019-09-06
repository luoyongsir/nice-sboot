package com.nice.sboot.auth.pojo.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 大部分时候直接用User，不必扩展
 * @author luoyong
 * @date 2019/7/25 11:01
 */
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (o == null || getClass() != o.getClass()) {return false;}
		if (!super.equals(o)) {return false;}
		UserBO bo = (UserBO) o;
		return Objects.equals(nickname, bo.nickname) && Objects.equals(mobile, bo.mobile);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nickname, mobile);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserBO{");
		sb.append("nickname='").append(nickname).append('\'');
		sb.append(", mobile='").append(mobile).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
