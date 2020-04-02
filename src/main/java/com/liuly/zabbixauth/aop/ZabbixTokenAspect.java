package com.liuly.zabbixauth.aop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liuly.zabbixauth.annotation.DataStaticWiring;
import com.liuly.zabbixauth.annotation.ZabbixToken;
import com.liuly.zabbixauth.util.SessionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/20
 * @since JDK 1.8
 */
@Aspect
@Component
public class ZabbixTokenAspect {

    @Autowired
    private SessionUtils sessionUtils;

    @Before("annotationPointCut()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @Pointcut("@annotation(com.liuly.zabbixauth.annotation.ZabixAuthToken)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //获取锁所切方法的签名
        MethodSignature sign = (MethodSignature) pjp.getSignature();
        //或者获取该登录者的session
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        //获取切面方法
        Method method = sign.getMethod();
        //获取方法中的参数
        Object[] args = pjp.getArgs();
        //获取参数中的注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //获取参数中的变量
        Parameter[] parameters = method.getParameters();
        //循环参数列表，为带有@ZabbixToken 注解的参数赋值
        for (int num = 0; num < args.length; num++) {
            if (args[num] != null) {
                //获取传参数类型是String类型，还是基本类型，还是实体类型，根据不同类型做不同的处理
                Class<?> cls = args[num].getClass();
                if (cls.isAssignableFrom(String.class)) {
                    for (Annotation an : parameterAnnotations[num]) {
                        if (an instanceof ZabbixToken) {
                            args[num] = DataStaticWiring.acObjectValue(session, sessionUtils);
                        }
                    }
                } else if (cls.isPrimitive()) {
                } else if(List.class.isAssignableFrom(cls)){
                      parasValue(parameters[num],args[num], session ,false);
                } else {
                    for (Field field : cls.getDeclaredFields()) {
                        if (field.isAnnotationPresent(ZabbixToken.class) && field.getType().isAssignableFrom(String.class)) {
                            DataStaticWiring.wiring(args[num], field, session, sessionUtils);
                        }
                    }
                }
            } else {//当参数类型为String直接赋值
                Class<?> cls = parameters[num].getType();
                if (cls.isAssignableFrom(String.class)) {
                    for (Annotation an : parameterAnnotations[num]) {
                        if (an instanceof ZabbixToken && ((ZabbixToken) an).notNull()) {
                            args[num] = DataStaticWiring.acObjectValue(session, sessionUtils);
                        }
                    }//类型为基本类型忽略
                } else if (cls.isPrimitive()) {
                }else if(List.class.isAssignableFrom(cls)){
                    //类型为List类型，则进行遍历看是否实体里的属性是否含有@ZabbixToken注解，有则赋值
                    for (Annotation an : parameterAnnotations[num]) {
                        if (an instanceof ZabbixToken && ((ZabbixToken) an).notNull()) {
                            args[num] = parasValue(parameters[num] ,args[num], session ,true);
                        }
                    }
                } else {//类型为实体里的属性是否含有@ZabbixToken注解，有则为该属性赋值
                    boolean isPresentZabbiToken = false;
                    Object instance = null;
                    for (Annotation an : parameterAnnotations[num]) {
                        if (an instanceof ZabbixToken && ((ZabbixToken) an).notNull()) {
                            instance = cls.newInstance();
                            for (Field field : cls.getDeclaredFields()) {
                                if (field.isAnnotationPresent(ZabbixToken.class) && field.getType().isAssignableFrom(String.class)) {
                                    DataStaticWiring.wiring(instance, field, session, sessionUtils);
                                    isPresentZabbiToken = true;
                                }
                            }
                        }
                    }
                    if (isPresentZabbiToken) args[num] = instance;
                }
            }
        }
        return pjp.proceed(args);
    }

    @AfterReturning(value = "annotationPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

    }
     //通过获取方法的参数，并为实List中的实体带有@ZabbixToken注解赋值
    private List parasValue(Parameter parameter, Object value ,HttpSession session, boolean isNull) throws Exception {
        Class<?>cls = parameter.getType();//参数的类型 反射为List.class
        //获取java反射获取参数化类型的class实例（泛型的类型）
        ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
        Class clazz = (Class)parameterizedType.getActualTypeArguments()[0];
        List newList = null;
        // 获取该参数实体的所用属性
        Field[] fields = clazz.getDeclaredFields();
        if (!isNull) {//获取集合长度
            int size = typeCapacity(value);
            for (int i = 0; i < size; i++) {
                //反射获取到list的get方法
                Method getMethod = cls.getDeclaredMethod("get", int.class);
                //调用get方法获取数据
                if(!getMethod.isAccessible()){
                    getMethod.setAccessible(true);
                }
                Object var = getMethod.invoke(value, i);
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ZabbixToken.class)) {
                        Class<?> fieldType = field.getType();
                        if (fieldType.isAssignableFrom(String.class)) {
                            DataStaticWiring.wiring(var, field, session, sessionUtils);
                        }
                    }
                }
            }
        }else {
            // 实例化一条数据
            List list = new ArrayList<>();
            Class listClass = list.getClass();
            Method methodList = listClass.getMethod("add", Object.class);
            Object target = clazz.newInstance();
            boolean isZabbixToken = false;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ZabbixToken.class)) {
                    isZabbixToken = true;
                    Class<?> fieldType = field.getType();
                    if (fieldType.isAssignableFrom(String.class)) {
                        DataStaticWiring.wiring(target, field, session, sessionUtils);
                    }
                }
            }
            methodList.invoke(list, target);
            if(isZabbixToken) newList = list;
        }
        return newList;
    }
    //反射获取list集合的长度
    public static int typeCapacity(Object value) {
        try {
            Class<?> clzz = value.getClass();
            Method sizeMethod = clzz.getDeclaredMethod("size");
            if(!sizeMethod.isAccessible()){
                sizeMethod.setAccessible(true);
            }
            //集合长度
            int size = (int) sizeMethod.invoke(value);
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
