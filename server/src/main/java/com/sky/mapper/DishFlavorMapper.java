package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    void deleteByDishIds(@Param("dishIds") List<Long> dishIds);


    /**
     * 根据菜品ID查找菜品口味
     * @param id 菜品ID
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);
}
