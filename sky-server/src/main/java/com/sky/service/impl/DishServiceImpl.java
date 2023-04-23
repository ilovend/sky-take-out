package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 菜服务impl
 *
 * @author ilovend
 * @date 2023/04/21
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    /**
     * 菜品映射器
     */
    @Autowired
    private DishMapper dishMapper;
    /**
     * 菜味道映射器
     */
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 和味道一起保存
     *
     * @param dishDTO 菜dto
     */
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
//        利用useGeneratedKeys="true" keyProperty="id"自动生成主键
        dishMapper.insert(dish);
//        获取insert语句生成的主键值
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO 菜页面查询dto
     * @return {@link PageResult}
     */
    @Override
    public PageResult pagingQuery(DishPageQueryDTO dishPageQueryDTO) {
        Dish dish = new Dish();

        BeanUtils.copyProperties(dishPageQueryDTO, dish);
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        if (dishPageQueryDTO.getCategoryId() != null) {
            dish.setCategoryId(dishPageQueryDTO.getCategoryId().longValue());
        }
        Page<DishVO> page = dishMapper.pageQuery(dish);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除条目
     *
     * @param ids id
     */
    @Override
    public void batchDeleteItems(List<Long> ids) {
//        判断当前菜品是否能够删除-- - 是否存在起售中的菜品？？
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
//        判断当前菜品是否能够删除-- - 是否被套餐关联了？？
        List<Long> setmealIds = setmealDishMapper.geSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
//                当前菜品被套餐关联了不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
//            删除菜单表
            dishMapper.deleteById(id);
//            删除口味表
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * 通过id获取味道
     *
     * @param id id
     * @return {@link DishVO}
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public void updateStatus(Integer status, Integer id) {
        dishMapper.updateStatus(status, id);
    }

    /**
     * 按类别id列表
     *
     * @param categoryId 类别id
     * @return {@link List}<{@link Dish}>
     */
    @Override
    public List<Dish> listByCategoryId(Long categoryId) {
        Dish build = new Dish().builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(build);
    }


}
