package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 提交订单
     *
     * @param orderSubmitDTO 订单提交数据传输对象
     * @return 订单提交结果视图对象
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO);

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单查询
     *
     * @param page     当前页码
     * @param pageSize 每页显示数量
     * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     * @return 分页结果
     */
    PageResult pageQueryUser(int page, int pageSize, Integer status);

    /**
     * 查询订单详情
     *
     * @param id 订单ID
     * @return 订单视图对象
     */
    OrderVO details(Long id);

    /**
     * 用户取消订单
     *
     * @param id
     */
    void userCancelById(Long id) throws Exception;

    /**
     * 再来一单
     *
     * @param id
     */
    void repetition(Long id);

    /**
     * 条件搜索订单
     *
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     *
     * @return 订单统计视图对象
     */
    OrderStatisticsVO statistics();

    /**
     * 接单
     *
     * @param ordersConfirmDTO 订单确认数据传输对象
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;
    /**
     * 派单
     *
     * @param id 订单ID
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param id 订单ID
     */
    void complete(Long id);
}