package com.nice.sboot.auth.mapper;

import com.nice.sboot.auth.entity.SysRole;

/**
 * This interface corresponds to the database table sys_role
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public interface SysRoleMapper {
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
	int insert(SysRole record);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int insertSelective(SysRole record);

	/**
	 * 根据主键查询记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param id
	 * @return
	 */
	SysRole selectByPrimaryKey(Integer id);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(SysRole record);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(SysRole record);
}
