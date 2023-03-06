package com.coderpwh.rocketmq.service.impl;

import com.coderpwh.rocketmq.mq.producer.QuickStartProducer;
import com.coderpwh.rocketmq.service.SendService;
import com.coderpwh.rocketmq.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author coderpwh
 * @date 2022/12/29 9:57
 */
@Service
public class SendServiceImpl implements SendService {

    private static Logger logger = LoggerFactory.getLogger(SendServiceImpl.class);


    @Resource
    private QuickStartProducer producer;


    /***
     * mq简单示例
     * @return
     */
    @Override
    public Result quickStart() {
        try {
            logger.info("已经进入quickStart方法中，准备发送发送mq消息");
            producer.send();
            logger.info("已经进入quickStart方法中，mq发送消息完毕");
        } catch (Exception e) {
            logger.error("quickStart发送mq消息异常，异常信息为:{}", e.getMessage());
        }
        return Result.ok();
    }

}
