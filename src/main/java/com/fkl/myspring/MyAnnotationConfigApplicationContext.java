package com.fkl.myspring;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyAnnotationConfigApplicationContext {
    //ioc容器
    private Map<String, Object> ioc = new HashMap<>();
    public MyAnnotationConfigApplicationContext(String pack){
        //遍历包，找到目标类(原材料)

        Set<BeanDefinition> beanDefinitions = findBeanDefinitions(pack);
        createObject(beanDefinitions);
        //自动装载
        autoObject(beanDefinitions);
    }

    public void autoObject(Set<BeanDefinition> beanDefinitions){
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class clazz = beanDefinition.getBeanClass();
            //获得类的所有属性
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if(annotation != null){
                    Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);

                    if(qualifier != null){
                        //byName
                        //System.out.println(declaredField);
                        String value = qualifier.value();
                        Object bean = getBean(value);
                        //System.out.println(bean);
                        String fieldName = declaredField.getName();
                        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                        try {
                            Method method = clazz.getMethod(methodName, declaredField.getType());
                            Object object = getBean(beanDefinition.getBeanName());
                            System.out.println("-------------自动装配前-------------");
                            System.out.println("对象：" + object);
                            System.out.println("属性：" + bean);
                            method.invoke(object, bean);
                            System.out.println("-------------自动装配后-------------");
                            System.out.println("对象：" + object);
                            System.out.println("属性：" + bean);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }else {
                        //byType
                    }
                }
            }
        }
    }

    public Object getBean(String beanName){
        return ioc.get(beanName);
    }


    public void createObject(Set<BeanDefinition> beanDefinitions){
        //创建ioc对象
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            //beanDefinition中的get方法获取值
            Class clazz = beanDefinition.getBeanClass();
            String beanName = beanDefinition.getBeanName();
            try {
                //通过无参构造创建对象
                Object object = clazz.getConstructor().newInstance();
                //添加到容器之前完成属性的赋值
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Value valueAnnotation = declaredField.getAnnotation(Value.class);
                    if(valueAnnotation != null){
                        //如果有Value注解，就给对应的属性赋值
                        String value = valueAnnotation.value();
                        String fieldName = declaredField.getName();
                        //通过set方法赋值
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                       // System.out.println(methodName);
                        //获得方法
                        Method method = clazz.getMethod(methodName, declaredField.getType());
                        //完成数据类型转换
                        Object val = null;
                        switch (declaredField.getType().getName()){
                            case "java.lang.Integer" :
                                val = Integer.parseInt(value);
                                break;
                            case "java.lang.Float" :
                                val = Float.parseFloat(value);
                                break;
                            case "java.lang.String" :
                                val = value;
                        }
                        //通过invoke()反射调用方法
                        method.invoke(object, val);
                    }
                }
                ioc.put(beanName, object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public Set<BeanDefinition> findBeanDefinitions(String pack){
        //1、获取包下的所有类
        //2、遍历这些类，找到添加了注解的类
        //3、将这些类封装成BeanDefinition，装载到集合中
        Set<Class<?>> classes = MyTools.getClasses(pack);
        Iterator<Class<?>> iterator = classes.iterator();
        //set去重
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        while (iterator.hasNext()) {
            //clazz 是全路径类名：  class com.fkl.myspring.entity.Account
            Class<?> clazz = iterator.next();
            //判断类有没有添加注解，这里的Component是自定义的注解
            //componentAnnotation的值为@com.fkl.myspring.Component(value=a)
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            if(componentAnnotation != null){
                String beanName = componentAnnotation.value();
                if("".equals(beanName)){
                    //将类名首字母转小写，获得beanName
                    String className = clazz.getSimpleName();
                    beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                    System.out.println("beanName : " + beanName);
                }
               ;
                BeanDefinition beanDefinition = new BeanDefinition(beanName, clazz);
                beanDefinitions.add(beanDefinition);
            }

        }
        return beanDefinitions;
    }
}
