package com.elgoooog.depin.parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInStaxParser implements DepInFileParser {
    public void parseBeans(File file, Map<String, Object> beans) {
        try {
            InputStream fileStream = new FileInputStream(file);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(fileStream);

            parse(streamReader, beans);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found: " + file.getAbsolutePath());
        } catch (XMLStreamException e) {
            throw new RuntimeException("failed to parse file: " + file.getAbsolutePath());
        }
    }



    protected void parse(XMLStreamReader streamReader, Map<String, Object> beans) throws XMLStreamException {
        Stack<Map<String, String>> attrStack = new Stack<Map<String, String>>();
        String currentElement;
        List<Object> args = new ArrayList<Object>();
        List<Constructor<?>> constructors = new ArrayList<Constructor<?>>();
        int argIndex = 0;
        while (streamReader.hasNext()) {
            switch (streamReader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    Map<String, String> attrMap = captureAttributes(streamReader);
                    if ("bean".equals(currentElement)) {
                        constructors = captureConstructors(attrMap);
                        argIndex = 0;
                    }
                    attrStack.push(attrMap);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    attrMap = attrStack.pop();
                    if ("bean".equals(currentElement)) {
                        Object obj = createInstance(args, constructors.get(0));
                        beans.put(attrMap.get("id"), obj);
                        args.clear();
                    } else if ("arg".equals(currentElement)) {
                        String val = attrMap.get("val");
                        if(val != null) {
                            List<Class<?>> validTypes = findValidPrimitiveTypes(val);
                            discardInvalidConstructorsForVal(constructors, argIndex, validTypes);
                            addValToArgs(args, val, validTypes);
                        }
                        ++argIndex;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    protected void addValToArgs(List<Object> args, String val, List<Class<?>> validTypes) {
        Class<?> type = validTypes.get(0);
        if(double.class.equals(type)) {
            args.add(Double.parseDouble(val));
        } else if(int.class.equals(type)) {
            args.add(Integer.parseInt(val));
        } else if(float.class.equals(type)) {
            args.add(Float.parseFloat(val));
        } else if(long.class.equals(type)) {
            args.add(Long.parseLong(val));
        } else if(byte.class.equals(type)) {
            args.add(Byte.parseByte(val));
        } else if(short.class.equals(type)) {
            args.add(Short.parseShort(val));
        } else if(boolean.class.equals(type)) {
            args.add(Boolean.parseBoolean(val));
        } else if(char.class.equals(type)) {
            args.add(val.charAt(0));
        } else { // must be string
            args.add(val);
        }
    }

    protected void discardInvalidConstructorsForVal(List<Constructor<?>> constructors, int argIndex,
                                                    List<Class<?>> validPrimitiveTypes) {
        Set<Constructor<?>> invalidConstructors = new HashSet<Constructor<?>>();
        for(Constructor<?> c : constructors) {
            if(c.getParameterTypes().length < argIndex + 1) {
                invalidConstructors.add(c);
                continue;
            }

            Class<?> paramType = c.getParameterTypes()[argIndex];
            if(!validPrimitiveTypes.contains(paramType)) {
                invalidConstructors.add(c);
            }
        }

        constructors.removeAll(invalidConstructors);
    }

    protected List<Class<?>> findValidPrimitiveTypes(String val) {
        List<Class<?>> validPrimitiveTypes = new ArrayList<Class<?>>();

        try {
            Double.parseDouble(val);
            validPrimitiveTypes.add(double.class);
        } catch(NumberFormatException e) {
            //not a double
        }

        try {
            Integer.parseInt(val);
            validPrimitiveTypes.add(int.class);
        } catch(NumberFormatException e) {
            //not an int
        }

        try {
            Float.parseFloat(val);
            validPrimitiveTypes.add(float.class);
        } catch(NumberFormatException e) {
            //not a float
        }

        try {
            Long.parseLong(val);
            validPrimitiveTypes.add(long.class);
        } catch(NumberFormatException e) {
            //not a long
        }

        try {
            Byte.parseByte(val);
            validPrimitiveTypes.add(byte.class);
        } catch(NumberFormatException e) {
            //not a byte
        }

        try {
            Short.parseShort(val);
            validPrimitiveTypes.add(short.class);
        } catch(NumberFormatException e) {
            //not a short
        }

        if("true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val)) {
            validPrimitiveTypes.add(boolean.class);
        } //else not a boolean

        if(val.length() == 1) {
            validPrimitiveTypes.add(char.class);
        } //else not a char

        validPrimitiveTypes.add(String.class);
        return validPrimitiveTypes;
    }

    protected List<Constructor<?>> captureConstructors(Map<String, String> attrMap) {
        try {
            Class<?> clazz = Class.forName(attrMap.get("class"));
            Constructor<?>[] cons = clazz.getConstructors();
            List<Constructor<?>> constructors = new ArrayList<Constructor<?>>(cons.length);
            constructors.addAll(Arrays.asList(cons));
            return constructors;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected Map<String, String> captureAttributes(XMLStreamReader streamReader) {
        int attrCount = streamReader.getAttributeCount();
        String attrName;
        String attrValue;
        Map<String, String> attrMap = new HashMap<String, String>();
        for (int i = 0; i < attrCount; ++i) {
            attrName = streamReader.getAttributeName(i).getLocalPart();
            attrValue = streamReader.getAttributeValue(i);
            attrMap.put(attrName, attrValue);
            System.out.println(attrName + " : " + attrValue);
        }
        return attrMap;
    }

    protected Object createInstance(List<Object> args, Constructor<?> constructor) {
        Object obj;
        try {
            obj = constructor.newInstance(args.toArray(new Object[args.size()]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
