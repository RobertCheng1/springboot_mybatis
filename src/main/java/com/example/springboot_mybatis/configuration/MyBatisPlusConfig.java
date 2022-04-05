package com.example.springboot_mybatis.configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: MyBatisPlusConfig
 * @Date: 2022/3/29 11:07
 */
@Configuration
public class MyBatisPlusConfig {
    // ToDo: 可能由于版本的原因没有生效: 慕课教程中用的类是  PaginationInterceptor
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }

}
