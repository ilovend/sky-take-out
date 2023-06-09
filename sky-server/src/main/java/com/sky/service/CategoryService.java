package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult pagingQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void updateCategory(CategoryDTO categoryDTO);

    void updateStatus(Integer status, Long id);

    List<Category> list(Integer type);


}
