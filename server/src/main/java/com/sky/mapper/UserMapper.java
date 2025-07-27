package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Map;


@Mapper
public interface UserMapper {


    /**
     * 根据openid查询用户
     * @param openid 微信用户的openid
     * @return User 用户信息
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入新用户
     * @param user 用户信息
     */
    void insert(User user);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return User 用户信息
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 统计指定时间段内的用户数量
        * @param map 包含开始时间和结束时间的Map
     * @return Integer 用户数量
     */
    Integer countByMap(Map<String,LocalDateTime> map);
}
