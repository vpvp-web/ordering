package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;


public interface SetMealService {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO 对象
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids 套餐ID数组
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据ID查询套餐信息和菜品信息
     * @param id 套餐ID
     * @return SetmealVO 包含套餐和菜品信息的视图对象
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 更新套餐信息
     * @param setmealDTO 套餐数据传输对象
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 启用或停用套餐
     * @param status 套餐状态（1：启用，0：停用）
     * @param id 套餐ID
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
