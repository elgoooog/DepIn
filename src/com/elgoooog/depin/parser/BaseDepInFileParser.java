package com.elgoooog.depin.parser;

import com.elgoooog.depin.parser.model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nicholas Hauschild
 *         Date: 2/16/11
 *         Time: 5:10 PM
 */
public abstract class BaseDepInFileParser implements DepInFileParser {
    protected static final Set<String> validArgNames = new HashSet<String>();

    static {
        validArgNames.add("int");
        validArgNames.add("long");
        validArgNames.add("short");
        validArgNames.add("byte");
        validArgNames.add("float");
        validArgNames.add("double");
        validArgNames.add("boolean");
        validArgNames.add("char");
        validArgNames.add("string");
    }

    protected void updateBeanWithRefArg(Bean bean, Bean ref) {
        bean.addArg(new Ref(ref));
    }

    protected void updateBeanWithLiteralArg(Bean bean, String currentElement, String val) {
        if ("int".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(int.class, Integer.parseInt(val)));
        } else if ("float".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(float.class, Float.parseFloat(val)));
        } else if ("long".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(long.class, Long.parseLong(val)));
        } else if ("double".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(double.class, Double.parseDouble(val)));
        } else if ("short".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(short.class, Short.parseShort(val)));
        } else if ("byte".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(byte.class, Byte.parseByte(val)));
        } else if ("boolean".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(boolean.class, Boolean.parseBoolean(val)));
        } else if ("char".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(char.class, val.charAt(0)));
        } else if ("string".equalsIgnoreCase(currentElement)) {
            bean.addArg(new Literal(val));
        }
    }

    protected Bean createBean(String scope, String clazz) {
        if ("singleton".equalsIgnoreCase(scope)) {
            return new SingletonBean(clazz);
        } else {
            return new PrototypeBean(clazz);
        }
    }
}
