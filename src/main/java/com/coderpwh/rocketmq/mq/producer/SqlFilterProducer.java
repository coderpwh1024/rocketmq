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
 * @date 2023/2/17 15:16
 */
public class SqlFilterProducer {

    private static Logger logger = LoggerFactory.getLogger(SqlFilterProducer.class);

    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "SqlFilterTest";

    public static String[] tags = new String[]{"TagA", "TagB", "TagC"};

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        try {
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();

            for (int i = 0; i < 10; i++) {
                Message msg = new Message(TOPIC, tags[i % tags.length], ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                msg.putUserProperty("a", String.valueOf(i));
                SendResult sendResult = producer.send(msg);
                logger.info("SQL过滤发送结果为:{}", JSON.toJSONString(sendResult));
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("sql过滤生产方消息发送异常,异常消息为:{}", e.getMessage());
        }

    }


}
