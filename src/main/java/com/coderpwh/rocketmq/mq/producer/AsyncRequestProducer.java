package com.coderpwh.rocketmq.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.RequestCallback;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/3/1 14:29
 */
public class AsyncRequestProducer {

    private static Logger logger = LoggerFactory.getLogger(AsyncRequestProducer.class);


    private static String producerGroup = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "RequestTopic";
    public static final Long ttl = 3000L;

    public static void main(String[] args) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

            producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
            producer.setSendMsgTimeout(60000);
            producer.start();

            Message msg = new Message(TOPIC, "", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            long begin = System.currentTimeMillis();

            producer.request(msg, new RequestCallback() {
                @Override
                public void onSuccess(Message message) {
                    long cost = System.currentTimeMillis() - begin;
                    logger.info("request to topic:{},cost:{},replyMessage:{}", TOPIC, cost, message);
                }

                @Override
                public void onException(Throwable throwable) {
                    logger.error("请求失败，topic:{}", TOPIC);

                }
            }, ttl);
        } catch (Exception e) {
            logger.error("消费异常,异常消息为:{}", e.getMessage());
        }
    }
}
