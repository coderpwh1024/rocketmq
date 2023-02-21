package com.coderpwh.rocketmq.mq.producer;

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

    }


}
