package com.nice.sboot.auth.pojo.dto;

import com.nice.sboot.auth.entity.SysPermission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoyong
 * @date 2019/7/26 14:54
 */
@Getter
@Setter
@ToString
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

}
