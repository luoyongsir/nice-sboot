package com.nice.sboot.demo.mapper;

import com.nice.sboot.demo.entity.Coffee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CoffeeMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Coffee record);

	int insertSelective(Coffee record);

	Coffee selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Coffee record);

	int updateByPrimaryKey(Coffee record);

	@Select("select * from t_coffee order by id")
	List<Coffee> findAllWithParam(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
