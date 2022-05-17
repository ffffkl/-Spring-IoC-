package com.fkl.myspring.entity;

import com.fkl.myspring.Autowired;
import com.fkl.myspring.Component;
import com.fkl.myspring.Qualifier;
import com.fkl.myspring.Value;
import lombok.Data;

@Data
@Component("")
public class Account {
    @Value("1")
    private Integer id;
    @Value("ljr")
    private String name;
    @Value("3")
    private Integer age;
    @Autowired
    @Qualifier("myOrder")
    private Order order;
}
