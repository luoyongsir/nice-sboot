package com.nice.sboot.auth.mapper;

import com.nice.sboot.auth.entity.SysUserRole;

import java.util.List;

/**
 * This interface corresponds to the database table sys_user_role
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public interface SysUserRoleMapper {
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
	int insert(SysUserRole record);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int insertSelective(SysUserRole record);

	/**
	 * 根据主键查询记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param id
	 * @return
	 */
	SysUserRole selectByPrimaryKey(Integer id);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(SysUserRole record);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(SysUserRole record);

	/**
	 * 根据用户id查询角色列表
	 * @author luoyong
	 * @date 2019/7/25 10:39
	 * @param userId
	 * @return
	 */
	List<SysUserRole> findByUserId(Integer userId);
}
