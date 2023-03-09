package com.coderpwh.rocketmq.mq.boot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/9 17:03
 */

@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer",selectorExpression ="${demo.rocketmq.tag}",tlsEnable = "${demo.rocketmq.tlsEnable}")
public class StringConsumer implements RocketMQListener<String> {

    private static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    @Override
    public void onMessage(String s) {
        logger.info("StringConsumer 接收消息内容为:{}", s);
    }
}
