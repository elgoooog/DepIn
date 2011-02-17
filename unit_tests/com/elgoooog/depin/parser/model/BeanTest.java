package com.elgoooog.depin.parser.model;

import com.elgoooog.depin.test.Dog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 1:05 AM
 */
public class BeanTest {
    @Test
    public void getInstanceTest() throws Exception {
        Bean bean = new Bean(Dog.class);
        bean.addArg(new Literal("fido"));
        bean.addArg(new Literal(int.class, 7));

        Dog dog = (Dog) bean.getInstance();

        assertNotNull(dog);
        assertEquals("fido", dog.getName());
        assertEquals(7, dog.getAge());
    }
}
