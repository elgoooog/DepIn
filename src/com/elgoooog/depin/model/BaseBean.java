package com.elgoooog.depin.model;

import com.elgoooog.depin.util.CycleChecker;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
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
    private List<InjectedField> injectedFields;
    private List<Property> properties;

    public BaseBean(String className) {
        try {
            clazz = Class.forName(className);
            args = new Args();
            cycleChecker = new CycleChecker(this);
            injectedFields = new ArrayList<InjectedField>();
            properties = new ArrayList<Property>();
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
    public void addInjectedField(InjectedField field) {
        injectedFields.add(field);
    }

    @Override
    public void addProperty(Property p) {
        properties.add(p);
    }

    @Override
    public Args getArgs() {
        return args;
    }

    public Set<Bean> getDependants() {
        return cycleChecker.getDependants();
    }

    protected Object createInstance(Constructor<?> constructor) {
        try {
            Object instance = constructor.newInstance(getArgs().getVals().toArray());

            for(Property property : properties) {
                property.setOn(instance);
            }

            for(InjectedField injectedField : injectedFields) {
                injectedField.injectInto(instance);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
