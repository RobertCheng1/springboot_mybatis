package com.example.springboot_mybatis.controller;

import com.example.springboot_mybatis.dao.StudentMapper;
import com.example.springboot_mybatis.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {
    // 依赖注入的方式：
    // Case1: @Autowired 有告警：Field injection is not recommended
    // 参考： https://blog.csdn.net/zhangjingao/article/details/81094529
    // @Autowired
    // private StudentMapper studentMapper;
    //
    // Case2: 构造方法，这个不用写 @Autowired？难道Spring框架自己做的。如果属性很多的话肯定，这么写肯定不合理
    // private StudentMapper studentMapper;
    // public HelloController(StudentMapper studentMapper){
    //     System.out.println("aaaaaaaaaaaaa");
    //     System.out.println(studentMapper);
    //     this.studentMapper = studentMapper;
    // }
    // Case3: 根据 case2 的实现 和 stardb_cluster 中 domain-service-impl-MyDbServiceImpl 的启发：
    // 猜测 final 修饰符 + 注解 @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    // 参考：https://blog.csdn.net/qq_37256896/article/details/115869717
    private final StudentMapper studentMapper;

    @GetMapping("/hello")
    public String hello(){
        List<Student> list = studentMapper.selectList(null);
        System.out.println(list);
        return "This is from Springboot Mybatis;";
    }
}

