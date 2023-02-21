package com.coderpwh.rocketmq.mq.producer;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
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

        Tracer tracer = initTracer();

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.setCreateTopicKey("TBW102");
        producer.setSendMsgTimeout(60000);


    }


    private static Tracer initTracer() {
        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);

        Configuration config = new Configuration("rocketmq").withSampler(samplerConfiguration).withReporter(reporterConfig);

        GlobalTracer.registerIfAbsent(config.getTracer());
        return config.getTracer();
    }


}
