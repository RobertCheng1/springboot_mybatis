package com.example.springboot_mybatis.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: MyScheduler
 * @Date: 2022/3/8 11:37
 */

@Component
public class MyScheduler {

    @Scheduled(cron = "${task.report:0 */1 * * * *}")
    public void cronDailyReport() {
       System.out.printf("---In the cronDailyReport at %s---\n", new Date());
    }

}
