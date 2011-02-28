package com.elgoooog.depin.model;

import com.elgoooog.depin.test.inject.Skull;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 2/26/11
 *         Time: 11:09 PM
 */
public class BaseBeanTest {
    private BaseBean bean;

    @Before
    public void setup() throws Exception {
        bean = new PrototypeBean("com.elgoooog.depin.test.inject.Skull");
    }

    @Test
    public void createInstanceTest() throws Exception {
        Constructor<Skull> constructor = Skull.class.getConstructor();

        bean.addInjectedField(new InjectedFieldStub());
        bean.addInjectedField(new InjectedFieldStub());

        assertEquals(0, InjectedFieldStub.getCallCount());
        Skull skull = (Skull) bean.createInstance(constructor);
        assertNotNull(skull);
        assertEquals(2, InjectedFieldStub.getCallCount());
    }

    private static class InjectedFieldStub extends InjectedField {
        private static int count = 0;

        public InjectedFieldStub() {
            super(null, null);
        }

        public void injectInto(Object o) {
            ++count;
        }

        public static int getCallCount() {
            return count;
        }
    }
}
