package com.withThymeleaf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private Boolean gender;
}