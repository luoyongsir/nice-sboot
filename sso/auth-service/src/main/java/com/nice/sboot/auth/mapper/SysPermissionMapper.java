package com.nice.sboot.auth.mapper;

import com.nice.sboot.auth.entity.SysPermission;

import java.util.List;

/**
 * This interface corresponds to the database table sys_permission
 * @author luoyong
 * @date 2019/07/24 18:47
 */
public interface SysPermissionMapper {
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
	int insert(SysPermission record);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int insertSelective(SysPermission record);

	/**
	 * 根据主键查询记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param id
	 * @return
	 */
	SysPermission selectByPrimaryKey(Integer id);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(SysPermission record);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/07/24 18:47
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(SysPermission record);

	/**
	 * 根据多个id查询权限列表
	 * @author luoyong
	 * @date 2019/7/25 9:56
	 * @param ids
	 * @return
	 */
	List<SysPermission> findByIds(List<Integer> ids);
}
