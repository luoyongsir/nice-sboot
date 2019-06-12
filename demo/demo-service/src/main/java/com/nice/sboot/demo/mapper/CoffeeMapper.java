package com.nice.sboot.demo.mapper;

import com.nice.sboot.demo.entity.Coffee;

public interface CoffeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Coffee record);

    int insertSelective(Coffee record);

    Coffee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coffee record);

    int updateByPrimaryKey(Coffee record);
}