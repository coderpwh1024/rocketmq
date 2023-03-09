package com.coderpwh.rocketmq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * @author coderpwh
 * @date 2023/3/9 15:07
 */
@RocketMQTransactionListener(rocketMQTemplateBeanName = "extRocketMQTemplate")
public class ExtTransactionListenerImpl implements RocketMQLocalTransactionListener {


    private static Logger logger = LoggerFactory.getLogger(ExtTransactionListenerImpl.class);

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        logger.info("检查ExtTransactionListenerImpl 监听,返回UNKNOWN");
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        logger.info("检查ExtTransactionListenerImpl 监听,返回commit");

        return RocketMQLocalTransactionState.COMMIT;
    }

}
