package com.elgoooog.depin.parser;

import com.elgoooog.depin.parser.model.Bean;
import com.elgoooog.depin.parser.model.Beans;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * @author Nicholas Hauschild
 *         Date: 2/13/11
 *         Time: 11:25 PM
 */
public class DepInDomParser extends BaseDepInFileParser {
    public void parseBeans(InputStream is) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList beans = root.getChildNodes();
            for (int i = 0; i < beans.getLength(); ++i) {
                Node node = beans.item(i);
                if (node instanceof Element) {
                    Element bean = (Element) node;
                    if ("bean".equals(bean.getNodeName())) {
                        String classString = bean.getAttribute("class");
                        String scope = bean.getAttribute("scope");

                        Bean beanModel = createBean(scope, classString);

                        populateModel(bean, beanModel);

                        String id = bean.getAttribute("id");
                        if (id != null) {
                            beanModel.setId(id);
                            Beans.addBean(id, beanModel);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void populateModel(Element bean, Bean beanModel) throws Exception {
        NodeList children = bean.getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            Node node = children.item(i);
            if (node instanceof Element) {
                Element child = (Element) node;
                String nodeName = child.getNodeName();
                String val = child.getAttribute("val");
                if (validArgNames.contains(nodeName)) {
                    updateBeanWithLiteralArg(beanModel, nodeName, val);
                } else if ("ref".equals(nodeName)) {
                    updateBeanWithRefArg(beanModel, val);
                }
            }
        }
    }
}
