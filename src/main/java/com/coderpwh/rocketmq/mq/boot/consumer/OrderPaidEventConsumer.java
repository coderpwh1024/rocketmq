package com.coderpwh.rocketmq.mq.boot.consumer;

import com.alibaba.fastjson.JSON;
import com.coderpwh.rocketmq.domain.OrderPaidEvent;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/10 11:31
 */
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.orderTopic}", consumerGroup = "order-paid-consumer")
public class OrderPaidEventConsumer implements RocketMQListener<OrderPaidEvent> {

    private static Logger logger = LoggerFactory.getLogger(OrderPaidEventConsumer.class);

    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        logger.info("OrderPaidEventConsumer 接收消息,订单id为:{},订单信息为:{}", orderPaidEvent.getOrderId(), JSON.toJSONString(orderPaidEvent));
    }

}
