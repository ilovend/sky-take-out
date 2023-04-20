package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult pagingQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);
}
