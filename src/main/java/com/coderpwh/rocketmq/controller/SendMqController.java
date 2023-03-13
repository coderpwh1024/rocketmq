package com.coderpwh.rocketmq.controller;

import com.coderpwh.rocketmq.service.AclProducerService;
import com.coderpwh.rocketmq.service.ProducerService;
import com.coderpwh.rocketmq.service.SendService;
import com.coderpwh.rocketmq.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author coderpwh
 * @date 2022/12/28 15:52
 */

@RestController
@RequestMapping(value = "/send")
public class SendMqController {

    @Resource
    private SendService sendService;


    @Resource
    private ProducerService producerService;


    @Resource
    private AclProducerService aclProducerService;


    /***
     *  mq简单示例
     * @return
     */
    @RequestMapping(value = "/quick_start", method = RequestMethod.GET)
    public Result quickStart() {
        return sendService.quickStart();
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result testProducer() {
        return producerService.testProducer();
    }

    @RequestMapping(value = "/acl_test", method = RequestMethod.POST)
    public Result testAcl() {
        return aclProducerService.testAclProducer();
    }


}
