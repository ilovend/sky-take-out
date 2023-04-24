package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(Dish dish);

    Dish getById(Long id);

    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    void updateStatus(Integer status, Integer id);

    List<Dish> list(Dish build);

    List<Dish> getBySetmealId(Long id);
}
