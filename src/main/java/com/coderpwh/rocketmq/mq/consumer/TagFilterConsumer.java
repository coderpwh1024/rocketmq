package com.coderpwh.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author coderpwh
 * @date 2023/2/16 15:31
 */
public class TagFilterConsumer {

    private static Logger logger = LoggerFactory.getLogger(TagFilterConsumer.class);

    public static final String CONSUMER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TagFilterTest";


    private static void consumer() {

        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe(TOPIC, "TagA||TagC");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    logger.info("Receive new Messages,线程名为:{},消息体为:{}", Thread.currentThread().getName(), JSON.toJSONString(consumeConcurrentlyContext));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("异常消息为:{}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        consumer();
    }


}
