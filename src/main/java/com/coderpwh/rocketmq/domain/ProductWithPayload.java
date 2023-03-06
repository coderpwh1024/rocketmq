package com.coderpwh.rocketmq.domain;

import lombok.Data;

/**
 * @author coderpwh
 * @date 2023/3/6 16:24
 */
@Data
public class ProductWithPayload<T> {

    private String productName;

    private T payload;


}
