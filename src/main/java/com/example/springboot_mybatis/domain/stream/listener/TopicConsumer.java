package com.example.springboot_mybatis.domain.stream.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: TopicListener
 * @Date: 2022/7/11 7:59 PM
 */

@Slf4j
@Component
public class TopicConsumer {

    @StreamListener("robert")
    private void onMessage(@Payload String aggregationMessage) {
        log.info("[aggregationMessage]线程编号: {}, 消息内容: {}", Thread.currentThread().getId(), aggregationMessage);
    }

}