package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author coderpwh
 * @date 2023/2/14 9:58
 */
public class ScheduledMessageProducer {

    private static Logger logger = LoggerFactory.getLogger(ScheduledMessageProducer.class);

    public static final String PRODUCER_GROUP = "ExampleProducerGroup";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TestTopic";


    public static void main(String[] args) {

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        try {
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();
            Integer totalMessagesToSend = 100;

            for (int i = 0; i < totalMessagesToSend; i++) {
                Message message = new Message(TOPIC, ("Hello scheduled message " + i).getBytes(StandardCharsets.UTF_8));
                message.setDelayTimeLevel(3);
                SendResult result = producer.send(message);
                logger.info("发送结果为:{}", JSON.toJSONString(result));
            }
        } catch (Exception e) {
            logger.error("消息发送失败,异常信息为:{}", e.getMessage());
        }
        producer.shutdown();
    }


}
