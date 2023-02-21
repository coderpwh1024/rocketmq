package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/21 10:18
 */
public class TraceProducer {

    private static Logger logger = LoggerFactory.getLogger(TraceProducer.class);

    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";

    public static final String KEY = "OrderID188";
    public static final int MESSAGE_COUNT = 128;


    public static void main(String[] args) {

        try {

            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(6000);
            producer.start();

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                Message msg = new Message(TOPIC, TAG, KEY, "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                logger.info("发送结果为:{}", JSON.toJSONString(sendResult));
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("生产消息发送异常,异常为:{}", e.getMessage());
        }

    }

}
