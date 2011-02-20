package com.elgoooog.depin.parser.model;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:23 AM
 */
public class Ref implements Arg {
    private Bean ref;

    public Ref(Bean r) {
        ref = r;
    }

    @Override
    public Class<?> getType() {
        return ref.getBeanClass();
    }

    @Override
    public Object getValue() {
        return ref.getInstance();
    }
}
