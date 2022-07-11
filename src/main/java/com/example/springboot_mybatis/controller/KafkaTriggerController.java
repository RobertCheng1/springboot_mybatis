package com.example.springboot_mybatis.controller;

import com.example.springboot_mybatis.domain.stream.source.KafkaSender;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: KafkaTriggerController
 * @Date: 2022/7/11 6:00 PM
 */
@Slf4j
@RestController
@RequestMapping("/kafkapoc")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaTriggerController {

    private final KafkaSender kafkaSender;

    @Data
    static class MockInfo {
        private String name;
        private Integer score;
    }
    @GetMapping("/")
    public String get() {
        log.info("In the KafkaTriggerController");
        MockInfo mockInfo = new MockInfo();
        mockInfo.setName("robert");
        mockInfo.setScore(100);
        kafkaSender.pocInfoSender().send(MessageBuilder.withPayload(mockInfo).build());
        return "from btGet RequestBody";
    }
}
