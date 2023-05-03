package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/*
 * 编写service的接口和实现
 * 1.制作Excel的模板
 * 2.把制作好的Excel模板拷贝到项目中
 * 3.编写controller
 *  1.该方法不需要有返回值
 *  2.方法的参数需要接收reponse对象
 * 4.结束时间应该昨天
 *  1.获取过去三天的起始时间和结束时间
 *  2.调用工作台查询今日的service方法完成概览数据的查询
 *  3.通过类加载模板对象
 *  4.通过io创建XSSFWorkbook对象
 *  5.通过Excel对象获取页签对象
 *  6.通过页签获取对应的行对象
 *  7.通过行对象获取单元格并设置值
 *  8.循环30次查询每一天的运营数据
 *  9.通过页签获取对应的行对象
 *  10.通过行对象获取单元格对象并设置值
 *  11.通过response对象把Excel
 *
 * */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /*
         * 营业额:当日已完成订单的总数量
         * 有效订单:当日已完成订单的数量
         * 订单完成率:有效订单数/总订单数
         * 平均客单价:营业额/有效订单数
         * 新增用户:当日新增用户的数量
         * */

        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

        Integer totalOrderCount = orderMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        Integer validOrderCount = orderMapper.countByMap(map);
        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0 && validOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover / validOrderCount;
        }
        Integer newUsers = userMapper.countByMap(map);
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        Integer waitingOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        map.put("status", null);
        Integer allOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();

    }

    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
