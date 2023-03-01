package com.coderpwh.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.mq.producer.RequestProducer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.utils.MessageUtil;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author coderpwh
 * @date 2023/3/1 14:43
 */
public class ResponseConsumer {

    private static Logger logger = LoggerFactory.getLogger(ResponseConsumer.class);


    private static String producerGroup = "please_rename_unique_group_name";

    private static String consumerGroup = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    private static String topic = "RequestTopic";

    public static void consumer() {

        try {
            DefaultMQProducer replyProducer = new DefaultMQProducer(producerGroup);
            replyProducer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            replyProducer.start();

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);

            consumer.setPullTimeDelayMillsWhenException(0L);

            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    logger.info("接收消息,线程名:{},消息为:{}", Thread.currentThread().getName(), list);
                    for (MessageExt msg : list) {
                        try {
                            logger.info("handle message:{}", JSON.toJSONString(msg));
                            String replyTo = MessageUtil.getReplyToClient(msg);
                            byte[] replyContent = "reply message contents.".getBytes(StandardCharsets.UTF_8);
                            Message replyMessage = MessageUtil.createReplyMessage(msg, replyContent);

                            SendResult replyResult = replyProducer.send(replyMessage, 3000);
                            logger.info("replayTo:{},replyResult:{}", replyTo, JSON.toJSONString(replyResult));
                        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.subscribe(topic, "*");
            consumer.start();
        } catch (Exception e) {
            logger.error("异常消息为:{}", e.getMessage());
        }
    }


    public static void main(String[] args) {
        consumer();
    }
}
