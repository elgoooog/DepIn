package com.elgoooog.depin.parser.model;

import com.elgoooog.depin.parser.util.CycleChecker;

import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 4:51 PM
 */
public abstract class BaseBean implements Bean {
    private Class<?> clazz;
    private String id;
    private Args args;
    private CycleChecker cycleChecker;

    public BaseBean(String className) {
        try {
            clazz = Class.forName(className);
            args = new Args();
            cycleChecker = new CycleChecker(this);
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
    public void addArg(Arg arg) {
        args.add(arg);
        cycleChecker.checkForCycle(arg);
    }

    @Override
    public Args getArgs() {
        return args;
    }

    public Set<Bean> getDependants() {
        return cycleChecker.getDependants();
    }
}
