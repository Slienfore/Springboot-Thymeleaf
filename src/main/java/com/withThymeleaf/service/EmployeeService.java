package com.withThymeleaf.service;

import com.withThymeleaf.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    // 员工列表
    List<Employee> lists();

    // 保存员工信息
    void save(Employee employee);

    // 通过员工 Id 进行查询员工信息
    Employee findEmployeeById(Integer id);

    // 更新员工信息
    void update(Employee employee);

    // 删除员工信息
    void delete(Integer id);
}