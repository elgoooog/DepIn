package com.elgoooog.depin.parser;

import com.elgoooog.depin.Beans;
import com.elgoooog.depin.model.Bean;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInStaxParser extends BaseDepInFileParser {
    public void parseBeans(InputStream is, Beans beans) {
        XMLStreamReader streamReader = null;
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            streamReader = inputFactory.createXMLStreamReader(is);

            parse(streamReader, beans);

        } catch (XMLStreamException e) {
            throw new RuntimeException("failed to parse stream", e);
        } finally {
            try {
                streamReader.close();
            } catch (Exception e) {
                // tough luck
            }
        }
    }

    protected void parse(XMLStreamReader streamReader, Beans beans) throws XMLStreamException {
        String currentElement;
        Bean bean = null;
        Map<Bean, List<ArgHolder>> args = new HashMap<Bean, List<ArgHolder>>();
        Map<Bean, List<PropertyHolder>> props = new HashMap<Bean, List<PropertyHolder>>();

        //first pass, gather all beans
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
                        beans.addBean(bean.getId(), bean);
                    } else if (validArgNames.contains(currentElement)) {
                        List<ArgHolder> argHolder = getHolder(bean, args);
                        argHolder.add(new LiteralHolder(currentElement, attrs.get("val")));
                    } else if ("ref".equalsIgnoreCase(currentElement)) {
                        List<ArgHolder> argHolder = getHolder(bean, args);
                        argHolder.add(new RefHolder(attrs.get("val"), beans));
                    } else if("property".equalsIgnoreCase(currentElement)) {
                        String primitive = attrs.get("primitive");
                        String name = attrs.get("name");
                        String val = attrs.get("val");
                        List<PropertyHolder> propHolder = getHolder(bean, props);
                        if(primitive != null) {
                            propHolder.add(new LiteralProperty(name, primitive, val));
                        } else {
                            propHolder.add(new RefProperty(name, val, beans));
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        //setup arguments on second pass
        for(Bean beanModel : beans) {
            List<ArgHolder> argHolders = args.get(beanModel);
            List<PropertyHolder> propertyHolders = props.get(beanModel);
            if(argHolders != null) {
                for(ArgHolder argHolder : argHolders) {
                    argHolder.updateBean(beanModel);
                }
            }
            if(propertyHolders != null) {
                for(PropertyHolder propertyHolder : propertyHolders) {
                    propertyHolder.updateBean(beanModel);
                }
            }
        }
    }

    protected <T> List<T> getHolder(Bean bean, Map<Bean, List<T>> args) {
        List<T> argHolder = args.get(bean);
        if(argHolder == null) {
            argHolder = new ArrayList<T>();
            args.put(bean, argHolder);
        }
        return argHolder;
    }

    protected interface ArgHolder {
        void updateBean(Bean bean);
    }

    protected class RefHolder implements ArgHolder {
        private String val;
        private Beans beans;

        protected RefHolder(String v, Beans b) {
            val = v;
            beans = b;
        }

        public void updateBean(Bean bean) {
            updateBeanWithRefArg(bean, beans.getBean(val));
        }
    }

    protected class LiteralHolder implements ArgHolder {
        private String type;
        private String val;

        protected LiteralHolder(String t, String v) {
            type = t;
            val = v;
        }

        public void updateBean(Bean bean) {
            updateBeanWithLiteralArg(bean, type, val);
        }
    }

    protected interface PropertyHolder {
        void updateBean(Bean bean);
    }

    protected class RefProperty implements PropertyHolder {
        private String property;
        private String value;
        private Beans beans;

        protected RefProperty(String p, String v, Beans b) {
            property = p;
            value = v;
            beans = b;
        }

        public void updateBean(Bean bean) {
            updateBeanWithRefProperty(bean, property, beans.getBean(value));
        }
    }

    protected class LiteralProperty implements PropertyHolder {
        private String property;
        private String type;
        private String val;

        protected LiteralProperty(String p, String t, String v) {
            property = p;
            type = t;
            val = v;
        }

        public void updateBean(Bean bean) {
            updateBeanWithLiteralProperty(bean, type, property, val);
        }
    }
}
