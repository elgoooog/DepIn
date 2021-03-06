package com.elgoooog.depin.test.zoo.animal;

/**
 * @author Nicholas Hauschild
 *         Date: 2/8/11
 *         Time: 11:45 PM
 */
public class Cat extends Animal {
    private String name;

    public Cat(String n) {
        name = n;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}
