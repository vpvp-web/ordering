package com.sky.mapper;


import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    /**
     * 根据套餐ID查询套餐菜品ID列表
     * @param dishIds 菜品ID列表
     * @return 套餐菜品ID列表
     */
    List<Long> getSetmealDishIdsByDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐菜品关系
     * @param setmealDishes 套餐菜品列表
     */
   void insertBatch( List<SetmealDish> setmealDishes);


    /**
     * 根据套餐ID删除套餐菜品关系
     * @param ids 套餐ID列表
     */
    void deleteBySetmealId(@Param("ids") List<Long> ids);

    /**
     * 根据套餐ID查询套餐菜品信息
     * @param id 套餐ID
     * @return 套餐菜品列表
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);





}
