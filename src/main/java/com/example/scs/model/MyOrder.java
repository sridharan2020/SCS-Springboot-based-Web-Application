package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyOrder {
    private Long myOrderId;
    private String orderId;
    private String amount;
    private String receipt;
    private String status;
    private Integer user_id;
    private String paymentId;
}

