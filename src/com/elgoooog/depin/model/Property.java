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
    private Arg arg;
    private Class<?> clazz;
    private Method setterMethod;
    private Method getterMethod;

    public Property(Class<?> c, String n, Arg a) {
        String base = Character.toUpperCase(n.charAt(0)) + n.substring(1);
        setterName = "set" + base;
        getterName = "get" + base;
        arg = a;
        clazz = c;
    }

    public void setOn(Object instance) {
        checkType(instance.getClass());
        try {
            if(setterMethod == null) {
                setterMethod = AccessibleObjectUtil.findProperMethod(clazz,
                    Collections.<Class<?>>singletonList(arg.getType()), setterName);
            }
            setterMethod.invoke(instance, arg.getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getFrom(Object instance) {
        checkType(instance.getClass());
        try {
            if(getterMethod == null) {
                getterMethod = AccessibleObjectUtil.findProperMethod(instance.getClass(),
                    Collections.<Class<?>>emptyList(), getterName);
            }
            return getterMethod.invoke(instance);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void checkType(Class<?> aClass) {
        if(aClass != clazz) {
            throw new RuntimeException("Object passed in of type: " + aClass.getName() + ", and required" +
                    "type is " + clazz.getName());
        }
    }
}
