package com.coderpwh.rocketmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.domain.OrderPaidEvent;
import com.coderpwh.rocketmq.domain.User;
import com.coderpwh.rocketmq.mq.boot.producer.ExtRocketMQTemplate;
import com.coderpwh.rocketmq.service.ProducerService;
import com.coderpwh.rocketmq.util.Result;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author coderpwh
 * @date 2023/3/6 16:31
 */
@Service
public class ProducerServiceImpl implements ProducerService {


    private static Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);


    @Value("${demo.rocketmq.transTopic}")
    private String springTransTopic;

    @Value("${demo.rocketmq.topic}")
    private String springTopic;

    @Value("${demo.rocketmq.topic.user}")
    private String userTopic;

    @Value("${demo.rocketmq.orderTopic}")
    private String orderPaidTopic;

    @Value("${demo.rocketmq.msgExtTopic}")
    private String msgExtTopic;


    @Value("${demo.rocketmq.bytesRequestTopic}")
    private String stringRequestTopic;


    @Value("${demo.rocketmq.objectRequestTopic}")
    private String objectRequestTopic;

    @Value("${demo.rocketmq.genericRequestTopic}")
    private String genericRequestTopic;


    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Resource
    private ExtRocketMQTemplate extRocketMQTemplate;


    @Override
    public Result testProducer() {
        testSpringTopic();

        testUserTopic();

        testUserTopicByWithPayload();

        testSpringTopicByExtRocketMQTemplate();

        testSpringTopicByWithpload();


        return null;
    }


    /**
     * springTopic 测试
     *
     * @return
     */
    public Result testSpringTopic() {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "Hello World!");
        logger.info("方法[testSpringTopic]中 topic:{},结果为:{}", springTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    public Result testUserTopic() {
        User user = new User();
        user.setUserAge(18);
        user.setUserName("coderpwh");
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, user);
        logger.info("方法[testUserTopic]中 topic:{},结果为:{}", userTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    public Result testUserTopicByWithPayload() {
        User user = new User();
        user.setUserAge(18);
        user.setUserName("coderpwh");
        Message<User> message = MessageBuilder.withPayload(user).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build();

        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, message);
        logger.info("方法testUserTopicByWithPayload中,topic为:{},发送结果为:{}", userTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    public Result testSpringTopicByExtRocketMQTemplate() {

        Message<byte[]> message = MessageBuilder.withPayload("Hello,World 2023!".getBytes()).build();

        SendResult sendResult = extRocketMQTemplate.syncSend(springTopic, message);

        logger.info("testSpringTopicByWithPayload方法中,topic为:{},发送结果为:{}", springTopic, JSON.toJSONString(sendResult));

        return Result.ok();
    }

    public Result testSpringTopicByWithpload() {

        Message<byte[]> message = MessageBuilder.withPayload("Hello,World! I'm from spring message".getBytes()).build();

        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, message);

        logger.info("方法testSpringTopicByWithpload中,topic:{},发送结果为:{}", springTopic, JSON.toJSONString(sendResult));

        return Result.ok();
    }

    public Result asyncSend() {

        OrderPaidEvent orderPaidEvent = new OrderPaidEvent("T_001", new BigDecimal("88.00"));

        rocketMQTemplate.asyncSend(orderPaidTopic, orderPaidEvent, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("asnc onSuccess 发送结果为:{}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("async onException Throwable为:{}", throwable);
            }
        });

        return Result.ok();
    }


}
