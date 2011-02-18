package com.elgoooog.depin.parser.model;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 4:51 PM
 */
public abstract class BaseBean implements Bean {
    private Class<?> clazz;
    private String id;
    private Args args;

    public BaseBean(Class<?> c) {
        clazz = c;
        args = new Args();
    }

    public BaseBean(String className) {
        try {
            clazz = Class.forName(className);
            args = new Args();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> getBeanClass() {
        return clazz;
    }

    @Override
    public void setId(String i) {
        id = i;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addArg(Arg a) {
        args.add(a);
    }

    @Override
    public Args getArgs() {
        return args;
    }
}
