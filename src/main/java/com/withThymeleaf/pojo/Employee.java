package com.withThymeleaf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    private Double salary;
    // 配置日期格式 => 前端回传字符串格式, 需要进行转换
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String photo;
}