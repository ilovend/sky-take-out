package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

public interface DishService {

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pagingQuery(DishPageQueryDTO dishPageQueryDTO);

    void batchDeleteItems(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    void updateStatus(Integer status, Integer id);


    List<Dish> listByCategoryId(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
