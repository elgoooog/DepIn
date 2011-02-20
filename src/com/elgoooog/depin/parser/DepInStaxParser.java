package com.elgoooog.depin.parser;

import com.elgoooog.depin.Beans;
import com.elgoooog.depin.parser.model.Bean;

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
                        List<ArgHolder> argHolder = getArgHolder(bean, args);
                        argHolder.add(new LiteralHolder(currentElement, attrs.get("val")));
                    } else if ("ref".equalsIgnoreCase(currentElement)) {
                        List<ArgHolder> argHolder = getArgHolder(bean, args);
                        argHolder.add(new RefHolder(attrs.get("val"), beans));
                    }
                    break;
                default:
                    break;
            }
        }

        //setup arguments on second pass
        for(Bean beanModel : beans) {
            List<ArgHolder> argHolders = args.get(beanModel);
            if(argHolders != null) {
                for(ArgHolder argHolder : argHolders) {
                    argHolder.updateBean(beanModel);
                }
            }
        }
    }

    protected List<ArgHolder> getArgHolder(Bean bean, Map<Bean, List<ArgHolder>> args) {
        List<ArgHolder> argHolder = args.get(bean);
        if(argHolder == null) {
            argHolder = new ArrayList<ArgHolder>();
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
}
