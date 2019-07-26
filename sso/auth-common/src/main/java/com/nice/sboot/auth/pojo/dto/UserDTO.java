package com.nice.sboot.auth.pojo.dto;

import com.nice.sboot.auth.entity.SysPermission;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoyong-014
 * @date 2019/7/26 14:54
 */
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 3658756987107691494L;
	/** 账号 */
	private String username;

	/** 密码 */
	private String password;

	/** 昵称 */
	private String nickname;

	/** 手机号 */
	private String mobile;

	private List<SysPermission> permissionList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<SysPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<SysPermission> permissionList) {
		this.permissionList = permissionList;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserDTO{");
		sb.append("username='").append(username).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", nickname='").append(nickname).append('\'');
		sb.append(", mobile='").append(mobile).append('\'');
		sb.append(", permissionList=").append(permissionList);
		sb.append('}');
		return sb.toString();
	}
}
