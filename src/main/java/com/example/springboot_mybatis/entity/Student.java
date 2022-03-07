package com.example.springboot_mybatis.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: Student
 * @Date: 2022/3/6 16:54
 */
@Data
public class Student {
    private Integer id;
    private String name;
    private Integer gender;
    private String address;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
