package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/20 15:33
 */
public class PushProducer {


    private static Logger logger = LoggerFactory.getLogger(TagFilterProducer.class);

    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "BroadCastTest";

    public static String[] tags = new String[]{"TagA", "TagB", "TagC"};


    public static void main(String[] args) {
        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();

            for (int i = 0; i < 10; i++) {
                Message msg = new Message(TOPIC, tags[i % tags.length], "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                logger.info("发送结果为:{}", JSON.toJSONString(sendResult));
            }

        } catch (Exception e) {
            logger.error("广播模式发送消息异常,异常为:{}", e.getMessage());

        }


    }

}
