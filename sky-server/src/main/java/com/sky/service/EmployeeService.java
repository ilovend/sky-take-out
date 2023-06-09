package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 保存
     *
     * @param employeeDTO 员工dto
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 页面查询
     *
     * @param employeePageQueryDTO 员工页面查询dto
     * @return {@link PageResult}
     */
    PageResult pagingQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 通过id
     *
     * @param id id
     * @return {@link Employee}
     */
    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
