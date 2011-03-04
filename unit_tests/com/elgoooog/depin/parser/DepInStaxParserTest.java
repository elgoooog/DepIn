package com.elgoooog.depin.parser;

import com.elgoooog.depin.Beans;
import com.elgoooog.depin.model.Bean;
import com.elgoooog.depin.model.PrototypeBean;
import com.elgoooog.depin.model.Literal;
import com.elgoooog.depin.model.SingletonBean;
import com.elgoooog.depin.test.zoo.animal.Dog;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 2/14/11
 *         Time: 12:17 AM
 */
public class DepInStaxParserTest {
    private DepInStaxParser parser;

    @Before
    public void setup() throws Exception {
        parser = new DepInStaxParser();
    }

    @Test
    public void updateBeanWithLiteralArgTest() throws Exception {
        Bean bean = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        bean.setId("test--Dog");

        parser.updateBeanWithLiteralArg(bean, "string", "bob");
        parser.updateBeanWithLiteralArg(bean, "int", "3");

        assertEquals("bob", bean.getArgs().get(0).getValue());
        assertEquals(String.class, bean.getArgs().get(0).getType());

        assertEquals(3, bean.getArgs().get(1).getValue());
        assertEquals(int.class, bean.getArgs().get(1).getType());
    }

    @Test
    public void updateBeanWithRefArgTest() throws Exception {
        Bean cageBean = new PrototypeBean("com.elgoooog.depin.test.zoo.Cage");
        cageBean.setId("test--Cage");

        Bean fidoBean = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        fidoBean.setId("test--Fido");
        fidoBean.addArg(new Literal("fido"));
        fidoBean.addArg(new Literal(int.class, 7));
        Dog expected = (Dog) fidoBean.getInstance();

        parser.updateBeanWithRefArg(cageBean, fidoBean);

        assertEquals(Dog.class, cageBean.getArgs().get(0).getType());
        Dog actual = (Dog) cageBean.getArgs().get(0).getValue();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    public void createBeanTest() throws Exception {
        Bean bean1 = parser.createBean("singleton", "com.elgoooog.depin.test.zoo.animal.Dog");
        assertTrue(bean1 instanceof SingletonBean);

        Bean bean2 = parser.createBean("blah", "com.elgoooog.depin.test.zoo.animal.Dog");
        assertTrue(bean2 instanceof PrototypeBean);
    }

    @Test
    public void getHolderTest() throws Exception {
        Map<Bean, List<DepInStaxParser.ArgHolder>> argHolders = new HashMap<Bean, List<DepInStaxParser.ArgHolder>>();
        Bean beanPresent = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");
        Bean beanAbsent = new PrototypeBean("com.elgoooog.depin.test.zoo.animal.Dog");

        List<DepInStaxParser.ArgHolder> presentArgHolder = new ArrayList<DepInStaxParser.ArgHolder>();
        presentArgHolder.add(parser.new RefHolder("blah", new Beans()));
        presentArgHolder.add(parser.new LiteralHolder("boom", "bam"));

        argHolders.put(beanPresent, presentArgHolder);

        List<DepInStaxParser.ArgHolder> actual1 = parser.getHolder(beanPresent, argHolders);
        assertEquals(actual1, presentArgHolder);
        assertEquals(2, actual1.size());

        List<DepInStaxParser.ArgHolder> actual2 = parser.getHolder(beanAbsent, argHolders);
        assertTrue(actual2.isEmpty());
    }
}
