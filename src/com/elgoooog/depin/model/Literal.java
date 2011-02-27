package com.elgoooog.depin.model;

import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:23 AM
 */
public class Literal implements Arg {
    private Class<?> type;
    private Object value;

    public Literal(Class<?> t, Object v) {
        type = t;
        value = v;
    }

    public Literal(Object v) {
        this(v.getClass(), v);
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateDependants(Set<Bean> dependants) {
        //
    }
}
