package com.coderpwh.rocketmq.mq.consumer;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.trace.hook.ConsumeMessageOpenTracingHookImpl;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/21 16:01
 */
public class OpenTracingPushConsumer {

    private static Logger logger = LoggerFactory.getLogger(OpenTracingPushConsumer.class);

    public static final String CONSUMER_GROUP = "CID_JODIE_1";
    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";
    public static final String TOPIC = "TopicTest";


    public static void consumer() {
        try {
            Tracer tracer = initTracer();
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            consumer.getDefaultMQPushConsumerImpl().registerConsumeMessageHook(new ConsumeMessageOpenTracingHookImpl(tracer));

            consumer.subscribe(TOPIC, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setConsumeTimestamp("202302211600");
            consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
                logger.info("Receive new Messages: 线程名:{},消息体:{}", Thread.currentThread().getName(), msg);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        } catch (Exception e) {
            logger.error("消费端异常,异常信息为:{}", e.getMessage());
        }

    }

    public static void main(String[] args) {
        consumer();
    }


    private static Tracer initTracer() {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);

        Configuration config = new Configuration("rocketmq").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(config.getTracer());
        return config.getTracer();
    }

}
