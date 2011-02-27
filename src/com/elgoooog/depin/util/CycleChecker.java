package com.elgoooog.depin.util;

import com.elgoooog.depin.model.Bean;
import com.elgoooog.depin.model.Arg;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/21/11
 *         Time: 11:06 PM
 */
public class CycleChecker {
    private Set<Bean> dependsOn = new HashSet<Bean>();

    public CycleChecker(Bean bean) {
        dependsOn = new HashSet<Bean>();
        dependsOn.add(bean);
    }

    public void checkForCycle(Arg arg) {
        arg.updateDependants(dependsOn);
    }

    public Set<Bean> getDependants() {
        return dependsOn;
    }
}
