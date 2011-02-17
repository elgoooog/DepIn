package com.elgoooog.depin.parser.model;

import com.elgoooog.depin.parser.util.ConstructorUtil;

import java.lang.reflect.Constructor;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 4:51 PM
 */
public class Bean {
    private Class<?> clazz;
    private String id;
    private Args args;

    private Constructor<?> constructor;

    public Bean(Class<?> c) {
        clazz = c;
        args = new Args();
    }

    public Bean(String className) {
        try {
            clazz = Class.forName(className);
            args = new Args();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getBeanClass() {
        return clazz;
    }

    public void setId(String i) {
        id = i;
    }

    public String getId() {
        return id;
    }

    public void addArg(Arg a) {
        args.add(a);
    }

    public Args getArgs() {
        return args;
    }

    public Object getInstance() {
        if (constructor == null) {
            try {
                constructor = ConstructorUtil.findProperConstructor(clazz, args.getTypes());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return constructor.newInstance(args.getVals().toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
