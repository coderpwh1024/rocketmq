package com.coderpwh.rocketmq.mq.consumer;

import apache.rocketmq.v2.MessageQueue;
import org.apache.rocketmq.client.consumer.PullResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
