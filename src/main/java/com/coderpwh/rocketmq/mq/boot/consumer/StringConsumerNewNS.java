package com.coderpwh.rocketmq.mq.boot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/9 17:18
 */
@Service
@RocketMQMessageListener(nameServer = "${demo.rocketmq.myNameServer}", topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer_newns")
public class StringConsumerNewNS implements RocketMQListener<String> {


    private static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    @Override
    public void onMessage(String s) {
        logger.info("StringConsumerNewNS 接受消息为:{}", s);
    }

}
