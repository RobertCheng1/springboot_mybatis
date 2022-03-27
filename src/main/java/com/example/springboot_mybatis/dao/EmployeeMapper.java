package com.example.springboot_mybatis.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.springboot_mybatis.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: EmployeeMapper
 * @Date: 2022/3/6 16:55
 */
@Component
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("SELECT * FROM employee ${ew.customSqlSegment}")
    List<Employee> selectMy(@Param(Constants.WRAPPER) Wrapper<Employee> wrapper);
}
