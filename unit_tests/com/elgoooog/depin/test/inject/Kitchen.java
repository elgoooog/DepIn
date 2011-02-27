package com.elgoooog.depin.test.inject;

import com.elgoooog.depin.inject.Inject;

/**
 * @author Nicholas Hauschild
 *         Date: 2/26/11
 *         Time: 11:05 PM
 */
public class Kitchen {
    @Inject("Fridge")
    private Fridge fridge;

    public Fridge getFridge() {
        return fridge;
    }
}
