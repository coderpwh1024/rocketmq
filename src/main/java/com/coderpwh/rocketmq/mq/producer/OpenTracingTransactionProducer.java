package com.coderpwh.rocketmq.mq.producer;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
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


    private static Tracer initTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);

        Configuration config = new Configuration("rocketmq").withSampler(samplerConfig).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(config.getTracer());

        return config.getTracer();
    }


}
