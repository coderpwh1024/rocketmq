package com.coderpwh.rocketmq.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author coderpwh
 * @date 2022/12/29 10:42
 */
@Component
public class QuickStartProducer {


    private static Logger logger = LoggerFactory.getLogger(QuickStartProducer.class);


    public static final int MESSAGE_COUNT = 1000;
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TestTopic";
    public static final String TAG = "TagA";


    public  void send() throws InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        try {
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.start();

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String str = "Hello RocketMQ" + i;
                Message msg = new Message(TOPIC, TAG, str.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }
        } catch (Exception e) {
            logger.error("quickStartProducer中[send]异常信息为:{}", e.getMessage());
            e.printStackTrace();
            Thread.sleep(1000);
        }
        producer.shutdown();
    }


}
