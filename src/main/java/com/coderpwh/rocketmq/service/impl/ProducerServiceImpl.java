package com.coderpwh.rocketmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.domain.User;
import com.coderpwh.rocketmq.mq.consumer.OpenTracingPushConsumer;
import com.coderpwh.rocketmq.service.ProducerService;
import com.coderpwh.rocketmq.util.Result;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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


    @Override
    public Result testProducer() {
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


}
