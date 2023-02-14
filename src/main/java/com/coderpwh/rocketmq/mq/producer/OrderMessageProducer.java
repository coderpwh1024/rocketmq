package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.CreateMessageQueueForLogicalQueueRequestBody;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author coderpwh
 * @date 2023/2/14 11:42
 */
public class OrderMessageProducer {
    private static Logger logger = LoggerFactory.getLogger(OrderMessageProducer.class);

    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TopicTestjjj";

    public static String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

    public static void main(String[] args) {

        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.start();

            for (int i = 0; i < 100; i++) {
                int orderId = i % 10;

                Message msg = new Message(TOPIC, tags[i % tags.length], "KEY" + i, ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object obj) {
                        Integer id = (Integer) obj;
                        int index = id % list.size();
                        return list.get(index);
                    }
                }, orderId);
                logger.info("发送结果为:{}", JSON.toJSONString(sendResult));
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("OrderMessageProducer发送消息失败, 异常信息为:{}", e.getMessage());
        }
    }


}
