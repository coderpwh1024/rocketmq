package com.coderpwh.rocketmq.mq.consumer;

import com.coderpwh.rocketmq.mq.producer.ScheduledMessageProducer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/14 10:12
 */
public class ScheduledMessageConsumer {

    private static Logger logger = LoggerFactory.getLogger(ScheduledMessageConsumer.class);


    public static final String CONSUMER_GROUP = "ExampleConsumer";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TestTopic";


    public static void consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        try {
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe(TOPIC, "*");

            consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
                for (MessageExt message : messages) {
                    logger.info("Receive message,msgId:{},时间为:{}", message.getMsgId(), System.currentTimeMillis() - message.getStoreTimestamp());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("消费异常,异常消息为:{}", e.getMessage());
        }
    }


    public static void main(String[] args) {


    }


}
