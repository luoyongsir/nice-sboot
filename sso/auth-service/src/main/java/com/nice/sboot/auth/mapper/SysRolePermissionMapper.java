package com.nice.sboot.auth.mapper;

import com.nice.sboot.auth.entity.SysRolePermission;

import java.util.List;

/**
 * This interface corresponds to the database table sys_role_permission
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public interface SysRolePermissionMapper {
	/**
	 * 根据主键删除数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int insert(SysRolePermission record);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int insertSelective(SysRolePermission record);

	/**
	 * 根据主键查询记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param id
	 * @return
	 */
	SysRolePermission selectByPrimaryKey(Integer id);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(SysRolePermission record);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(SysRolePermission record);

	/**
	 * 根据多个角色id查询权限列表
	 * @author luoyong
	 * @date 2019/7/25 10:01
	 * @param roleIds
	 * @return
	 */
	List<SysRolePermission> findByRoleIds(List<Integer> roleIds);
}
