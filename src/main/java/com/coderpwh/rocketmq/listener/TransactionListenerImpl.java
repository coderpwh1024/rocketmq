package com.coderpwh.rocketmq.listener;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MQ事务消息监听
 *
 * @author coderpwh
 * @date 2023/3/8 14:38
 */
@RocketMQTransactionListener
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private static Logger logger = LoggerFactory.getLogger(TransactionListenerImpl.class);

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();


    /***
     *  执行事务
     * @param message
     * @return
     */

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object obj) {

        String transactionId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);

        logger.info("开始执行事务消息，事务transactionId为:{}", transactionId);

        int value = transactionIndex.getAndIncrement();

        int status = value % 3;

        localTrans.put(transactionId, status);

        if (status == 0) {
            logger.info("执行本地事务消息成功,消息内容为:{}", JSON.toJSONString(message.getPayload()));
            return RocketMQLocalTransactionState.COMMIT;
        }

        if (status == 1) {
            logger.info("执行本地事务消息失败,消息内容为:{}", JSON.toJSONString(message.getPayload()));
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        logger.info("执行本地事务消息失败,发生未知情况");
        return RocketMQLocalTransactionState.UNKNOWN;
    }


    /***
     *  检查事务消息
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        String transactionId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);

        RocketMQLocalTransactionState retState = RocketMQLocalTransactionState.COMMIT;

        Integer status = localTrans.get(transactionId);

        if (null != status) {
            switch (status) {
                case 0:
                    retState = RocketMQLocalTransactionState.COMMIT;
                    break;
                case 1:
                    retState = RocketMQLocalTransactionState.ROLLBACK;
                    break;
                case 2:
                    retState = RocketMQLocalTransactionState.UNKNOWN;
                    break;
            }
        }
        logger.info("检查事务,事务id为:{},事务状态为:{},状态为:{}", transactionId, retState, status);
        return retState;
    }

}
