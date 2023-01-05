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
 * @date 2023/1/5 14:53
 */
public class SplitBatchProducer {

    private static Logger logger = LoggerFactory.getLogger(SplitBatchProducer.class);

    public static final String PRODUCER_GROUP = "BatchProducerGroupName";

    public static final String DEFAULT_NAMESRVADDR = "";

    public static final int MESSAGE_COUNT = 100 * 1000;

    public static final String TOPIC = "BatchTest";

    public static final String TAG = "Tag";

    public static void main(String[] args) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer();
            producer.start();

            List<Message> messages = new ArrayList<>(MESSAGE_COUNT);
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                messages.add(new Message(TOPIC, TAG, "OrderID" + i, ("Hello world" + i).getBytes(StandardCharsets.UTF_8)));
            }

            ListSplitter splitter = new ListSplitter(messages);
            while (splitter.hasNext()) {
                List<Message> listItem = splitter.next();
                SendResult sendResult = producer.send(listItem);
                logger.info("批量发送mq消息，发送结果为:{}", sendResult);
            }
        } catch (Exception e) {
            logger.error("批量发送mq消息异常,异常信息为:{}",e.getMessage());

        }
    }


}
