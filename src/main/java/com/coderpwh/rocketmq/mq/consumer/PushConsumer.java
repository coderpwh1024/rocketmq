package com.coderpwh.rocketmq.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者负载均衡
 *
 * @author coderpwh
 * @date 2023/2/20 15:21
 */
public class PushConsumer {

    private static Logger logger = LoggerFactory.getLogger(PushConsumer.class);

    public static final String CONSUMER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "BroadCastTest";
    public static final String SUB_EXPRESSION = "TagA||TagC||TagD";

    public static void consumer() {

        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.subscribe(TOPIC, SUB_EXPRESSION);

            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
                logger.info("Receive new Message 线程名:{},消息体:{}", Thread.currentThread().getName(), msg);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        } catch (Exception e) {
            logger.error("消费异常,异常消息为:{}", e.getMessage());
        }


    }


    public static void main(String[] args) {
        consumer();
    }

}
