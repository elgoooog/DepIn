package com.elgoooog.depin.parser.util;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 11:54 PM
 */
public class ConstructorUtil {
    public static Constructor<?> findProperConstructor(Class<?> clazz, List<Class<?>> types) throws NoSuchMethodException {
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
}
