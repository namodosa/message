package com.namolog.message.security.common;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CommonUtil {

    public static boolean existClassMethod(String classMethodName) {
        int lastDotIndex = classMethodName.lastIndexOf(".");
        if (lastDotIndex == -1)
            return false;
        String methodName = classMethodName.substring(lastDotIndex + 1);
        String className = classMethodName.substring(0, lastDotIndex);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // class 가 없으면 map 에 등록하지 않고 패스~
            return false;
        }
        if (clazz == null)
            return false;
        try {
            Method m = clazz.getMethod(methodName, (Class<?>) null);
        } catch (NoSuchMethodException | SecurityException e) {
            // class 가 없으면 map 에 등록하지 않고 패스~
            return false;
        }
        return true;
    }
}
