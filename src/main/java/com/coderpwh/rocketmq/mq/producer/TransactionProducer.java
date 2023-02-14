package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author coderpwh
 * @date 2023/2/14 14:57
 */
public class TransactionProducer {

    private static Logger logger = LoggerFactory.getLogger(TransactionProducer.class);
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TopicTest1234";

    public static final int MESSAGE_COUNT = 10;


    public static String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};


    public static void main(String[] args) {

        try {

            TransactionMQProducer producer = new TransactionMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);

            ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), r -> {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            });

            producer.setExecutorService(executorService);
//            producer.setTransactionListener();
            producer.start();

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                Message msg = new Message(TOPIC, tags[i % tags.length], "KEY" + i, ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                logger.info("事务发送消息结果为:{}", JSON.toJSONString(sendResult));
                Thread.sleep(10);
            }
            for (int i = 0; i < 100000; i++) {
                Thread.sleep(1000);
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("事务消息发送异常,异常结果为:{}", e.getMessage());
        }

    }


}
