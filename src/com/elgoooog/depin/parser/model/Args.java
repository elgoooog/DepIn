package com.elgoooog.depin.parser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:46 AM
 */
public class Args {
    private List<Arg> args;

    public Args(List<Arg> a) {
        args = a;
    }

    public Args() {
        this(new ArrayList<Arg>());
    }

    public void add(Arg a) {
        args.add(a);
    }

    public Arg get(int index) {
        return args.get(index);
    }

    public List<Class<?>> getTypes() {
        List<Class<?>> types = new ArrayList<Class<?>>();
        for (Arg arg : args) {
            types.add(arg.getType());
        }
        return types;
    }

    public List<Object> getVals() {
        List<Object> values = new ArrayList<Object>();
        for (Arg arg : args) {
            values.add(arg.getValue());
        }
        return values;
    }
}
