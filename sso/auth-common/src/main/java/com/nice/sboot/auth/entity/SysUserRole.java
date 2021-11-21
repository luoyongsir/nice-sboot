package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class corresponds to the database table sys_user_role
 * @author luoyong
 * @date 2019/07/24 18:47
 */
@Getter
@Setter
@ToString
public class SysUserRole implements Serializable {
    private Integer id;

    /** 用户ID */
    private Integer userId;

    /** 角色ID */
    private Integer roleId;

    private static final long serialVersionUID = -656722076262318352L;

}
