package com.elgoooog.depin.parser.util;

import com.elgoooog.depin.test.Animal;
import com.elgoooog.depin.test.Cage;
import com.elgoooog.depin.test.Dog;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:03 AM
 */
public class ConstructorUtilTest {
    @Test
    public void findProperConstructorTest() throws Exception {
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(String.class);
        types.add(int.class);

        Constructor<?> constructor = ConstructorUtil.findProperConstructor(Dog.class, types);

        assertEquals(2, constructor.getParameterTypes().length);
        assertEquals(String.class, constructor.getParameterTypes()[0]);
        assertEquals(int.class, constructor.getParameterTypes()[1]);
    }

    @Test
    public void findProperConstructorTest_subclass() throws Exception {
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Dog.class);

        Constructor<?> constructor = ConstructorUtil.findProperConstructor(Cage.class, types);

        assertEquals(1, constructor.getParameterTypes().length);
        assertEquals(Animal.class, constructor.getParameterTypes()[0]);
    }

    @Test
    public void isProperConstructorTest() throws Exception {
        Constructor<?> twoArgDogConstructor = Dog.class.getConstructor(String.class, int.class);
        List<Class<?>> types = new ArrayList<Class<?>>();

        types.add(String.class);
        types.add(int.class);

        boolean isProperConstructor = ConstructorUtil.isProperConstructor(twoArgDogConstructor, types);
        assertTrue(isProperConstructor);
    }

    @Test
    public void isProperConstructorTest_subclass() throws Exception {
        Constructor<?> oneArgCageConstructor = Cage.class.getConstructor(Animal.class);

        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Dog.class);

        boolean isProperConstructor = ConstructorUtil.isProperConstructor(oneArgCageConstructor, types);
        assertTrue(isProperConstructor);

        List<Class<?>> types2 = new ArrayList<Class<?>>();
        types2.add(Animal.class);

        boolean isProperConstructor2 = ConstructorUtil.isProperConstructor(oneArgCageConstructor, types2);
        assertTrue(isProperConstructor2);
    }
}
