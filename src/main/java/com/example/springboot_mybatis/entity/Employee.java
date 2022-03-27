package com.example.springboot_mybatis.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: Employee
 * @Date: 2022/3/6 16:54
 */
@Data
public class Employee {
    private Long id;

    @TableField(condition = SqlCondition.LIKE) // 实体作为条件构造器改造方法的参数,使用“等值”比较符
    private String name;

    // @TableField(condition = "%s&lt;#{%s}")  // SqlCondition中只有 NOT_EQUAL，没有小于，于是触类旁通
    private Integer age;

    private String email;
    private Long managerId;
    private LocalDateTime createTime;
}
