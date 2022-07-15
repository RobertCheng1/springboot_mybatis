package com.example.springboot_mybatis.domain.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: KafkaConsumer
 * @Date: 2022/7/13 5:47 PM
 */
public interface KafkaConsumer {

    String MYALARM_INPUT = "myalarm-input";

    /**
     * 默认 topic 为 output 内字符串
     * 
     * @return subscription channel
     */
    @Input(MYALARM_INPUT)
    MessageChannel pocInfoConsumer();
}
