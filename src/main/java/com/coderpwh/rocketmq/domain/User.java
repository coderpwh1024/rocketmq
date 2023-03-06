package com.coderpwh.rocketmq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author coderpwh
 * @date 2023/3/6 16:21
 */
@Data
public class User implements Serializable {

    private String userName;

    private Integer userAge;


}
