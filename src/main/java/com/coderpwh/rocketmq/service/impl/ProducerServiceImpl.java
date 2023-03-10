package com.coderpwh.rocketmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.coderpwh.rocketmq.domain.OrderPaidEvent;
import com.coderpwh.rocketmq.domain.ProductWithPayload;
import com.coderpwh.rocketmq.domain.User;
import com.coderpwh.rocketmq.mq.boot.producer.ExtRocketMQTemplate;
import com.coderpwh.rocketmq.service.ProducerService;
import com.coderpwh.rocketmq.util.Result;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author coderpwh
 * @date 2023/3/6 16:31
 */
@Service
public class ProducerServiceImpl implements ProducerService {


    private static Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);


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


    @Value("${demo.rocketmq.bytesRequestTopic}")
    private String bytesRequestTopic;


    @Value("${demo.rocketmq.objectRequestTopic}")
    private String objectRequestTopic;

    @Value("${demo.rocketmq.genericRequestTopic}")
    private String genericRequestTopic;


    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @Resource
    private ExtRocketMQTemplate extRocketMQTemplate;


    @Override
    public Result testProducer() {
//        testSpringTopic();

//        testUserTopic();

//        testUserTopicByWithPayload();

//        testSpringTopicByExtRocketMQTemplate();

//       testSpringTopicByWithpload();

//        asyncSend();


        // TODO  test 未消费
//        convertAndSendByMsgExtTopic();

        convertAndSendByMsgExtTopicTag1();

        /*testBatchMessages();

        testSendBatchMessageOrderly();

        testRocketMQTemplateTransaction();

        testExtRocketMQTemplateTransaction();

        testStringRequestTopic();

        testBytesRequestTopic();

        testObjectRequestTopic();

        testGenericRequestTopic();

        testStringRequestTopicBySendAndReceive();

        testObjectRequestTopicBySendAndReceive();*/

        return Result.ok();
    }


    /**
     * springTopic 测试
     *
     * @return
     */
    public Result testSpringTopic() {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "Hello World!");
        logger.info("方法[testSpringTopic]中 topic:{},结果为:{}", springTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    /***
     * testUserTopic 测试
     * @return
     */
    public Result testUserTopic() {
        User user = new User();
        user.setUserAge(18);
        user.setUserName("coderpwh");
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, user);
        logger.info("方法[testUserTopic]中 topic:{},结果为:{}", userTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    /***
     *  testUserTopicByWithPayload 测试
     * @return
     */
    public Result testUserTopicByWithPayload() {
        User user = new User();
        user.setUserAge(20);
        user.setUserName("coderpwh");
        Message<User> message = MessageBuilder.withPayload(user).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build();

        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, message);
        logger.info("方法testUserTopicByWithPayload中,topic为:{},发送结果为:{}", userTopic, JSON.toJSONString(sendResult));
        return Result.ok();
    }


    /***
     *  testSpringTopicByExtRocketMQTemplate 测试
     * @return
     */
    public Result testSpringTopicByExtRocketMQTemplate() {

        Message<byte[]> message = MessageBuilder.withPayload("Hello,World 2023!".getBytes()).build();

        SendResult sendResult = extRocketMQTemplate.syncSend(springTopic, message);

        logger.info("testSpringTopicByWithPayload方法中,topic为:{},发送结果为:{}", springTopic, JSON.toJSONString(sendResult));

        return Result.ok();
    }


    /***
     *  testSpringTopicByWithpload 测试
     * @return
     */
    public Result testSpringTopicByWithpload() {

        Message<byte[]> message = MessageBuilder.withPayload("Hello,World! I'm from spring message".getBytes()).build();

        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, message);

        logger.info("方法testSpringTopicByWithpload中,topic:{},发送结果为:{}", springTopic, JSON.toJSONString(sendResult));

        return Result.ok();
    }


    /**
     * asyncSend 测试
     *
     * @return
     */

    public Result asyncSend() {

        OrderPaidEvent orderPaidEvent = new OrderPaidEvent("T_001", new BigDecimal("88.00"));

        rocketMQTemplate.asyncSend(orderPaidTopic, orderPaidEvent, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("asnc onSuccess 发送结果为:{}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("async onException Throwable为:{}", throwable);
            }
        });

        return Result.ok();
    }


    /***
     *convertAndSendByMsgExtTopic 测试
     * @return
     */
    public Result convertAndSendByMsgExtTopic() {
        rocketMQTemplate.convertAndSend(msgExtTopic + ": tag0", "I'm from tag0");
        logger.info("syncSend topic:{},tag:{}", msgExtTopic, "tag0");
        return Result.ok();
    }


    /***
     * convertAndSendByMsgExtTopicTag1 测试
     * @return
     */
    public Result convertAndSendByMsgExtTopicTag1() {

        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag1", "I'm from tag1");
        logger.info("syncSend topic:{},tag:{}", msgExtTopic, "tag1");
        return Result.ok();
    }


    /***
     *  testBatchMessages测试
     * @return
     */
    public Result testBatchMessages() {
        List<Message> msgs = new ArrayList<>();
        String str = "Hello RocketMQ Batch Msg#";

        for (int i = 0; i < 10; i++) {
            str = str + i;
            Message mssage = MessageBuilder.withPayload(str).setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build();
            msgs.add(mssage);
        }
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, msgs, 60000);
        logger.info("Batch messages send result:{}", JSON.toJSONString(sendResult));
        return Result.ok();
    }


    /***
     * 延时队列 testSendBatchMessageOrderly
     * @return
     */
    public Result testSendBatchMessageOrderly() {

        for (int q = 0; q < 4; q++) {
            List<Message> msgs = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                int msgIndex = q * 10 + i;
                String msg = String.format("Hello RocketMQ Batch Msg#%d to queue: %d", msgIndex, q);

                Message<String> message = MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.KEYS, "KEY_" + msgIndex).build();
                msgs.add(message);
            }
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(springTopic, msgs, q + "", 60000);
            logger.info("Batch messages orderly to queue:{},发送结果为:{}", sendResult.getMessageQueue().getQueueId(), JSON.toJSONString(sendResult));
        }

        return Result.ok();
    }


    /**
     * testRocketMQTemplateTransaction 测试
     *
     * @return
     */
    public Result testRocketMQTemplateTransaction() {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

        for (int i = 0; i < 10; i++) {
            try {
                Message msg = MessageBuilder.withPayload("rocketMQTemplate transactional message" + i).setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(springTopic + ":" + tags[i % tags.length], msg, null);
                logger.info("rocketMQTemplate send Transactional msg body:{},sendResult:{}", msg.getPayload(), sendResult.getSendStatus());
                Thread.sleep(10);
            } catch (Exception e) {
                logger.error("异常信息为:{}", e.getMessage());
            }
        }
        return Result.ok();
    }

    public Result testExtRocketMQTemplateTransaction() {
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = MessageBuilder.withPayload("extRocketMQTemplate transactional message" + i).setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
                SendResult sendResult = extRocketMQTemplate.sendMessageInTransaction(springTransTopic, msg, null);
                logger.info("testExtRocketMQTemplateTransaction 发送事务消息 body:{},发送结果sendResult:{}", msg.getPayload(), JSON.toJSONString(sendResult.getSendStatus()));

                Thread.sleep(10);
            } catch (Exception e) {
                logger.error("方法testExtRocketMQTemplateTransaction中 消息发送异常,异常信息为:{}", e.getMessage());
            }
        }
        return Result.ok();
    }


    /***
     *  testStringRequestTopic 测试
     * @return
     */
    public Result testStringRequestTopic() {

        String replyString = rocketMQTemplate.sendAndReceive(stringRequestTopic, "request string", String.class);

        logger.info("发送内容:{},发送结果:{}", "request string", replyString);

        return Result.ok();
    }


    /***
     *  testBytesRequestTopic 测试
     * @return
     */
    public Result testBytesRequestTopic() {
        Message<String> message = MessageBuilder.withPayload("request byte[]").build();

        byte[] replyBytes = rocketMQTemplate.sendAndReceive(bytesRequestTopic, message, byte[].class, 3000);
        logger.info("发送内容:{},发送结果为:{}", "request byte[]", new String(replyBytes));

        return Result.ok();
    }


    /***
     *  testObjectRequestTopic 测试
     * @return
     */
    public Result testObjectRequestTopic() {

        User requestUser = new User();
        requestUser.setUserAge(9);
        requestUser.setUserName("requestUserName");

        User replyUser = rocketMQTemplate.sendAndReceive(objectRequestTopic, requestUser, User.class, "order-id");
        logger.info("发送消息为:{},接收消息为:{}", JSON.toJSONString(requestUser), JSON.toJSONString(replyUser));

        return Result.ok();
    }


    /***
     *   testGenericRequestTopic 测试
     * @return
     */
    public Result testGenericRequestTopic() {
        String content = "request generic";

        ProductWithPayload<String> replyGenericObject = rocketMQTemplate.sendAndReceive(genericRequestTopic, content, new TypeReference<ProductWithPayload<String>>() {
        }.getType(), 30000, 2);

        logger.info("发送内容:{},发送结果为:{}", content, replyGenericObject);

        return Result.ok();
    }


    /***
     * testStringRequestTopicBySendAndReceive
     * @return
     */
    public Result testStringRequestTopicBySendAndReceive() {

        String content = "request string";

        rocketMQTemplate.sendAndReceive(stringRequestTopic, content, new RocketMQLocalRequestCallback() {
            @Override
            public void onSuccess(Object obj) {
                logger.info("发送内容:{},接收消息为:{}", JSON.toJSONString(content), JSON.toJSONString(obj));
            }

            @Override
            public void onException(Throwable throwable) {
                logger.error("异常信息为:{}", throwable.getMessage());
            }
        });

        return Result.ok();
    }

    public Result testObjectRequestTopicBySendAndReceive() {
        User user = new User();
        user.setUserAge(9);
        user.setUserName("requestUserName");

        rocketMQTemplate.sendAndReceive(objectRequestTopic, user, new RocketMQLocalRequestCallback<User>() {
            @Override
            public void onSuccess(User message) {
                logger.info("接收与发送的消息为:{}", JSON.toJSONString(message));
            }

            @Override
            public void onException(Throwable throwable) {
                logger.error("异常消息为:{}", throwable.getMessage());
            }
        }, 5000);

        return Result.ok();
    }


}
