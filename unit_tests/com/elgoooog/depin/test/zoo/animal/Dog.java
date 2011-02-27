package com.elgoooog.depin.test.zoo.animal;

/**
 * @author Nicholas Hauschild
 *         Date: 2/9/11
 *         Time: 12:32 AM
 */
public class Dog extends Animal {
    private String name;
    private int age;

    public Dog(String n) {
        this(n, 0);
    }

    public Dog(String n, int a) {
        name = n;
        age = a;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int a) {
        age = a;
    }
}

