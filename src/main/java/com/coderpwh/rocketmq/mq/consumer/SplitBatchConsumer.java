package com.coderpwh.rocketmq.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/10 11:39
 */
public class SplitBatchConsumer {


    private static Logger logger = LoggerFactory.getLogger(SplitBatchConsumer.class);

    public static final String CONSUMER_GROUP = "BatchProducerGroupName";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "BatchTest";


    public static void consumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe(TOPIC, "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msg);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            System.out.printf("Consumer Started.%n");
        } catch (Exception e) {
            logger.error("SplitBatchConsumer消费异常,异常信息为：{}", e.getMessage());
        }
    }


    public static void main(String[] args) {
        consumer();
    }

}
