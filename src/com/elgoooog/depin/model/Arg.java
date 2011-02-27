package com.elgoooog.depin.model;

import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 5:15 PM
 */
public interface Arg {
    Class<?> getType();

    Object getValue();

    void updateDependants(Set<Bean> dependants);
}
