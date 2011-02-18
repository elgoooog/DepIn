package com.elgoooog.depin.parser;

import com.elgoooog.depin.parser.model.Bean;
import com.elgoooog.depin.parser.model.Beans;
import com.elgoooog.depin.parser.model.Literal;
import com.elgoooog.depin.parser.model.PrototypeBean;
import com.elgoooog.depin.test.Cage;
import com.elgoooog.depin.test.Dog;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Nicholas Hauschild
 *         Date: 2/14/11
 *         Time: 12:17 AM
 */
public class DepInStaxParserTest {
    private DepInStaxParser parser;

    @BeforeClass
    public static void init() throws Exception {
        Bean fidoBean = new PrototypeBean(Dog.class);
        fidoBean.setId("test--Fido");
        fidoBean.addArg(new Literal("fido"));
        fidoBean.addArg(new Literal(int.class, 7));
        Beans.addBean("Fido", fidoBean);
    }

    @Before
    public void setup() throws Exception {
        parser = new DepInStaxParser();
    }

    @Test
    public void updateBeanWithLiteralArgTest() throws Exception {
        Bean bean = new PrototypeBean(Dog.class);
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
        Bean cageBean = new PrototypeBean(Cage.class);
        cageBean.setId("test--Cage");

        Bean dogBean = Beans.getBean("Fido");
        Dog expected = (Dog) dogBean.getInstance();

        parser.updateBeanWithRefArg(cageBean, "Fido");

        assertEquals(Dog.class, cageBean.getArgs().get(0).getType());
        Dog actual = (Dog) cageBean.getArgs().get(0).getValue();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAge(), actual.getAge());
    }
}
