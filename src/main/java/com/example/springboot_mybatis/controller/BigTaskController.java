package com.example.springboot_mybatis.controller;

import com.example.springboot_mybatis.executor.MyThreadPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: BigTaskController
 * @Date: 2022/5/6 13:56
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BigTaskController {

    @GetMapping("/submit")
    public Boolean submitTask() {
        System.out.println("In the submit task");
        MyThreadPool.submit(() -> {
            try {
                System.out.printf("Executed by the thread pool1: now = %d\n", new Date().getTime());
                Thread.sleep(3000);
                System.out.printf("Executed by the thread pool2: now = %d\n", new Date().getTime());
            } catch (Exception e) {
                System.out.println(" the execution in the thread pool encounter exception");
            }
        });
        return true;
    }
}
