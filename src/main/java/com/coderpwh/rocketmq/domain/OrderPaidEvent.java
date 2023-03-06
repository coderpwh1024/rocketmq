package com.coderpwh.rocketmq.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author coderpwh
 * @date 2023/3/6 16:23
 */
@Data
public class OrderPaidEvent implements Serializable {

    private String orderId;

    private BigDecimal paidMoney;


}
