package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Api(tags = "员工管理")
@Slf4j
public class EmployeeController {

    /**
     * 员工服务
     */
    @Autowired
    @ApiModelProperty(value = "员工服务")
    private EmployeeService employeeService;
    /**
     * jwt配置
     */
    @Autowired
    @ApiModelProperty(value = "jwt配置")
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO 员工登录dto
     * @return {@link Result}<{@link EmployeeLoginVO}>
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }


    /**
     * 注销
     *
     * @return {@link Result}<{@link String}>
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出", notes = "退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 保存
     *
     * @param employeeDTO 员工dto
     * @return {@link Result}
     */
    @PostMapping
    @ApiOperation(value = "保存", notes = "保存")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        log.info("保存成功");
        return Result.success();
    }


    /**
     * 分页查询
     *
     * @param employeePageQueryDTO 员工页面查询dto
     * @return {@link Result}<{@link PageResult}>
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public Result<PageResult> pagingQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pagingQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


}
