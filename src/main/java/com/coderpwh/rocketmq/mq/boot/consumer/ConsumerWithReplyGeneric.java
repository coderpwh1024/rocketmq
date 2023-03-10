package com.coderpwh.rocketmq.mq.boot.consumer;

import com.coderpwh.rocketmq.domain.ProductWithPayload;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 10:54
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.genericRequestTopic}", consumerGroup = "${demo.rocketmq.genericRequestConsumer}", selectorExpression = "${demo.rocketmq.tag}")
public class ConsumerWithReplyGeneric implements RocketMQReplyListener<String, ProductWithPayload<String>> {

    private static Logger logger = LoggerFactory.getLogger(ConsumerWithReplyGeneric.class);


    @Override
    public ProductWithPayload<String> onMessage(String s) {
        logger.info("ConsumerWithReplyGeneric 接收消息内容为:{}", s);
        return new ProductWithPayload<String>("replyProducetName", "product payload");
    }


}
