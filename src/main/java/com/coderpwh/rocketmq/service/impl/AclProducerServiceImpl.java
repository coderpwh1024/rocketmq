package com.coderpwh.rocketmq.service.impl;

import com.coderpwh.rocketmq.service.AclProducerService;
import com.coderpwh.rocketmq.util.Result;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.messaging.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author coderpwh
 * @date 2023/3/13 10:52
 */
@Service
public class AclProducerServiceImpl implements AclProducerService {


    private static Logger logger = LoggerFactory.getLogger(AclProducerServiceImpl.class);


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${demo.rocketmq.normal.transTopic}")
    private String springTransTopic;


    @Value("${demo.rocketmq.normal.topic}")
    private String springTopic;

    @Override

    public Result testAclProducer() {
        return null;
    }


    public void testTransaction() {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

        try {
            for (int i = 0; i < 10; i++) {
                Message msg = MessageBuilder.withPayload("Hello RocketMQ " + i).setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();

                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(springTransTopic + ":" + tags[i % tags.length], msg, null);
                logger.info("testTransaction方法,发送消息内容为:{},发送结果为:{}", msg.getPayload(), sendResult.getSendStatus());

                Thread.sleep(10);
            }
        } catch (Exception e) {
            logger.error("testTransaction方法 发送事务消息异常,异常消息为:{}", e.getMessage());
        }
    }




}
