package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pagingQuery(DishPageQueryDTO dishPageQueryDTO);

    void batchDeleteItems(List<Long> ids);
}
