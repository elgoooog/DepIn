package com.elgoooog.depin.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 11:54 PM
 */
public class AccessibleObjectUtil {
    public static Constructor<?> findProperConstructor(Class<?> clazz, List<Class<?>> types) {
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            if (isProperConstructor(constructor, types)) {
                return constructor;
            }
        }

        return null;
    }

    protected static boolean isProperConstructor(Constructor<?> constructor, List<Class<?>> types) {
        Class<?>[] constructorParams = constructor.getParameterTypes();

        if (constructorParams.length != types.size()) {
            return false;
        }

        for (int i = 0; i < types.size(); ++i) {
            if (!constructorParams[i].isAssignableFrom(types.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static Method findProperMethod(Class<?> clazz, List<Class<?>> types, String methodName) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (methodName.equals(method.getName()) && isProperMethod(method, types)) {
                return method;
            }
        }

        return null;
    }

    protected static boolean isProperMethod(Method method, List<Class<?>> types) {
        Class<?>[] methodParams = method.getParameterTypes();

        if (methodParams.length != types.size()) {
            return false;
        }

        for (int i = 0; i < types.size(); ++i) {
            if (!methodParams[i].isAssignableFrom(types.get(i))) {
                return false;
            }
        }
        return true;
    }
}
