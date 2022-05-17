package com.fkl.myspring.entity;

import com.fkl.myspring.Component;
import com.fkl.myspring.Value;
import lombok.Data;

@Data
@Component("myOrder")

public class Order {
    @Value("222")
    private String orderId;
    @Value("10.1")
    private Float price;
}
