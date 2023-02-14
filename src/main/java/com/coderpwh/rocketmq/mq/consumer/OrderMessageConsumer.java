package com.coderpwh.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author coderpwh
 * @date 2023/2/14 14:06
 */
public class OrderMessageConsumer {


    private static Logger logger = LoggerFactory.getLogger(OrderMessageConsumer.class);

    public static final String CONSUMER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TopicTestjjj";

    public static void consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        try {
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe(TOPIC, "TagA || TagC || TagD");
            consumer.registerMessageListener(new MessageListenerOrderly() {
                AtomicLong consumeTimes = new AtomicLong(0);

                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    consumeOrderlyContext.setAutoCommit(true);
                    logger.info("Receive new Message ,线程名为:{},消息为：{}", Thread.currentThread().getName(), list);
                    this.consumeTimes.incrementAndGet();
                    if ((this.consumeTimes.get() % 2) == 0) {
                        return ConsumeOrderlyStatus.SUCCESS;
                    } else if ((this.consumeTimes.get() % 5) == 0) {
                        consumeOrderlyContext.setSuspendCurrentQueueTimeMillis(3000);
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            logger.error("OrderMessageConsumer消费异常,异常消息为:{}", e.getMessage());
        }

    }

    public static void main(String[] args) {
        consumer();
    }
}
