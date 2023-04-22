package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * 员工服务impl
 *
 * @author ilovend
 * @date 2023/04/19
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * 员工映射器
     */
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录dto
     * @return {@link Employee}
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 保存
     *
     * @param employeeDTO 员工dto
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

//        将DTO的属性拷贝到实体类中
        BeanUtils.copyProperties(employeeDTO, employee);

//        设置账号的状态,默认正常状态 1表示正常 0表示锁定
        employee.setStatus(StatusConstant.ENABLE);

//        设置密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long currentId = BaseContext.getCurrentId();

        employee.setCreateUser(currentId);
        employee.setUpdateUser(currentId);

        employeeMapper.insert(employee);

    }

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO 员工分页查询dto
     * @return {@link PageResult}
     */
    @Override
    public PageResult pagingQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        Employee employee = new Employee();
        employee.setName(employeePageQueryDTO.getName());
//        1.PageHelper封装查询条件
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> emp = employeeMapper.pagingQuery(employee);
        return new PageResult(emp.getPages(), emp.getResult());
    }

    /**
     * 更新状态
     *
     * @param status 状态
     * @param id     id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Employee employee = Employee.builder().status(status).id(id).build();
        log.info("employee:{}", employee);
        employeeMapper.update(employee);
    }

    /**
     * 通过id
     *
     * @param id id
     * @return {@link Employee}
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 更新
     *
     * @param employeeDTO 员工dto
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }


}
