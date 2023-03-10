package com.coderpwh.rocketmq.mq.boot.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 11:10
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.msgExtTopic}", selectorExpression = "tag0||tag1", consumerGroup = "${spring.application.name}-message-ext-consumer")
public class MessageExtConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static Logger logger = LoggerFactory.getLogger(MessageExtConsumer.class);

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("接收消息id为:{},消息内容为:{}", messageExt.getMsgId(), JSON.toJSONString(messageExt.getBody()));
    }


    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        defaultMQPushConsumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }

}
