package com.elgoooog.depin.model;

import com.elgoooog.depin.util.ConstructorUtil;

import java.lang.reflect.Constructor;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 11:00 PM
 */
public class PrototypeBean extends BaseBean {
    private Constructor<?> constructor;

    public PrototypeBean(String clazz) {
        super(clazz);
    }

    @Override
    public Object getInstance() {
        if (constructor == null) {
            constructor = ConstructorUtil.findProperConstructor(getBeanClass(), getArgs().getTypes());
        }

        System.out.println("Creating prototype " + getId() + " (" + getBeanClass() + ")");
        return createInstance(constructor);
    }
}
