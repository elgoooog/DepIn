package com.elgoooog.depin.parser.model;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 5:15 PM
 */
public interface Arg {
    Class<?> getType();

    Object getValue();
}
