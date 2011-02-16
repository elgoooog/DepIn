package com.elgoooog.depin.parser;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInDomParser implements DepInFileParser {
    private static final Set<String> validArgNames = new HashSet<String>();

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

    public void parseBeans(File file, Map<String, Object> beanMap) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList beans = root.getChildNodes();
            for(int i = 0; i < beans.getLength(); ++i) {
                Node node = beans.item(i);
                if(node instanceof Element) {
                    Element bean = (Element) node;
                    if("bean".equals(bean.getNodeName())) {
                        String classString = bean.getAttribute("class");
                        Class<?> clazz = Class.forName(classString);
                        Object instance = createInstance(bean, clazz, beanMap);

                        String id = bean.getAttribute("id");
                        if(id != null) {
                            beanMap.put(id, instance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected Object createInstance(Element bean, Class<?> clazz, Map<String, Object> beanMap) throws Exception {
        NodeList children = bean.getChildNodes();
        List<Object> args = new ArrayList<Object>();
        List<Class<?>> types = new ArrayList<Class<?>>();

        for(int i = 0; i < children.getLength(); ++i) {
            Node node = children.item(i);
            if(node instanceof Element) {
                Element child = (Element) node;
                String nodeName = child.getNodeName();
                if(validArgNames.contains(nodeName)) {
                    String val = child.getAttribute("val");
                    addPrimitiveToArgs(val, nodeName, args, types);
                } else if("ref".equals(nodeName)) {
                    Object o = beanMap.get(child.getAttribute("val"));
                    addRefToArgs(o, args, types);
                }
            }
        }

        Constructor<?> constructor = findProperConstructor(clazz, types);

        return constructor.newInstance(args.toArray());
    }

    protected Constructor<?> findProperConstructor(Class<?> clazz, List<Class<?>> types) throws NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getConstructors();

        for(int i = 0; i < constructors.length; ++i) {
            Constructor constructor = constructors[i];
            if(isProperConstructor(constructor, types)) {
                return constructor;
            }
        }

        return null;
    }

    protected boolean isProperConstructor(Constructor<?> constructor, List<Class<?>> types) {
        Class<?>[] constructorParams = constructor.getParameterTypes();

        if(constructorParams.length != types.size()) {
            return false;
        }

        for(int i = 0; i < types.size(); ++i) {
            if(!constructorParams[i].isAssignableFrom(types.get(i))) {
                return false;
            }
        }
        return true;
    }

    protected void addRefToArgs(Object val, List<Object> args, List<Class<?>> types) {
        args.add(val);
        types.add(val.getClass());
    }

    protected void addPrimitiveToArgs(String val, String type, List<Object> args, List<Class<?>> types) {
        if("int".equalsIgnoreCase(type)) {
            args.add(Integer.parseInt(val));
            types.add(int.class);
        } else if("float".equalsIgnoreCase(type)) {
            args.add(Float.parseFloat(val));
            types.add(float.class);
        } else if("long".equalsIgnoreCase(type)) {
            args.add(Long.parseLong(val));
            types.add(long.class);
        } else if("double".equalsIgnoreCase(type)) {
            args.add(Double.parseDouble(val));
            types.add(double.class);
        } else if("short".equalsIgnoreCase(type)) {
            args.add(Short.parseShort(val));
            types.add(short.class);
        } else if("byte".equalsIgnoreCase(type)) {
            args.add(Byte.parseByte(val));
            types.add(byte.class);
        } else if("boolean".equalsIgnoreCase(type)) {
            args.add(Boolean.parseBoolean(val));
            types.add(boolean.class);
        } else if("char".equalsIgnoreCase(type)) {
            args.add(val.charAt(0));
            types.add(char.class);
        } else if("string".equalsIgnoreCase(type)) {
            args.add(val);
            types.add(String.class);
        }
    }
}
