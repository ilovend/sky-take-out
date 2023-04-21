package com.sky.controller.admin;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishItemVO;
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

    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result<T> batchDeleteItems(@RequestParam("ids") List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.batchDeleteItems(ids);
        return Result.success();
    }
}
