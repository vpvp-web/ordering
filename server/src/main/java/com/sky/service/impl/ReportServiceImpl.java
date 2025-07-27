package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;


    /**
     * 营业额统计
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 营业额报表
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //存放begin到end之间的日期
        List<LocalDate> dateList = new ArrayList();
        dateList.add(begin);
        while (!begin.isEqual(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
             turnoverList.add(turnover);
        }




        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO UserStatistics(LocalDate begin, LocalDate end) {
        //存放begin到end之间的日期
        List<LocalDate> dateList = new ArrayList();
        dateList.add(begin);
        while (!begin.isEqual(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //存放新用户数量
        List<Integer> newUserList = new ArrayList<>();
        //存放每天总用户数量
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            //查询每天新用户数量
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map<String,LocalDateTime> map = new HashMap();
            //先查询截至当前的全部用户数量
            map.put("end", endTime);
            Integer totalUserCount = userMapper.countByMap(map);
            //再查询每天新增用户数量
            map.put("begin", beginTime);
            Integer newUserCount = userMapper.countByMap(map);

            newUserList.add(newUserCount);
            totalUserList.add(totalUserCount);
        }
            return UserReportVO
                    .builder()
                    .dateList(StringUtils.join(dateList, ","))
                    .newUserList(StringUtils.join(newUserList, ","))
                    .totalUserList(StringUtils.join(totalUserList, ","))
                    .build();
    }

    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        //存放begin到end之间的日期
        List<LocalDate> dateList = new ArrayList();
        dateList.add(begin);
        while (!begin.isEqual(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询每天订单数量
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer orderCount = orderMapper.countByMap(map);
            map.put("status", Orders.COMPLETED);
            //查询每天订单有效数
            Integer validCount = orderMapper.countByMap(map);
            orderCountList.add(orderCount);
            validCountList.add(validCount);

        }
        Integer tatolorderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer totalValidCount = validCountList.stream().reduce(Integer::sum).get();

        //计算订单完成率
        Double orderCompletionRate = 0.0;
        if (tatolorderCount != 0) {
            orderCompletionRate = ((double) totalValidCount / tatolorderCount);
        }

        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validCountList, ","))
                .totalOrderCount(tatolorderCount)
                .validOrderCount(totalValidCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> saleTop10List = orderMapper.getSalesTop10(beginTime, endTime);
        List<String> nameList = saleTop10List.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        String nameListStr = StringUtils.join(nameList, ",");

        List<Integer> goodsNameList = saleTop10List.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(goodsNameList, ",");

        return SalesTop10ReportVO
                .builder()
                .nameList(nameListStr)
                .numberList(numberList)
                .build();

    }

    @Override
    public void exportBussinessData(HttpServletResponse response) {
        //查询数据库，获取营业数据
        LocalDate datebegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);
        //将日期转换为LocalDateTime
        LocalDateTime beginTime = LocalDateTime.of(datebegin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(dateEnd, LocalTime.MAX);
        BusinessDataVO businessDatavo = workspaceService.getBusinessData(beginTime, endTime);
        //将营业数据写入Excel
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/business_data_template.xlsx");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);
            excel.getSheet("sheet1").getRow(1).getCell(1).setCellValue("时间" + datebegin + "至" + dateEnd);
            excel.getSheet("sheet1").getRow(3).getCell(2).setCellValue(businessDatavo.getTurnover());
            excel.getSheet("sheet1").getRow(3).getCell(4).setCellValue(businessDatavo.getOrderCompletionRate());
            excel.getSheet("sheet1").getRow(3).getCell(6).setCellValue(businessDatavo.getNewUsers());
            excel.getSheet("sheet1").getRow(4).getCell(2).setCellValue(businessDatavo.getValidOrderCount());
            excel.getSheet("sheet1").getRow(4).getCell(4).setCellValue(businessDatavo.getUnitPrice());
            //填充明细数据
            for (int i = 0; i < 30; i++) {
                LocalDate date = datebegin.plusDays(i);
                LocalDateTime dayBegin = LocalDateTime.of(date, LocalTime.MIN);
                LocalDateTime dayEnd = LocalDateTime.of(date, LocalTime.MAX);
                //查询每天的营业数据
                BusinessDataVO dailyData = workspaceService.getBusinessData(dayBegin, dayEnd);
                excel.getSheet("sheet1").getRow(7 + i).getCell(1).setCellValue(date.toString());
                excel.getSheet("sheet1").getRow(7 + i).getCell(2).setCellValue(dailyData.getTurnover());
                excel.getSheet("sheet1").getRow(7 + i).getCell(3).setCellValue(dailyData.getValidOrderCount());
                excel.getSheet("sheet1").getRow(7 + i).getCell(4).setCellValue(dailyData.getOrderCompletionRate());
                excel.getSheet("sheet1").getRow(7 + i).getCell(5).setCellValue(dailyData.getUnitPrice());
                excel.getSheet("sheet1").getRow(7 + i).getCell(6).setCellValue(dailyData.getNewUsers());
            }

            //通过输出流下载
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            excel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
