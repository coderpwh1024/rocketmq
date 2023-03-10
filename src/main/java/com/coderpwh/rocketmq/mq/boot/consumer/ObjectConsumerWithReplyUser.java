package com.coderpwh.rocketmq.mq.boot.consumer;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.domain.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 11:20
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.objectRequestTopic}", consumerGroup = "${demo.rocketmq.objectRequestConsumer}", selectorExpression = "${demo.rocketmq.tag}")
public class ObjectConsumerWithReplyUser implements RocketMQReplyListener<User, User> {
    private static Logger logger = LoggerFactory.getLogger(ObjectConsumerWithReplyUser.class);

    @Override
    public User onMessage(User user) {
        logger.info("ObjectConsumerWithReplyUser 接收消息为：{}", JSON.toJSONString(user));

        User replyUser = new User();
        replyUser.setUserAge(10);
        replyUser.setUserName("replyUserName");
        return replyUser;
    }

}
