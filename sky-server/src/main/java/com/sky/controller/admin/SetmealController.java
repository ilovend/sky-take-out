package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * - 套餐名称唯一
     * - 套餐必须属于某个分类
     * - 套餐必须包含菜品
     * - 名称、分类、价格、图片为必填项
     * - 添加菜品窗口需要根据分类类型来展示菜品
     * - 新增的套餐默认为停售状态
     *
     * 接口设计（共涉及到4个接口）：
     *
     * - 根据类型查询分类（已完成）
     * - 根据分类id查询菜品
     * - 图片上传（已完成）
     * - 新增套餐
     *
     * @param setmealDTO setmeal dto
     * @return {@link Result}
     */
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

}
