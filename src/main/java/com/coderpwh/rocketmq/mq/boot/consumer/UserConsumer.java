package com.coderpwh.rocketmq.mq.boot.consumer;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.domain.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/9 16:53
 */

@Service
@RocketMQMessageListener(nameServer = "${demo.rocketmq.myNameServer}", topic = "${demo.rocketmq.topic.user}", consumerGroup = "user_consumer")
public class UserConsumer implements RocketMQListener<User> {
    private static Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Override
    public void onMessage(User user) {
        logger.info("user_consumer 接收消息内容为:{},用户年龄为:{},用户名称为:{}", JSON.toJSONString(user), user.getUserAge(), user.getUserName());
    }

}
