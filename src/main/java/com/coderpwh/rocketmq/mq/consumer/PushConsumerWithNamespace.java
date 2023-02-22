package com.coderpwh.rocketmq.mq.consumer;

import com.coderpwh.rocketmq.mq.producer.ProducerWithNamespace;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/22 11:29
 */
public class PushConsumerWithNamespace {

    private static Logger logger = LoggerFactory.getLogger(PushConsumerWithNamespace.class);

    public static final String NAMESPACE = "InstanceTest";

    public static final String CONSUMER_GROUP = "cidTest";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "NAMESPACE_TOPIC";


    private static void consumer() {

        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(NAMESPACE, CONSUMER_GROUP);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.subscribe(TOPIC, "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
                msg.forEach(r -> logger.info("topic is:{},msgId is:{},reconsumeTimes is:{}", r.getTopic(), r.getMsgId(), r.getReconsumeTimes()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
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
