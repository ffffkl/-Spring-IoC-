# Spring-IoC
手写代码的思路：
在不导入Spring依赖的前提下，自定义注解@Component、@Value、@Autowired、@Qualifier，并实现类似于Spring的功能。

1、自定义一个 MyAnnotationConfigApplicationContext，构造器中传入要扫描的包。

2、获取这个包下的所有类。

3、遍历这些类，找出添加了 @Component 注解的类，获取它的 Class 和对应的 beanName，封装成一个 BeanDefinition，存入集合 Set，这个机会就是 IoC 自动装载的原材料。

4、遍历 Set 集合，通过反射机制创建对象，同时检测属性有没有添加 @Value 注解，如果有还需要给属性赋值，再将这些动态创建的对象以 k-v 的形式存入缓存区。

5、提供 getBean 等方法，通过 beanName 取出对应的 bean 即可。