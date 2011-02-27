package com.elgoooog.depin.model;

import com.elgoooog.depin.model.Bean;
import com.elgoooog.depin.model.Literal;
import com.elgoooog.depin.model.PrototypeBean;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 11:14 PM
 */
public class PrototypeBeanTest {
    private Bean bean;

    @Before
    public void setup() throws Exception {
        bean = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        bean.setId("test--DogPrototype");
        bean.addArg(new Literal("fido"));
        bean.addArg(new Literal(int.class, 7));
    }

    @Test
    public void getInstanceTest() throws Exception {
        Dog dog = (Dog) bean.getInstance();

        assertNotNull(dog);
        assertEquals("fido", dog.getName());
        assertEquals(7, dog.getAge());
    }

    @Test
    public void getInstance_differentInstances() throws Exception {
        Dog dog1 = (Dog) bean.getInstance();
        Dog dog2 = (Dog) bean.getInstance();

        assertEquals(dog1.getName(), dog2.getName());
        assertEquals(dog1.getAge(), dog2.getAge());
        assertNotSame(dog1, dog2);
    }
}
