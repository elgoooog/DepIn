package com.elgoooog.depin.model;

import com.elgoooog.depin.exception.CycleException;

import java.util.Set;

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

    @Override
    public void updateDependants(Set<Bean> dependants) {
        Set<Bean> refDependents = ref.getDependants();

        int dependantsSize = dependants.size();
        int refDependantsSize = refDependents.size();

        dependants.addAll(refDependents);

        if(dependants.size() != (dependantsSize + refDependantsSize)) {
            throw new CycleException("Cycle detected");
        }
    }
}
