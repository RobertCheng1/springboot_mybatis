package com.example.springboot_mybatis.domain.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author: chengpengxing
 * @Description:
 * @File: KafkaSender
 * @Date: 2022/7/11 5:27 PM
 */
public interface KafkaSender {

    /**
     *
     * @return message channel
     */
    @Output("myalarm-output")
    MessageChannel pocInfoSender();

}