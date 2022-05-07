package com.example.springboot_mybatis.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
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
    // 由于 mybatis-plus 版本的原因慕课教程中用的类是 PaginationInterceptor, 直接 return new PaginationInterceptor();
    // 在我的这个版本中，用的是 MybatisPlusInterceptor； 直接 return new MybatisPlusInterceptor() 分页没有生效。
    // 后来搜索 “MybatisPlusInterceptor 没有分页”
    // https://www.csdn.net/tags/MtTaMg4sNjc2MTE3LWJsb2cO0O0O.html
    // https://blog.csdn.net/hyk1499449886/article/details/113483133
    // 可知需要调用 addInnerInterceptor 才可以
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
