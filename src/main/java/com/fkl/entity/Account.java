package com.fkl.entity;

import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

@Data
//@Component
public class Account {
   // @Value("1")
    private int id;
  //  @Value("fff")
    private String name;
   // @Value("2")
    private int age;
   // @Autowired
    private Order order;
}
