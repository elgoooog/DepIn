package com.elgoooog.depin.test.zoo;

import com.elgoooog.depin.test.zoo.animal.Animal;

/**
 * @author Nicholas Hauschild
 *         Date: 2/15/11
 *         Time: 11:38 PM
 */
public class Cage {
    private Animal animal;

    public Cage(Animal a) {
        animal = a;
    }

    public Cage() {
        this(null);
    }

    public Animal removeAnimal() {
        Animal temp = animal;
        animal = null;
        return temp;
    }

    public void setAnimal(Animal a) {
        animal = a;
    }

    public Animal getAnimal() {
        return animal;
    }
}
