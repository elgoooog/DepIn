package com.elgoooog.depin.util;

import com.elgoooog.depin.test.zoo.Cage;
import com.elgoooog.depin.test.zoo.animal.Animal;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:03 AM
 */
public class AccessibleObjectUtilTest {
    @Test
    public void findProperConstructorTest() throws Exception {
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(String.class);
        types.add(int.class);

        Constructor<?> constructor = AccessibleObjectUtil.findProperConstructor(Dog.class, types);

        assertNotNull(constructor);
        assertEquals(2, constructor.getParameterTypes().length);
        assertEquals(String.class, constructor.getParameterTypes()[0]);
        assertEquals(int.class, constructor.getParameterTypes()[1]);
    }

    @Test
    public void findProperConstructorTest_subclass() throws Exception {
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Dog.class);

        Constructor<?> constructor = AccessibleObjectUtil.findProperConstructor(Cage.class, types);

        assertNotNull(constructor);
        assertEquals(1, constructor.getParameterTypes().length);
        assertEquals(Animal.class, constructor.getParameterTypes()[0]);
    }

    @Test
    public void findProperConstructorTest_superclass() throws Exception {
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(String.class);
        types.add(int.class);

        Constructor<?> constructor = AccessibleObjectUtil.findProperConstructor(Object.class, types);

        assertNull(constructor);
    }

    @Test
    public void isProperConstructorTest() throws Exception {
        Constructor<?> twoArgDogConstructor = Dog.class.getConstructor(String.class, int.class);
        List<Class<?>> types = new ArrayList<Class<?>>();

        types.add(String.class);
        types.add(int.class);

        boolean isProperConstructor = AccessibleObjectUtil.isProperConstructor(twoArgDogConstructor, types);
        assertTrue(isProperConstructor);
    }

    @Test
    public void isProperConstructorTest_subclass() throws Exception {
        Constructor<?> oneArgCageConstructor = Cage.class.getConstructor(Animal.class);

        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Dog.class);

        boolean isProperConstructor = AccessibleObjectUtil.isProperConstructor(oneArgCageConstructor, types);
        assertTrue(isProperConstructor);

        List<Class<?>> types2 = new ArrayList<Class<?>>();
        types2.add(Animal.class);

        boolean isProperConstructor2 = AccessibleObjectUtil.isProperConstructor(oneArgCageConstructor, types2);
        assertTrue(isProperConstructor2);
    }

    @Test
    public void isProperConstructorTest_superclass() throws Exception {
        Constructor<?> oneArgCageConstructor = Cage.class.getConstructor(Animal.class);

        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Object.class);

        boolean isProperConstructor = AccessibleObjectUtil.isProperConstructor(oneArgCageConstructor, types);
        assertFalse(isProperConstructor);
    }

    @Test
    public void findProperMethodTest() throws Exception {
        Method method = AccessibleObjectUtil.findProperMethod(Cage.class,
                Collections.<Class<?>>singletonList(Animal.class), "setAnimal");

        assertNotNull(method);
        assertEquals("setAnimal", method.getName());
        assertEquals(1, method.getParameterTypes().length);
        assertEquals(Animal.class, method.getParameterTypes()[0]);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public void findProperMethodTest_subclass() throws Exception {
        Method method = AccessibleObjectUtil.findProperMethod(Cage.class,
                Collections.<Class<?>>singletonList(Dog.class), "setAnimal");

        assertNotNull(method);
        assertEquals("setAnimal", method.getName());
        assertEquals(1, method.getParameterTypes().length);
        assertEquals(Animal.class, method.getParameterTypes()[0]);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public void findProperMethodTest_superclass() throws Exception {
        Method method = AccessibleObjectUtil.findProperMethod(Cage.class,
                Collections.<Class<?>>singletonList(Object.class), "setAnimal");

        assertNull(method);
    }

    @Test
    public void isProperMethodTest() throws Exception {
        Method method = Cage.class.getDeclaredMethod("setAnimal", Animal.class);
        boolean proper = AccessibleObjectUtil.isProperMethod(method, Collections.<Class<?>>singletonList(Animal.class));

        assertTrue(proper);
    }

    @Test
    public void isProperMethodTest_subclass() throws Exception {
        Method method = Cage.class.getDeclaredMethod("setAnimal", Animal.class);
        boolean proper = AccessibleObjectUtil.isProperMethod(method, Collections.<Class<?>>singletonList(Dog.class));

        assertTrue(proper);
    }

    @Test
    public void isProperMethodTest_superclass() throws Exception {
        Method method = Cage.class.getDeclaredMethod("setAnimal", Animal.class);
        boolean proper = AccessibleObjectUtil.isProperMethod(method, Collections.<Class<?>>singletonList(Object.class));

        assertFalse(proper);
    }
}
