package com.xhkj.server.energy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BeanUtils {

    private static Logger log = LoggerFactory.getLogger(BeanUtils.class);

    public static <T> void copyProperties(T source, T target, boolean ignoreNull) {
        BeanInfo targetBeanInfo = null;
        BeanInfo sourceBeanInfo = null;
        try {
            targetBeanInfo = Introspector.getBeanInfo(target.getClass());
            sourceBeanInfo = Introspector.getBeanInfo(source.getClass());
        } catch (IntrospectionException e) {
            log.warn("Introspector.getBeanInfo occrued an exception", e);
        }
        PropertyDescriptor[] targetBeanInfoPropertyDescriptors = targetBeanInfo.getPropertyDescriptors();
        PropertyDescriptor[] sourcePropertyDescriptors = sourceBeanInfo.getPropertyDescriptors();
        for (PropertyDescriptor targgetPropertyDescriptor : targetBeanInfoPropertyDescriptors) {
            for (PropertyDescriptor sourcePropertyDescriptor : sourcePropertyDescriptors) {
                if (targgetPropertyDescriptor.getName().equals(sourcePropertyDescriptor.getName())) {
                    Method readMethod = sourcePropertyDescriptor.getReadMethod();
                    Method writeMethod = targgetPropertyDescriptor.getWriteMethod();
                    if (isPublic(readMethod, writeMethod)) {
                        try {
                            Object value = readMethod.invoke(source, null);
                            if (value == null && ignoreNull) {
                                continue;
                            }
                            writeMethod.invoke(target, value);
                        } catch (Exception e) {
                            log.warn("readMethod or writeMethod  occrued an exception", e);
                        }
                    }
                }
            }
        }
    }

    // 只能相同class的bean拷贝
    public static <T> void copyProperties2(T source, T target, boolean ignoreNull) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(target.getClass());
        } catch (IntrospectionException e) {
//			throw XException.XExceptionBuilder.newBuilder(e).build();
            e.printStackTrace();// todo
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (isPublic(readMethod, writeMethod)) {
                try {
                    Object value = readMethod.invoke(source, null);
                    if (value == null && ignoreNull) {
                        continue;
                    }
                    writeMethod.invoke(target, value);

                } catch (Exception e) {
//					throw XException.XExceptionBuilder.newBuilder(e).build();
                    e.printStackTrace();// todo
                }
            }
        }
    }

    private static boolean isPublic(Method... methods) {
        if (methods == null) {
            return false;
        }
        for (Method method : methods) {
            if (method == null) {
                return false;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
