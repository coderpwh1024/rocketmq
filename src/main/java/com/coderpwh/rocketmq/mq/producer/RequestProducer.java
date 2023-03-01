package com.coderpwh.rocketmq.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/3/1 14:15
 */
public class RequestProducer {
    private static Logger logger = LoggerFactory.getLogger(RequestProducer.class);

    private static String producerGroup = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "RequestTopic";

    private static Long tt1 = 3000L;

    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.setSendMsgTimeout(60000);
            producer.start();

            Message msg = new Message(TOPIC, "", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            long begin = System.currentTimeMillis();
            Message retMsg = producer.request(msg, tt1);
            long cost = System.currentTimeMillis() - begin;
            logger.info("request to:{} cost:{} replyMessage:{}", TOPIC, cost, retMsg);

            producer.shutdown();
        } catch (Exception e) {
            logger.error("生产者异常,异常消息为:{}", e.getMessage());
        }
    }

}
