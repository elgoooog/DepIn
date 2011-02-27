package com.elgoooog.depin.model;

import com.elgoooog.depin.util.ConstructorUtil;

import java.lang.reflect.Constructor;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 11:02 PM
 */
public class SingletonBean extends BaseBean {
    private Object instance;

    public SingletonBean(String clazz) {
        super(clazz);
    }

    @Override
    public Object getInstance() {
        if (instance == null) {
            System.out.println("Creating singleton " + getId() + " (" + getBeanClass() + ")");
            Constructor<?> constructor = ConstructorUtil.findProperConstructor(getBeanClass(), getArgs().getTypes());
            instance = createInstance(constructor);
        }

        return instance;
    }
}
