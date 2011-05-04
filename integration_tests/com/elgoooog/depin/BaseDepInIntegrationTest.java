package com.elgoooog.depin;

import com.elgoooog.depin.exception.CycleException;
import com.elgoooog.depin.exception.NameAlreadyBoundException;
import com.elgoooog.depin.inject.BeanInjector;
import com.elgoooog.depin.parser.DepInStaxParser;
import com.elgoooog.depin.test.inject.Brain;
import com.elgoooog.depin.test.inject.Fridge;
import com.elgoooog.depin.test.inject.Kitchen;
import com.elgoooog.depin.test.inject.Skull;
import com.elgoooog.depin.test.zoo.Cage;
import com.elgoooog.depin.test.zoo.animal.Animal;
import com.elgoooog.depin.test.zoo.animal.Cat;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/8/11
 *         Time: 12:18 AM
 */

public class BaseDepInIntegrationTest {
    protected DepIn depin;

    @Before
    public void initStaxParser() throws Exception {
        depin = new DepIn(new DepInFileLoader(new DepInStaxParser()), new BeanInjector());
    }

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

    @Test(expected = CycleException.class)
    public void testTwoCycle() throws Exception {
        depin.loadConfiguration("config/depinTest_cycle.xml");

        depin.get("testParent");
    }

    @Test(expected = CycleException.class)
    public void testThreeCycle() throws Exception {
        depin.loadConfiguration("config/depinTest_cycle.xml");

        depin.get("testA");
    }

    @Test(expected = NameAlreadyBoundException.class)
    public void testDuplicateNames() throws Exception {
        depin.loadConfiguration("config/depinTest_nameAlreadyExists.xml");
    }

    @Test
    public void testUnorderedDefinition() throws Exception {
        depin.loadConfiguration("config/depinTest_unorderedDefinition.xml");

        Cage cage = (Cage) depin.get("Cage");
        Animal animal = cage.getAnimal();
        assertEquals("Floyd", animal.getName());
    }

    @Test
    public void testInject() throws Exception {
        depin.loadConfiguration("config/depinTest_inject.xml");

        Skull skull = (Skull) depin.get("Skull");
        Brain brain = skull.getBrain();
        assertNotNull(brain);
    }

    @Test
    public void testInject_prototype() throws Exception {
        depin.loadConfiguration("config/depinTest_inject.xml");

        Skull skull = (Skull) depin.get("Skull");
        Brain notExpected = (Brain) depin.get("Brain");
        Brain brain = skull.getBrain();
        assertNotNull(brain);
        assertEquals(notExpected.getBrainPower(), brain.getBrainPower());
        assertNotSame(notExpected, brain);
    }

    @Test
    public void testInject_singleton() throws Exception {
        depin.loadConfiguration("config/depinTest_inject.xml");

        Kitchen kitchen = (Kitchen) depin.get("Kitchen");
        Fridge expected = (Fridge) depin.get("Fridge");
        Fridge fridge = kitchen.getFridge();
        assertNotNull(fridge);
        assertEquals(expected.getFoodCount(), fridge.getFoodCount());
        assertNotSame(expected, fridge);
    }

    @Test
    public void propertyTest_literal() throws Exception {
        depin.loadConfiguration();

        Dog dog = (Dog) depin.get("PropertyDog");

        assertNotNull(dog);
        assertEquals("pup", dog.getName());
        assertEquals(30, dog.getAge());
    }

    @Test
    public void propertyTest_ref() throws Exception {
        depin.loadConfiguration();

        Cage cage = (Cage) depin.get("PropertyCage");

        assertNotNull(cage);
        assertNotNull(cage.getAnimal());
        assertEquals(Cat.class, cage.getAnimal().getClass());
        assertEquals("Buff", cage.getAnimal().getName());
    }
}
