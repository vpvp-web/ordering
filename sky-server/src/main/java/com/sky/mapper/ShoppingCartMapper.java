package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 动态条件查询购物车列表
     * @param shoppingCar
     * @return
     */

    List<ShoppingCart> list(ShoppingCart shoppingCar);

    /**
     * 更新购物车数量
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateById(ShoppingCart shoppingCart);

    /**
     * 插入购物车
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time,number)" +
      "VALUE (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime}, #{number})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     * @param shoppingCart
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void delete(ShoppingCart shoppingCart);

    /**
     * 根据ID删除购物车
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

}
