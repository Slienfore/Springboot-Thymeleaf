package com.withThymeleaf.mapper;

import com.withThymeleaf.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    // 员工列表
    List<Employee> lists();

    // 保存员工信息
    void save(Employee employee);

    // 根据员工 id 进行查询员工信息
    Employee findEmployeeById(Integer id);

    // 更新员工信息
    void update(Employee employee);

    // 删除员工信息
    void delete(Integer id);
}