package com.elgoooog.depin.model;

import java.lang.reflect.Field;

/**
 * @author Nicholas Hauschild
 *         Date: 2/26/11
 *         Time: 7:29 PM
 */
public class InjectedField {
    private Field field;
    private Bean refBean;

    public InjectedField(Field f, Bean b) {
        field = f;
        refBean = b;
    }

    public void injectInto(Object o) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(o, refBean.getInstance());
    }
}
