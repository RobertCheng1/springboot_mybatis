package com.example.springboot_mybatis.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @Author: chengpengxing
 * @Description:
 * @File: PocController
 * @Date: 2022/6/10 1:14
 */
@Slf4j
@RestController
@RequestMapping("/poc")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PocController {

    @Data
    static class UserInfo {
        private  Integer age;
        private  String name;
    }
    @GetMapping("/a")
    public String testGet(UserInfo userInfo, HttpServletResponse response) {
        log.info("In the testGet of OrderController");
        System.out.printf("age = %d, name = %s\n", userInfo.getAge(), userInfo.getName());

        response.addHeader("Content-Disposition", "read_db.txt");
        response.setContentType("application/json");
        return "stardb core abc";
    }

    @PostMapping("/a")
    public String testPost(UserInfo userInfo) {
        log.info("In the testPost of OrderController");
        System.out.printf("age = %d, name = %s\n", userInfo.getAge(), userInfo.getName());
        System.out.println(userInfo);
        return "from testPost RequestBody";
    }

    @PostMapping("/adv")
    public String testReqPost(@RequestBody UserInfo userInfo) {
        log.info("In the testReqPost of OrderController");
        System.out.printf("age = %d, name = %s\n", userInfo.getAge(), userInfo.getName());
        return "from testReqPost RequestBody";
    }



    // 验证同一个接口：只能被串行访问；不同的接口可以并行访问
    @GetMapping("/bt")
    public String btGet() {
        log.info("In the btGet of OrderController");
        return "from btGet RequestBody";
    }
    @GetMapping("/jt")
    public String jtGet() {
        log.info("In the jtGet1 of OrderController");
        log.info("In the jtGet2 of OrderController");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("after sleep");
        return "from jtGet RequestBody";
    }
}
