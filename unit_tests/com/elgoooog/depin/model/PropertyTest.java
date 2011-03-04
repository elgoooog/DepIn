package com.elgoooog.depin.model;

import com.elgoooog.depin.test.zoo.Cage;
import com.elgoooog.depin.test.zoo.animal.Animal;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Nicholas Hauschild
 *         Date: 3/1/11
 *         Time: 12:25 AM
 */
public class PropertyTest {
    @Test
    public void setOnTest() throws Exception {
        Bean dogBean = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        dogBean.addArg(new Literal("Fido"));

        Property property = new Property(Cage.class, "animal", new Ref(dogBean));
        Cage cage = new Cage();

        property.setOn(cage);

        Animal dog = cage.getAnimal();
        assertNotNull(dog);
        assertEquals(Dog.class, dog.getClass());
        assertEquals("Fido", dog.getName());
    }

    @Test
    public void getFromTest() throws Exception {
        Property property = new Property(Cage.class, "animal", null);
        Cage cage = new Cage();
        cage.setAnimal(new Dog("Fido"));

        Object dog = property.getFrom(cage);

        assertNotNull(dog);
        assertEquals(Dog.class, dog.getClass());
        assertEquals("Fido", ((Dog)dog).getName());
    }

    @Test(expected = RuntimeException.class)
    public void checkTypeTest() throws Exception {
        Property property = new Property(Cage.class, "animal", null);
        property.checkType(Cage.class);

        //bomb here...
        property.checkType(Dog.class);
        fail();
    }
}
