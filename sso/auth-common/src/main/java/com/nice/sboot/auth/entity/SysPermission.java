package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_permission
 * @author luoyong
 * @date 2019/07/24 18:47
 */
@Getter
@Setter
@ToString
public class SysPermission implements Serializable {
	private static final long serialVersionUID = -384836418253372871L;
	private Integer id;
	/** 父ID */
	private Integer pid;
	/** 资源类型（1：菜单，2：按钮，3：操作） */
	private Byte type;
	/** 资源名称 */
	private String name;
	/** 资源标识（或者叫权限字符串） */
	private String code;
	/** 资源URI */
	private String uri;
	/** 序号 */
	private Integer seq;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;

}
