package com.coderpwh.rocketmq.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author coderpwh
 * @date 2023/2/22 11:12
 */
public class ProducerWithNamespace {

    private static Logger logger = LoggerFactory.getLogger(ProducerWithNamespace.class);
    public static final String NAMESPACE = "InstanceTest";

    public static final String PRODUCER_GROUP = "pidTest";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final int MESSAGE_COUNT = 100;

    public static final String TOPIC = "NAMESPACE_TOPIC";

    public static final String TAG = "tagTest";

    public static void main(String[] args) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer(NAMESPACE, PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                Message message = new Message(TOPIC, TAG, "Hello world".getBytes(StandardCharsets.UTF_8));
                SendResult sendResult = producer.send(message);
                logger.info("Topic:{},send success,misId is:{}", message.getTopic(), sendResult.getMsgId());
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("异常消息为:{}", e.getMessage());
        }

    }

}
