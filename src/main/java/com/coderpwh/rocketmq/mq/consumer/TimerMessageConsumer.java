package com.coderpwh.rocketmq.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/10 17:24
 */
public class TimerMessageConsumer {


    private static Logger logger = LoggerFactory.getLogger(TimerMessageConsumer.class);

    public static final String CONSUMER_GROUP = "TimerMessageConsumerGroup";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TimerTopic";


    public static void consumer() {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.subscribe(TOPIC, "*");

            consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
                for (MessageExt message : messages) {
                    logger.info("接收message消息体,msgId:{},时间为:{}", message.getMsgId(), System.currentTimeMillis() - message.getBornTimestamp());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("延时队列消费端异常,异常消息为:{}", e.getMessage());
        }
    }


    public static void main(String[] args) {
        consumer();
    }
}
