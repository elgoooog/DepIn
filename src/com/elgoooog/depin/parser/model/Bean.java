package com.elgoooog.depin.parser.model;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 11:22 PM
 */
public interface Bean {
    Class<?> getBeanClass();

    void setId(String i);

    String getId();

    void addArg(Arg a);

    Args getArgs();

    Object getInstance();
}