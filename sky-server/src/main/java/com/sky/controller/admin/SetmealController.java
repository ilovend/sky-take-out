package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 添加setmeal<br/>
     * 业务规则：
     * <p>
     * - 套餐名称唯一
     * - 套餐必须属于某个分类
     * - 套餐必须包含菜品
     * - 名称、分类、价格、图片为必填项
     * - 添加菜品窗口需要根据分类类型来展示菜品
     * - 新增的套餐默认为停售状态
     * <p>
     * 接口设计（共涉及到4个接口）：
     * <p>
     * - 根据类型查询分类（已完成）
     * - 根据分类id查询菜品
     * - 图片上传（已完成）
     * - 新增套餐
     *
     * @param setmealDTO setmeal dto
     * @return {@link Result}
     */
    @PostMapping
    @ApiOperation("添加套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * 页面
     *
     * @param setmealPageQueryDTO setmeal页面查询dto
     * @return {@link Result}<{@link PageResult}>
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 通过id
     *
     * @param id id
     * @return {@link Result}<{@link SetmealVO}>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据id查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * 启动或停止
     *
     * @param status 状态
     * @param id     id
     * @return {@link Result}
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐状态")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改套餐状态：{}", id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
