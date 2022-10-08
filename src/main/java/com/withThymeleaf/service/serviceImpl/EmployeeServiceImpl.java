package com.withThymeleaf.service.serviceImpl;

import com.withThymeleaf.mapper.EmployeeMapper;
import com.withThymeleaf.pojo.Employee;
import com.withThymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> lists() {

        return employeeMapper.lists();
    }

    @Override
    public void save(Employee employee) {
        employeeMapper.save(employee);
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeMapper.findEmployeeById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void delete(Integer id) {
        employeeMapper.delete(id);
    }
}