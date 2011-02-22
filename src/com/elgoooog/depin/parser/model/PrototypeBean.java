package com.elgoooog.depin.parser.model;

import com.elgoooog.depin.parser.util.ConstructorUtil;

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
            try {
                constructor = ConstructorUtil.findProperConstructor(getBeanClass(), getArgs().getTypes());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            System.out.println("Creating prototype " + getId() + " (" + getBeanClass() + ")");
            return constructor.newInstance(getArgs().getVals().toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
