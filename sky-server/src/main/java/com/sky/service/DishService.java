package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 保存菜品口味
     * @param dishDTO 菜品信息
     */
    void saveWithFlavor(DishDTO dishDTO);


    /**
     * 分页查询菜品信息
     * @param dishPageQueryDTO 分页查询对象
     * @return 返回分页结果
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据ID删除菜品信息
     * @param ids 菜品ID列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询菜品信息
     * @param id 菜品ID
     * @return 返回菜品信息
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息和口味
     * @param dishDTO 菜品信息
     */
    void updateWithFlavor(DishDTO dishDTO);
}
