package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 类别映射器
 *
 * @author ilovend
 * @date 2023/04/20
 */
@Mapper
public interface CategoryMapper {

    /**
     * 插入
     *
     * @param category 类别
     */
    void insert(Category category);

    /**
     * 通过条件查询
     *
     * @param category 类别
     * @return {@link Page}<{@link Category}>
     */
    Page<Category> getByCondition(Category category);

    /**
     * 删除通过id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新
     *
     * @param category 类别
     */
    void update(Category category);

    /**
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     */
    void updateStatus(Integer status, Long id);

    List<Category> list(Integer type);
}
