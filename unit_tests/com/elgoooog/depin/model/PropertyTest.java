package com.elgoooog.depin.model;

import com.elgoooog.depin.test.zoo.Cage;
import com.elgoooog.depin.test.zoo.animal.Animal;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 3/1/11
 *         Time: 12:25 AM
 */
public class PropertyTest {
    @Test
    public void testSetOn() throws Exception {
        Bean dogBean = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        dogBean.addArg(new Literal("Fido"));

        Property property = new Property("animal", dogBean);
        Cage cage = new Cage();

        property.setOn(cage);

        Animal dog = cage.getAnimal();
        assertNotNull(dog);
        assertEquals(Dog.class, dog.getClass());
        assertEquals("Fido", dog.getName());
    }

    @Test
    public void testGetFrom() throws Exception {
        Property property = new Property("animal", null);
        Cage cage = new Cage();
        cage.setAnimal(new Dog("Fido"));

        Object dog = property.getFrom(cage);

        assertNotNull(dog);
        assertEquals(Dog.class, dog.getClass());
        assertEquals("Fido", ((Dog)dog).getName());
    }
}
