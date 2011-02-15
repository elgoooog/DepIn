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
                        Object instance = createInstance(bean, clazz);

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

    protected Object createInstance(Element bean, Class<?> clazz) throws Exception {
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
                    addToArgs(val, nodeName, args, types);
                }
            }
        }

        Constructor<?> constructor = clazz.getConstructor(types.toArray(new Class<?>[types.size()]));

        return constructor.newInstance(args.toArray());
    }

    protected void addToArgs(String val, String type, List<Object> args, List<Class<?>> types) {
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
