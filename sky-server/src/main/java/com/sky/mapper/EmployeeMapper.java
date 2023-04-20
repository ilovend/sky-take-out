package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 员工映射器
 *
 * @author ilovend
 * @date 2023/04/20
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 得到用户名
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return {@link Employee}
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入
     *
     * @param employee 员工
     */
    void insert(Employee employee);

    /**
     * 分页查询
     *
     * @param employee 员工
     * @return {@link Page}<{@link Employee}>
     */
    Page<Employee> pagingQuery(Employee employee);

    /**
     * 更新
     *
     * @param employee 员工
     */
    void update(Employee employee);

    /**
     * 通过id
     *
     * @param id id
     * @return {@link Employee}
     */
    Employee getById(Long id);

}
