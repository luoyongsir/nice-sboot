package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class corresponds to the database table sys_role_permission
 * @author luoyong
 * @date 2019/07/24 18:47
 */
@Getter
@Setter
@ToString
public class SysRolePermission implements Serializable {
    private Integer id;

    /** 角色ID */
    private Integer roleId;

    /** 权限ID */
    private Integer permissionId;

    private static final long serialVersionUID = -545871720371550383L;

}
