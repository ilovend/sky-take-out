package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单Controller
 *
 * @author ilovend
 * @date 2023/04/20
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    /**
     * 菜单服务
     */
    @Autowired
    private DishService dishService;

    /**
     * 保存
     *
     * @param dishDTO 菜单dto
     * @return {@link Result}<{@link T}>
     */
    @PostMapping
    @ApiOperation(value = "添加菜品")
    public Result<T> save(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO 菜页面查询dto
     * @return {@link Result}<{@link PageResult}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> pagingQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pagingQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除条目
     *
     * @param ids id
     * @return {@link Result}<{@link T}>
     */
    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result<T> batchDeleteItems(@RequestParam("ids") List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.batchDeleteItems(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id id
     * @return {@link Result}<{@link DishVO}>
     */
    @GetMapping
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品:{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }


    /**
     * 更新
     *
     * @param dishDTO 菜dto
     * @return {@link Result}<{@link T}>
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result<T> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     * @return {@link Result}
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result updateStatus(@PathVariable("status") Integer status, Integer id) {
        log.info("修改菜品状态:{}", status);
        dishService.updateStatus(status, id);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result listByCategoryId(Long categoryId) {
        log.info("根据分类id查询菜品:{}", categoryId);
        List<Dish> dishVOS = dishService.listByCategoryId(categoryId);
        return Result.success(dishVOS);
    }

}
