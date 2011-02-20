package com.elgoooog.depin;

import com.elgoooog.depin.test.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/8/11
 *         Time: 12:18 AM
 */

@Ignore
public class BaseDepInIntegrationTest {
    protected DepIn depin;

    @Test
    public void testDefaultConstructor() throws Exception {
        depin.loadConfiguration();

        Animal animal = (Animal) depin.get("Animal");
        assertNotNull(animal);
    }

    @Test
    public void testConstructorWithPrimitive_val_1() throws Exception {
        depin.loadConfiguration();

        Cat cat = (Cat) depin.get("Buff");
        assertNotNull(cat);
        assertEquals("Buff", cat.getName());
    }

    @Test
    public void testConstructorWithPrimitive_val_2() throws Exception {
        depin.loadConfiguration();

        Dog dog = (Dog) depin.get("Fido");
        assertNotNull(dog);
        assertEquals("Fido", dog.getName());
        assertEquals(0, dog.getAge());
    }

    @Test
    public void testConstructorWithPrimitive_vals() throws Exception {
        depin.loadConfiguration();

        Dog dog = (Dog) depin.get("Rex");
        assertNotNull(dog);
        assertEquals("Rex", dog.getName());
        assertEquals(7, dog.getAge());
    }

    @Test
    public void testConstructorWithRef() throws Exception {
        depin.loadConfiguration();

        Cage cage = (Cage) depin.get("AnimalCage");
        assertNotNull(cage);
        assertEquals("animal", cage.getAnimal().getName());
        assertEquals(Animal.class, cage.getAnimal().getClass());
    }

    @Test
    public void testConstructorWithRef_childType() throws Exception {
        depin.loadConfiguration();

        Cage cage = (Cage) depin.get("DogCage");
        assertNotNull(cage);
        assertEquals("Fido", cage.getAnimal().getName());
        assertEquals(Dog.class, cage.getAnimal().getClass());
    }

    @Test
    public void testPrototype() throws Exception {
        depin.loadConfiguration();

        Animal animal1 = (Animal) depin.get("Animal");
        Animal animal2 = (Animal) depin.get("Animal");

        assertNotSame(animal1, animal2);
    }

    @Test
    public void testSingleton() throws Exception {
        depin.loadConfiguration();

        Animal animal1 = (Animal) depin.get("SingletonAnimal");
        Animal animal2 = (Animal) depin.get("SingletonAnimal");

        assertSame(animal1, animal2);
    }

    @Test
    public void testBasicCycle() throws Exception {
        depin.loadConfiguration("config/depinTest_cycle.xml");

        Parent parent = (Parent) depin.get("testParent");
        Child child = (Child) depin.get("testChild");

        fail();
    }

    @Test
    public void testUnorderedDefinition() throws Exception {
        depin.loadConfiguration("config/depinTest_unorderedDefinition.xml");

        Cage cage = (Cage) depin.get("Cage");
        Animal animal = cage.getAnimal();
        assertEquals("Floyd", animal.getName());
    }
}
