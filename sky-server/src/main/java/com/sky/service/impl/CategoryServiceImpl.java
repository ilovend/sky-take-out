package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 类别服务impl
 *
 * @author ilovend
 * @date 2023/04/20
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    /**
     * 类别映射器
     */
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 添加类别
     *
     * @param categoryDTO 类别dto
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        log.info("添加分类：{}", categoryDTO);
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);
    }


    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO 类别页面查询dto
     * @return {@link PageResult}
     */
    @Override
    public PageResult pagingQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类：{}", categoryPageQueryDTO);
        Category category = new Category();
        category.setName(categoryPageQueryDTO.getName());
        category.setType(categoryPageQueryDTO.getType());
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> cate = categoryMapper.getByCondition(category);
        return new PageResult(cate.getTotal(), cate.getResult());
    }

    /**
     * 删除通过id
     *
     * @param id id
     */
    @Override
    public void deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryMapper.deleteById(id);
    }

    /**
     * 更新类别
     *
     * @param categoryDTO 类别dto
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        log.info("更新分类状态：{}", id);
        categoryMapper.updateStatus(status, id);
    }

    /**
     * 列表
     *
     * @param type 类型
     * @return {@link List}<{@link Category}>
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

}
