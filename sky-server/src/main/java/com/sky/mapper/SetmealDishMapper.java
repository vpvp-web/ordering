package com.sky.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据套餐ID查询套餐菜品ID列表
     * @param dishIds 菜品ID列表
     * @return 套餐菜品ID列表
     */
    List<Long> getSetmealDishIdsByDishIds(List<Long> dishIds);
}
