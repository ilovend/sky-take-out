package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * 报表管理
 *
 * @author ilovend
 * @date 2023/05/02
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "报表管理")
public class ReportController {
    /**
     * 报告服务
     */
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link Result}<{@link TurnoverReportVO}>
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation(value = "营业额统计", notes = "营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        TurnoverReportVO turnoverReportVO = reportService.getTurnover(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link Result}<{@link UserReportVO}>
     */
    @GetMapping("/userStatistics")
    @ApiOperation(value = "用户统计", notes = "用户统计")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        UserReportVO userReportVO = reportService.getUserStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link Result}<{@link OrderReportVO}>
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation(value = "订单统计", notes = "订单统计")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        OrderReportVO orderReportVO = reportService.getOrderStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 热销商品
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link Result}<{@link SalesTop10ReportVO}>
     */
    @GetMapping("/top10")
    @ApiOperation(value = "热销商品", notes = "热销商品")
    public Result<SalesTop10ReportVO> top10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        SalesTop10ReportVO salesTop10ReportVO = reportService.getSalesTop10(begin, end);
        return Result.success(salesTop10ReportVO);
    }

    @GetMapping("/export")
    @ApiOperation("导出运营数据报表")
    public void export(HttpServletResponse response) {
        reportService.exportBusinessData(response);
    }
}