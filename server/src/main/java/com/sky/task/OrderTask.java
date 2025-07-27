package com.sky.task;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;


    /**
     * 定时任务：处理超时订单
     * 该方法可以根据实际需求设置定时执行的频率
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟执行一次
    public void processTimeoutOrders() {
        log.info("处理超时订单...", LocalDateTime.now());
        // 查询超时订单并进行相应的处理

        LocalDateTime time= LocalDateTime.now().plusMinutes(-15); // 假设超时订单为15分钟前未支付的订单
        List<Orders> orderlist = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        if(!orderlist.isEmpty()){
            for (Orders orders : orderlist) {
                orders.setStatus(Orders.CANCELLED); // 将订单状态改为已取消
                orders.setCancelReason("订单超时未支付，已自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }

    }

    /**
     * 定时任务：处理派送中订单
     * 该方法可以根据实际需求设置定时执行的频率
     */
    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行
    public void processDeliveryOrders() {
        log.info("处理派送中订单...", LocalDateTime.now());
        // 查询派送中订单并进行相应的处理

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60); // 假设派送中订单为1小时前未完成的订单
        List<Orders> orderlist = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if(!orderlist.isEmpty()){
            for (Orders orders : orderlist) {
                orders.setStatus(Orders.COMPLETED); // 将订单状态改为已完成
                orderMapper.update(orders);
            }
        }

    }
}
