package com.nice.sboot.demo.mapper;

import com.nice.sboot.demo.entity.Coffee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * This interface corresponds to the database table T_COFFEE
 * @author luoyong
 * @date 2019/06/22 17:40
 */
public interface CoffeeMapper {
	/**
	 * 根据主键删除数据库记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param record
	 * @return
	 */
	int insert(Coffee record);

	/**
	 * 新增记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param record
	 * @return
	 */
	int insertSelective(Coffee record);

	/**
	 * 根据主键查询记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param id
	 * @return
	 */
	Coffee selectByPrimaryKey(Long id);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Coffee record);

	/**
	 * 根据主键修改数据库记录
	 * @author luoyong
	 * @date 2019/06/22 17:40
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Coffee record);

	/**
	 * 根据参数分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Select("select * from t_coffee order by id")
	List<Coffee> findAllWithParam(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
