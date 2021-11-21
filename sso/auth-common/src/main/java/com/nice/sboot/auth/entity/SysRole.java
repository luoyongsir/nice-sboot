package com.nice.sboot.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * This class corresponds to the database table sys_role
 * @author luoyong
 * @date 2019/07/24 18:47
 */
@Getter
@Setter
@ToString
public class SysRole implements Serializable {
    private Integer id;

    /** 角色名称 */
    private String roleName;

    private String roleCode;

    /** 角色描述 */
    private String roleDescription;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private static final long serialVersionUID = -736553213445164044L;

}
