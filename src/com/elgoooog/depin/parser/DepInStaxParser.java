package com.elgoooog.depin.parser;

import com.elgoooog.depin.parser.model.Bean;
import com.elgoooog.depin.parser.model.Beans;
import com.elgoooog.depin.parser.model.Literal;
import com.elgoooog.depin.parser.model.Ref;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInStaxParser extends BaseDepInFileParser {


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
        String currentElement;
        Bean bean = null;
        while (streamReader.hasNext()) {
            switch (streamReader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    Map<String, String> attrs = new HashMap<String, String>();
                    for (int i = 0; i < streamReader.getAttributeCount(); ++i) {
                        String name = streamReader.getAttributeName(i).getLocalPart().toLowerCase();
                        String value = streamReader.getAttributeValue(i);
                        attrs.put(name, value);
                    }

                    if ("bean".equalsIgnoreCase(currentElement)) {
                        if (attrs.get("class") == null) {
                            throw new RuntimeException("missing required attribute: 'class'");
                        }

                        bean = new Bean(attrs.get("class"));
                        bean.setId(attrs.get("id"));
                    } else if (validArgNames.contains(currentElement)) {
                        updateBeanWithLiteralArg(bean, currentElement, attrs.get("val"));
                    } else if ("ref".equalsIgnoreCase(currentElement)) {
                        updateBeanWithRefArg(bean, attrs.get("val"));
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    if ("bean".equalsIgnoreCase(currentElement)) {
                        if (bean != null) {
                            Beans.addBean(bean.getId(), bean);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        for (Bean b : Beans.allBeans()) {
            beans.put(b.getId(), b.getInstance());
        }
    }

    protected void updateBeanWithRefArg(Bean bean, String val) {
        bean.addArg(new Ref(val));
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
}
