package com.example.springboot_mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NbaPlayer {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerId;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 版本
    @Version
    private Integer version;

    // 逻辑删除标识（0：未删除  1：已删除）
    @TableLogic
    @TableField(select = false) //为 select 时不输出该列的值
    private Integer deleted;
}
