package com.coderpwh.rocketmq.service.impl;

import com.coderpwh.rocketmq.service.ProducerService;
import com.coderpwh.rocketmq.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author coderpwh
 * @date 2023/3/6 16:31
 */
@Service
public class ProducerServiceImpl implements ProducerService {


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
    

    @Override

    public Result testProducer() {
        return null;
    }


}
