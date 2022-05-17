package com.fkl.myspring;

public class Test1 {
    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext applicationContext = new MyAnnotationConfigApplicationContext("com.fkl.myspring.entity");
        //System.out.println(applicationContext.getBean("myOrder"));

    }
}
