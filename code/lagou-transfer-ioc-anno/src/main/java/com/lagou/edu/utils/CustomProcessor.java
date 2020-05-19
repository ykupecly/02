package com.lagou.edu.utils;

import com.lagou.edu.annotation.CustomAutowired;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class CustomProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    //bean初始化之前要调用的方法，这里直接返回
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    //bean初始化之后要调用的方法，这里判断如果有自定义注解@CustomAutowired，做进一步处理
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targeClass = bean.getClass();
        Field[] fields = targeClass.getDeclaredFields();
        for (Field field: fields ) {
            if (field.isAnnotationPresent(CustomAutowired.class)) {  //判断属性是否是自定义注解@CustomAutowired
                    try {
                        //为属性赋值
                        this.hanldCustomAutowired(field, bean, field.getType());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
            }
        }
        return bean;
    }

    private void hanldCustomAutowired(Field field, Object bean, Class type) throws IllegalAccessException {
        //获取所有该属性接口的实例bean
        Map<String,Object> beans = this.applicationContext.getBeansOfType(type);
        //设置该域可设置修改
        field.setAccessible(true);
        //获取注解@CustomAutowired中配置的value值
        String injectVal = field.getAnnotation(CustomAutowired.class).value();
        //将找到的实例赋值给属性域
        field.set(bean,beans.get(injectVal));
    }
}