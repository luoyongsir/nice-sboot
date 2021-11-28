package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_user
 * @author luoyong
 * @date 2019/07/24 18:47
 */
@Getter
@Setter
@ToString
public class SysUser implements Serializable {
	private static final long serialVersionUID = 417687107621748588L;
	private Integer id;
	/** 账号 */
	private String username;
	/** 密码 */
	private String password;
	/** 昵称 */
	private String nickname;
	/** 邮箱 */
	private String email;
	/** 状态（0：锁定，1：解锁） */
	private Byte status;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;

}
