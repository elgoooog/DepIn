package com.elgoooog.depin.model;

import com.elgoooog.depin.test.inject.Brain;
import com.elgoooog.depin.test.inject.Skull;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 2/27/11
 *         Time: 12:42 PM
 */
public class InjectedFieldTest {
    @Test
    public void injectIntoTest() throws Exception {
        Skull skull = new Skull();
        Field field = Skull.class.getDeclaredField("brain");
        field.setAccessible(false);
        InjectedField injectedField = new InjectedField(field, new BeanStub(new Brain(83)));

        injectedField.injectInto(skull);

        assertTrue(field.isAccessible());
        Brain brain = skull.getBrain();
        assertNotNull(brain);
        assertEquals(83, brain.getBrainPower());
    }

    private static class BeanStub extends BaseBean {
        private Object instance;

        public BeanStub(Object o) {
            super("java.lang.String"); //any class...
            instance = o;
        }

        @Override
        public Object getInstance() {
            return instance;
        }
    }
}
