package com.coderpwh.rocketmq.mq.boot.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/13 11:43
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.normal.transTopic}", consumerGroup = "group_define_in_cloud_MQ", accessKey = "${rocketmq.producer.access-key}", secretKey = "${rocketmq.producer.secret-key}")
public class AclStringTransactionalConsumer implements RocketMQListener<String> {


    private static Logger logger = LoggerFactory.getLogger(AclStringConsumer.class);

    @Override
    public void onMessage(String s) {
        logger.info("AclStringTransactionalConsumer 接收消息为:{}", s);
    }

}
