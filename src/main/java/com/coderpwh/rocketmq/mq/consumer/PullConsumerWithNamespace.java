package com.coderpwh.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author coderpwh
 * @date 2023/2/22 17:40
 */
public class PullConsumerWithNamespace {


    private static Logger logger = LoggerFactory.getLogger(PushConsumerWithNamespace.class);

    public static final String NAMESPACE = "InstanceTest";

    public static final String CONSUMER_GROUP = "cidTest";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "NAMESPACE_TOPIC";

    private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<>();


    public static void consumer() {

        try {
            DefaultMQPullConsumer pullConsumer = new DefaultMQPullConsumer(NAMESPACE, CONSUMER_GROUP);
            pullConsumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            pullConsumer.start();
            Set<MessageQueue> mqs = pullConsumer.fetchSubscribeMessageQueues(TOPIC);

            for (MessageQueue mq : mqs) {
                logger.info("Consume from the topic:{} queue:{}", mq.getTopic(), mq);
                SINGLE_MQ:
                while (true) {

                    PullResult pullResult =
                            pullConsumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    logger.info("消费结果为:{}", JSON.toJSONString(pullResult));
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            dealWithPullResult(pullResult);
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                }

            }

        } catch (Exception e) {
            logger.error("消息消费异常,异常消息为:{}", e.getMessage());
        }

    }

    public static void main(String[] args) {
        consumer();
    }


    private static long getMessageQueueOffset(MessageQueue mq) {

        Long offset = OFFSET_TABLE.get(mq);

        if (offset != null) {
            return offset;
        }

        return 0;
    }

    private static void dealWithPullResult(PullResult pullResult) {

        if (null == pullResult || pullResult.getMsgFoundList().isEmpty()) {
            return;
        }

        pullResult.getMsgFoundList().forEach(msg -> logger.info("Topic is:{},msgId:{}", msg.getTopic(), msg.getMsgId()));
    }

    private static void putMessageQueueOffset(MessageQueue mq, Long offset) {
        OFFSET_TABLE.put(mq, offset);
    }


}
