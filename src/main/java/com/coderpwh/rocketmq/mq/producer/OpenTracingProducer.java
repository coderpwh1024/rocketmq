package com.coderpwh.rocketmq.mq.producer;

import com.alibaba.fastjson.JSON;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.trace.hook.SendMessageOpenTracingHookImpl;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author coderpwh
 * @date 2023/2/21 10:58
 */
public class OpenTracingProducer {


    private static Logger logger = LoggerFactory.getLogger(OpenTracingProducer.class);

    public static final String PRODUCER_GROUP = "ProducerGroupName";

    public static final String DEFAULT_NAMESRVADDR = "120.79.226.167:9876";

    public static final String TOPIC = "TopicTest";

    public static final String TAG = "TagA";

    public static final String KEY = "OrderID188";


    public static void main(String[] args) {

        try {
            Tracer tracer = initTracer();

            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
            producer.setCreateTopicKey("TBW102");
            producer.setSendMsgTimeout(60000);
            producer.getDefaultMQProducerImpl().registerSendMessageHook(new SendMessageOpenTracingHookImpl(tracer));
            producer.start();

            Message msg = new Message(TOPIC, TAG, KEY, "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = producer.send(msg);
            logger.info("发送结果为：{}", JSON.toJSONString(sendResult));

            producer.shutdown();
        } catch (Exception e) {
            logger.error("异常消息为:{}", e.getMessage());
        }


    }


    private static Tracer initTracer() {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);

        Configuration config = new Configuration("rocketmq").withSampler(samplerConfiguration).withReporter(reporterConfig);

        GlobalTracer.registerIfAbsent(config.getTracer());
        return config.getTracer();
    }


}
