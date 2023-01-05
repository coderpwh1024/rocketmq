package com.coderpwh.rocketmq.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author coderpwh
 * @date 2023/1/5 13:56
 */
public class SimpleBatchProducer {


    private static Logger logger = LoggerFactory.getLogger(SimpleBatchProducer.class);
    public static final String PRODUCER_GROUP = "BatchProducerGroupName";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "BatchTest";

    public static final String TAG = "Tag";


    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.setSendMsgTimeout(60000);
            producer.start();
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(TOPIC, TAG, "OrderID001", "Hello world 0".getBytes(StandardCharsets.UTF_8)));
            messages.add(new Message(TOPIC, TAG, "OrderID002", "Hello world 1".getBytes(StandardCharsets.UTF_8)));
            messages.add(new Message(TOPIC, TAG, "OrderID003", "Hello world 2".getBytes(StandardCharsets.UTF_8)));
            messages.add(new Message(TOPIC, TAG, "OrderID004", "Hello world 3".getBytes(StandardCharsets.UTF_8)));

            SendResult sendResult = producer.send(messages);
            logger.info("批量发送mq消息结果为:{}", sendResult);
        } catch (Exception e) {
            logger.error("批量发送mq消息异常,异常消息为:{}", e.getMessage());
        }

    }

}
