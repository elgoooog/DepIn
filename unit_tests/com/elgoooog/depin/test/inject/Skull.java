package com.elgoooog.depin.test.inject;

import com.elgoooog.depin.inject.Inject;

/**
 * @author Nicholas Hauschild
 *         Date: 2/25/11
 *         Time: 5:38 PM
 */
public class Skull {
    @Inject("Brain")
    private Brain brain;

    public Brain getBrain() {
        return brain;
    }
}
