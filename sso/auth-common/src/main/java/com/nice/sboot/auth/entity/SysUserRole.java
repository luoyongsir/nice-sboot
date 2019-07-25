package com.nice.sboot.auth.entity;

import java.io.Serializable;

/**
 * This class corresponds to the database table sys_user_role
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public class SysUserRole implements Serializable {
    private Integer id;

    /** 用户ID */
    private Integer userId;

    /** 角色ID */
    private Integer roleId;

    private static final long serialVersionUID = -656722076262318352L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}