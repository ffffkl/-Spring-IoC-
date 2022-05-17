package com.fkl.myspring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeanDefinition {
    //BeanDefinition存入ioc容器中
    //beanName的值：如果Component不写，就是类名首字母小写加剩余部分，如果写了就是对应的值
    private String beanName;
    private Class beanClass;
}
