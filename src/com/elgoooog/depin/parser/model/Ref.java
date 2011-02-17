package com.elgoooog.depin.parser.model;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:23 AM
 */
public class Ref implements Arg {
    private String ref;

    public Ref(String r) {
        ref = r;
    }

    @Override
    public Class<?> getType() {
        return Beans.getBean(ref).getBeanClass();
    }

    @Override
    public Object getValue() {
        return Beans.getBean(ref).getInstance();
    }
}
