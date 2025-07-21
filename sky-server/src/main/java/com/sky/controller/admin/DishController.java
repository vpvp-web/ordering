package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理接口")
public class DishController {
    @Autowired
    private DishService dishService;




    /**
     * 保存菜品信息
     * @param dishDTO 菜品信息
     * @return 返回操作结果
     */
    @PostMapping
    @ApiOperation(value = "保存菜品信息")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("保存菜品信息：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return  Result.success();
    }
    @ApiOperation("分页查询菜品信息")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品信息：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 根据ID删除菜品信息
     * @param ids 菜品ID
     * @return 返回菜品信息
     */
    @DeleteMapping
    @ApiOperation("删除菜品信息")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品信息：{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询菜品信息
     * @param id 菜品ID
     * @return 返回菜品信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询菜品信息")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据ID查询菜品信息：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品信息
     * @param dishDTO 菜品信息
     * @return 返回操作结果
     */
    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 根据分类ID查询菜品信息
     * @param categoryId 分类ID
     * @return 返回菜品信息列表
     */
    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品信息")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("根据分类ID查询菜品信息：{}", categoryId);
        List<Dish> dishList = dishService.list(categoryId);
        return Result.success(dishList);
    }
}
