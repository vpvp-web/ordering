package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    /**
     * 保存菜品口味
     * @param dishDTO 菜品信息
     */
    public void saveWithFlavor(DishDTO dishDTO);


    /**
     * 分页查询菜品信息
     * @param dishPageQueryDTO 分页查询对象
     * @return 返回分页结果
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);
}
