package com.coderpwh.rocketmq.controller;

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


    /***
     *  mq简单示例
     * @return
     */
    @RequestMapping(value = "/quick_start", method = RequestMethod.GET)
    public Result quickStart() {
        return sendService.quickStart();
    }


}
