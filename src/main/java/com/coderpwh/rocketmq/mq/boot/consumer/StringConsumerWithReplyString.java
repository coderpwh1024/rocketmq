package com.coderpwh.rocketmq.mq.boot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 10:14
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.stringRequestTopic}", consumerGroup = "${demo.rocketmq.stringRequestConsumer}", selectorExpression = "${demo.rocketmq.tag}", replyTimeout = 10000)
public class StringConsumerWithReplyString implements RocketMQReplyListener<String, String> {


    private static Logger logger = LoggerFactory.getLogger(StringConsumer.class);

    @Override
    public String onMessage(String s) {
        logger.info("StringConsumerWithReplyString 接收到消息为:{}", s);
        return "reply String";
    }

}
