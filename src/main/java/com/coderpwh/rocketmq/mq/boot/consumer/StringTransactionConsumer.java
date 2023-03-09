package com.coderpwh.rocketmq.mq.boot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/9 17:10
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.transTopic}", consumerGroup = "string_trans_consumer")
public class StringTransactionConsumer implements RocketMQListener<String> {

    private static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    @Override
    public void onMessage(String s) {
        logger.info("StringTransactionConsumer 接收消息为:{}", s);
    }
}
