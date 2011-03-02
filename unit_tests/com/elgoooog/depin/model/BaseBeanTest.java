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
        InjectedFieldStub.clearCount();
        PropertyStub.clearCount();
        bean = new PrototypeBean("com.elgoooog.depin.test.inject.Skull");
    }

    @Test
    public void createInstanceTest_injectedFields() throws Exception {
        Constructor<Skull> constructor = Skull.class.getConstructor();

        bean.addInjectedField(new InjectedFieldStub());
        bean.addInjectedField(new InjectedFieldStub());

        assertEquals(0, InjectedFieldStub.getCallCount());
        assertEquals(0, PropertyStub.getCallCount());
        Skull skull = (Skull) bean.createInstance(constructor);
        assertNotNull(skull);
        assertEquals(2, InjectedFieldStub.getCallCount());
        assertEquals(0, PropertyStub.getCallCount());
    }

    @Test
    public void createInstanceTest_properties() throws Exception {
        Constructor<Skull> constructor = Skull.class.getConstructor();

        bean.addProperty(new PropertyStub());
        bean.addProperty(new PropertyStub());

        assertEquals(0, InjectedFieldStub.getCallCount());
        assertEquals(0, PropertyStub.getCallCount());
        Skull skull = (Skull) bean.createInstance(constructor);
        assertNotNull(skull);
        assertEquals(0, InjectedFieldStub.getCallCount());
        assertEquals(2, PropertyStub.getCallCount());
    }

    @Test
    public void createInstanceTest_wholeEnchilada() throws Exception {
        Constructor<Skull> constructor = Skull.class.getConstructor();

        bean.addInjectedField(new InjectedFieldStub());
        bean.addProperty(new PropertyStub());

        assertEquals(0, InjectedFieldStub.getCallCount());
        assertEquals(0, PropertyStub.getCallCount());
        Skull skull = (Skull) bean.createInstance(constructor);
        assertNotNull(skull);
        assertEquals(1, InjectedFieldStub.getCallCount());
        assertEquals(1, PropertyStub.getCallCount());
    }

    private static class InjectedFieldStub extends InjectedField {
        private static int count = 0;

        public InjectedFieldStub() {
            super(null, null);
        }

        @Override
        public void injectInto(Object o) {
            ++count;
        }

        public static int getCallCount() {
            return count;
        }

        public static void clearCount() {
            count = 0;
        }
    }

    private static class PropertyStub extends Property {
        private static int count = 0;

        public PropertyStub() {
            super("stub", null);
        }

        @Override
        public void setOn(Object o) {
            ++count;
        }

        public static int getCallCount() {
            return count;
        }

        public static void clearCount() {
            count = 0;
        }
    }
}
