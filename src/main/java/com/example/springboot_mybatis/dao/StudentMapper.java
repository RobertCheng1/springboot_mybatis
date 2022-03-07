package com.example.springboot_mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot_mybatis.entity.Student;
import org.springframework.stereotype.Component;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: StudentMapper
 * @Date: 2022/3/6 16:55
 */
@Component
public interface StudentMapper extends BaseMapper<Student> {

}
