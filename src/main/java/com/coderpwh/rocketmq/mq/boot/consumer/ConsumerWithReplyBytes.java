package com.coderpwh.rocketmq.mq.boot.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 10:43
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.bytesRequestTopic}", consumerGroup = "${demo.rocketmq.bytesRequestConsumer}", selectorExpression = "${demo.rocketmq.tag}")
public class ConsumerWithReplyBytes implements RocketMQReplyListener<MessageExt, byte[]> {


    private static Logger logger = LoggerFactory.getLogger(ConsumerWithReplyBytes.class);

    @Override
    public byte[] onMessage(MessageExt messageExt) {
        String str = "reply message content";
        logger.info("ConsumerWithReplyBytes 接收到消息为:{}",messageExt);

        return str.getBytes();
    }

}
