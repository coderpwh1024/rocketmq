package com.coderpwh.rocketmq.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/21 10:32
 */
public class TracePushConsumer {

    private static Logger logger = LoggerFactory.getLogger(TracePushConsumer.class);

    public static final String CONSUMER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TopicTest";


    public static void consumer() {

        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setConsumeTimestamp("");
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                logger.info("Receive New Messages,线程名:{},消息体:{}", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            logger.info("开始消费了");
        } catch (Exception e) {
            logger.error("消费者消息异常,异常消息为:{}", e.getMessage());
        }

    }


    public static void main(String[] args) {
        consumer();
    }

}
