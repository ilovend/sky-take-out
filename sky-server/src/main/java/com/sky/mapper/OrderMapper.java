package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     *
     * @param order 订单
     */
    void insert(Orders order);


    Orders getByNumberAndUserId(String orderNumber, Long userId);


    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    Orders getById(Long id);

    Integer countStatus(Integer status);

    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    Double sumByMap(Map map);
}
