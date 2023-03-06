package com.coderpwh.rocketmq.mq.boot.producer;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * RocketMQTemplate 额外配置类
 *
 * @author coderpwh
 * @date 2023/3/6 17:22
 */
@ExtRocketMQTemplateConfiguration(nameServer = "${demo.rocketmq.extNameServer}", tlsEnable = "${demo.rocketmq.ext.useTLS}", instanceName = "pztest33")
public class ExtRocketMQTemplate extends RocketMQTemplate {


}
