package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车映射器
 *
 * @author ilovend
 * @date 2023/04/29
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 列表
     *
     * @param shoppingCart 购物车
     * @return {@link List}<{@link ShoppingCart}>
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新数量通过id
     *
     * @param shoppingCart 购物车
     */
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 插入
     *
     * @param shoppingCart 购物车
     */
    void insert(ShoppingCart shoppingCart);

    void deleteByUserId(Long currentId);

    void insertBatch(List<ShoppingCart> shoppingCartList);
}
