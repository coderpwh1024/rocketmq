package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author coderpwh
 * @date 2023/2/10 17:07
 */

public class TimerMessageProducer {

    private static Logger logger = LoggerFactory.getLogger(TimerMessageProducer.class);

    public static final String PRODUCER_GROUP = "TimerMessagePruducerGroup";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TimerTopic";

    public static void main(String[] args) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();
            int totalMessageToSend = 10;

            for (int i = 0; i < totalMessageToSend; i++) {
                Message message = new Message(TOPIC, ("Hello scheduled message" + i).getBytes(StandardCharsets.UTF_8));
                message.setDelayTimeLevel(3);
                SendResult result = producer.send(message);
                logger.info("延时队列发送结果为:{}", JSON.toJSONString(result));
            }
        } catch (Exception e) {
            logger.error("延时队列发送异常,异常结果为:{}", e.getMessage());
        }

    }

}
