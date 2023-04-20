package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理")
@Slf4j
public class CategoryController {

    @Autowired
    @ApiModelProperty(value = "分类服务")
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param categoryDTO 分类dto
     * @return {@link Result}
     */
    @PostMapping
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类：{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO 类别页面查询dto
     * @return {@link Result}<{@link PageResult}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询分类", notes = "分页查询分类")
    public Result<PageResult> pagingQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pagingQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result deleteCategory(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

}
