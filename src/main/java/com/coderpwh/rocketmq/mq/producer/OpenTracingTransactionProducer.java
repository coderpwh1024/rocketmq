package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.trace.hook.EndTransactionOpenTracingHookImpl;
import org.apache.rocketmq.client.trace.hook.SendMessageOpenTracingHookImpl;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/21 17:01
 */
public class OpenTracingTransactionProducer {

    private static Logger logger = LoggerFactory.getLogger(OpenTracingTransactionProducer.class);

    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TopicTest";

    public static final String TAG = "Tag";

    public static final String KEY = "KEY";

    public static final int MESSAGE_COUNT = 100000;


    public static void main(String[] args) {

        try {
            Tracer tracer = initTracer();
            TransactionMQProducer producer = new TransactionMQProducer(PRODUCER_GROUP);
            producer.getDefaultMQProducerImpl().registerSendMessageHook(new SendMessageOpenTracingHookImpl(tracer));
            producer.getDefaultMQProducerImpl().registerEndTransactionHook(new EndTransactionOpenTracingHookImpl(tracer));

            producer.setTransactionListener(new TransactionListener() {
                @Override
                public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }

                @Override
                public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
            });
            producer.start();
            Message msg = new Message(TOPIC, TAG, KEY, "Hello RocketMQ".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            logger.info("发送结果为:{}", JSON.toJSONString(sendResult));

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                Thread.sleep(1000);
            }
            producer.shutdown();
        } catch (Exception e) {
            logger.error("异常消息为:{}", e.getMessage());
        }


    }


    private static Tracer initTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);

        Configuration config = new Configuration("rocketmq").withSampler(samplerConfig).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(config.getTracer());

        return config.getTracer();
    }


}
