package com.elgoooog.depin.test.inject;

/**
 * @author Nicholas Hauschild
 *         Date: 2/26/11
 *         Time: 11:05 PM
 */
public class Fridge {
    private int foodCount;

    public Fridge(int count) {
        foodCount = count;
    }

    public int getFoodCount() {
        return foodCount;
    }
}
