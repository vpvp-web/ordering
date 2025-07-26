package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;


public interface ReportService {
    /**
     * 营业额统计
     * @param begin 开始时间
     * @param end 结束时间
     * @return 营业额报表
     */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计
     * @param begin 开始时间
     * @param end 结束时间
     */
    UserReportVO UserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计
     * @param begin 开始时间
     * @param end 结束时间
     * @return 订单报表
     */
    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end);
}
