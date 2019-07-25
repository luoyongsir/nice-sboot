package com.nice.sboot.auth.entity;

import java.io.Serializable;

/**
 * This class corresponds to the database table sys_role_permission
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public class SysRolePermission implements Serializable {
    private Integer id;

    /** 角色ID */
    private Integer roleId;

    /** 权限ID */
    private Integer permissionId;

    private static final long serialVersionUID = -545871720371550383L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}