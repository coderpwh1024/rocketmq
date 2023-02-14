package com.coderpwh.rocketmq.mq.consumer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author coderpwh
 * @date 2023/2/14 16:02
 */

public class TransactionListenerImpl implements TransactionListener {


    public static void consumer() {

    }

    public static void main(String[] args) {

    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}
