package com.elgoooog.depin.parser;

import com.elgoooog.depin.parser.model.Bean;
import com.elgoooog.depin.Beans;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInStaxParser extends BaseDepInFileParser {
    public void parseBeans(InputStream is, Beans beans) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(is);

            parse(streamReader, beans);

        } catch (XMLStreamException e) {
            throw new RuntimeException("failed to parse stream");
        }
    }


    protected void parse(XMLStreamReader streamReader, Beans beans) throws XMLStreamException {
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
                        String clazz = attrs.get("class");
                        if (clazz == null) {
                            throw new RuntimeException("missing required attribute: 'class'");
                        }

                        bean = createBean(attrs.get("scope"), clazz);

                        bean.setId(attrs.get("id"));
                    } else if (validArgNames.contains(currentElement)) {
                        updateBeanWithLiteralArg(bean, currentElement, attrs.get("val"));
                    } else if ("ref".equalsIgnoreCase(currentElement)) {
                        updateBeanWithRefArg(bean, beans.getBean(attrs.get("val")));
                    }
                    break;
                case XMLStreamReader.END_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    if ("bean".equalsIgnoreCase(currentElement)) {
                        if (bean != null) {
                            beans.addBean(bean.getId(), bean);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
