package com.elgoooog.depin;

import com.elgoooog.depin.test.Animal;
import com.elgoooog.depin.test.Cage;
import com.elgoooog.depin.test.Cat;
import com.elgoooog.depin.test.Dog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 2/8/11
 *         Time: 12:18 AM
 */
public class DepInTest {
    private DepIn depin;

    @Before
    public void getDepin() {
        depin = DepIn.getInstance();
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        Animal animal = (Animal) depin.get("Animal");
        assertNotNull(animal);
    }

    @Test
    public void testConstructorWithPrimitive_val_1() throws Exception {
        Cat cat = (Cat) depin.get("Buff");
        assertNotNull(cat);
        assertEquals("Buff", cat.getName());
    }

    @Test
    public void testConstructorWithPrimitive_val_2() throws Exception {
        Dog dog = (Dog) depin.get("Fido");
        assertNotNull(dog);
        assertEquals("Fido", dog.getName());
        assertEquals(0, dog.getAge());
    }

    @Test
    public void testConstructorWithPrimitive_vals() throws Exception {
        Dog dog = (Dog) depin.get("Rex");
        assertNotNull(dog);
        assertEquals("Rex", dog.getName());
        assertEquals(7, dog.getAge());
    }

    @Test
    public void testConstructorWithRef() throws Exception {
        Cage cage = (Cage) depin.get("AnimalCage");
        assertNotNull(cage);
        assertEquals("animal", cage.getAnimal().getName());
        assertEquals(Animal.class, cage.getAnimal().getClass());
    }

    @Test
    public void testConstructorWithRef_childType() throws Exception {
        Cage cage = (Cage) depin.get("DogCage");
        assertNotNull(cage);
        assertEquals("Fido", cage.getAnimal().getName());
        assertEquals(Dog.class, cage.getAnimal().getClass());
    }
}
