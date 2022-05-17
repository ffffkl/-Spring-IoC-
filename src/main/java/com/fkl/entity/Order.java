package com.fkl.entity;

import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

@Data
//@Component
public class Order {
 //   @Value("123")
    private String orderId;
   // @Value("10.0")
    private Float price;
}
