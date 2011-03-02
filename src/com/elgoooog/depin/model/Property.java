package com.elgoooog.depin.model;

import com.elgoooog.depin.util.AccessibleObjectUtil;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @author Nicholas Hauschild
 *         Date: 2/28/11
 *         Time: 7:53 PM
 */
public class Property {
    private String setterName;
    private String getterName;
    private Bean ref;

    public Property(String n, Bean r) {
        String base = Character.toUpperCase(n.charAt(0)) + n.substring(1);
        setterName = "set" + base;
        getterName = "get" + base;
        ref = r;
    }

    public void setOn(Object instance) {
        try {
            Method method = AccessibleObjectUtil.findProperMethod(instance.getClass(),
                    Collections.<Class<?>>singletonList(ref.getBeanClass()), setterName);
            method.invoke(instance, ref.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getFrom(Object instance) {
        try {
            Method method = AccessibleObjectUtil.findProperMethod(instance.getClass(),
                    Collections.<Class<?>>emptyList(), getterName);
            return method.invoke(instance);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
