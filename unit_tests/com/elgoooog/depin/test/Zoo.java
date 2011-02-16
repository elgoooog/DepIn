package com.elgoooog.depin.test;

import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 2/15/11
 *         Time: 11:38 PM
 */
public class Zoo {
    private List<Cage> cages;

    public Zoo(Cage... c) {
        for(Cage cage : c) {
            cages.add(cage);
        }
    }

    public boolean addCage(Cage c) {
        return cages.add(c);
    }

    public List<Cage> getCages() {
        return cages;
    }

    public Cage removeCage(int index) {
        return cages.remove(index);
    }
}
