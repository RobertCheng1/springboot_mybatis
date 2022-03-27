package com.example.springboot_mybatis.dao;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: DataShare
 * @Date: 2022/3/7 18:32
 */
@Data
@Component
public class DataShare {

    private List<String> testList = new ArrayList<>();
}
