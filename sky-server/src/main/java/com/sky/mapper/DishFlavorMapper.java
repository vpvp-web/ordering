package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     *
     * @param flavors 菜品口味列表
     * @return 菜品口味列表
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品ID删除菜品口味
     * @param dishIds 菜品ID
     */

    void deleteByDishIds(List<Long> dishIds);
}
