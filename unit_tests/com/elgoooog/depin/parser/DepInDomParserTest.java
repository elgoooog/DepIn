package com.elgoooog.depin.parser;

import com.elgoooog.depin.test.Dog;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Nicholas Hauschild
 *         Date: 2/14/11
 *         Time: 12:17 AM
 */
public class DepInDomParserTest {
    private DepInDomParser parser;

    @Before
    public void setup() throws Exception {
        parser = new DepInDomParser();
    }

    @Test
    public void addToArgsTest() throws Exception {
        List<Object> args = new ArrayList<Object>();
        List<Class<?>> types = new ArrayList<Class<?>>();
        parser.addToArgs("1", "int", args, types);
        parser.addToArgs("2.1", "double", args, types);
        parser.addToArgs("true", "boolean", args, types);
        parser.addToArgs("a", "char", args, types);
        parser.addToArgs("123", "long", args, types);
        parser.addToArgs("23.33", "float", args, types);
        parser.addToArgs("12", "short", args, types);
        parser.addToArgs("21", "byte", args, types);

        assertEquals(1, args.get(0));
        assertEquals(int.class, types.get(0));

        assertEquals(2.1, args.get(1));
        assertEquals(double.class, types.get(1));

        assertEquals(true, args.get(2));
        assertEquals(boolean.class, types.get(2));

        assertEquals('a', args.get(3));
        assertEquals(char.class, types.get(3));

        assertEquals(123L, args.get(4));
        assertEquals(long.class, types.get(4));

        assertEquals(23.33F, args.get(5));
        assertEquals(float.class, types.get(5));

        short s = 12;
        assertEquals(s, args.get(6));
        assertEquals(short.class, types.get(6));

        byte b = 21;
        assertEquals(b, args.get(7));
        assertEquals(byte.class, types.get(7));
    }

    @Test
    public void createInstanceTest() throws Exception {
        Element e1 = new ElementStub("string", Collections.singletonMap("val", "Yoda"));
        Element e2 = new ElementStub("int", Collections.singletonMap("val", "99"));

        Node[] nodes = new Element[]{e1, e2};
        NodeList nodeList = new NodeListStub(nodes);
        Element element = new ElementStub(nodeList);
        Class<?> clazz = Dog.class;
        Object o = parser.createInstance(element, clazz);
        Dog dog = (Dog) o;

        assertEquals(99, dog.getAge());
        assertEquals("Yoda", dog.getName());
    }

    private class ElementStub implements Element {
        private NodeList nodeList;
        private String name;
        private Map<String, String> attrs;

        private ElementStub(String n, Map<String, String> a) {
            name = n;
            attrs = a;
        }

        private ElementStub(NodeList list) {
            nodeList = list;
        }

        public String getTagName() {
            return null;
        }

        public String getAttribute(String name) {
            return attrs.get(name);
        }

        public void setAttribute(String name, String value) throws DOMException {
        }

        public void removeAttribute(String name) throws DOMException {
        }

        public Attr getAttributeNode(String name) {
            return null;
        }

        public Attr setAttributeNode(Attr newAttr) throws DOMException {
            return null;  
        }

        public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
            return null;  
        }

        public NodeList getElementsByTagName(String name) {
            return null;  
        }

        public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
            return null;  
        }

        public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        }

        public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        }

        public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
            return null;
        }

        public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
            return null; 
        }

        public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
            return null; 
        }

        public boolean hasAttribute(String name) {
            return false; 
        }

        public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
            return false;  
        }

        public TypeInfo getSchemaTypeInfo() {
            return null; 
        }

        public void setIdAttribute(String name, boolean isId) throws DOMException {
        }

        public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        }

        public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        }

        public String getNodeName() {
            return name;
        }

        public String getNodeValue() throws DOMException {
            return null;  
        }

        public void setNodeValue(String nodeValue) throws DOMException {
        }

        public short getNodeType() {
            return 0;  
        }

        public Node getParentNode() {
            return null;  
        }

        public NodeList getChildNodes() {
            return nodeList;
        }

        public Node getFirstChild() {
            return null;  
        }

        public Node getLastChild() {
            return null;  
        }

        public Node getPreviousSibling() {
            return null;  
        }

        public Node getNextSibling() {
            return null; 
        }

        public NamedNodeMap getAttributes() {
            return null;  
        }

        public Document getOwnerDocument() {
            return null;  
        }

        public Node insertBefore(Node newChild, Node refChild) throws DOMException {
            return null;  
        }

        public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
            return null;  
        }

        public Node removeChild(Node oldChild) throws DOMException {
            return null;  
        }

        public Node appendChild(Node newChild) throws DOMException {
            return null;  
        }

        public boolean hasChildNodes() {
            return false;  
        }

        public Node cloneNode(boolean deep) {
            return null;  
        }

        public void normalize() {
            
        }

        public boolean isSupported(String feature, String version) {
            return false;  
        }

        public String getNamespaceURI() {
            return null;  
        }

        public String getPrefix() {
            return null;  
        }

        public void setPrefix(String prefix) throws DOMException {
            
        }

        public String getLocalName() {
            return null;  
        }

        public boolean hasAttributes() {
            return false;  
        }

        public String getBaseURI() {
            return null;  
        }

        public short compareDocumentPosition(Node other) throws DOMException {
            return 0;  
        }

        public String getTextContent() throws DOMException {
            return null;  
        }

        public void setTextContent(String textContent) throws DOMException {
            
        }

        public boolean isSameNode(Node other) {
            return false;  
        }

        public String lookupPrefix(String namespaceURI) {
            return null;  
        }

        public boolean isDefaultNamespace(String namespaceURI) {
            return false;  
        }

        public String lookupNamespaceURI(String prefix) {
            return null;  
        }

        public boolean isEqualNode(Node arg) {
            return false;  
        }

        public Object getFeature(String feature, String version) {
            return null;  
        }

        public Object setUserData(String key, Object data, UserDataHandler handler) {
            return null;  
        }

        public Object getUserData(String key) {
            return null;  
        }
    }

    private class NodeListStub implements NodeList {
        private Node[] nodes;

        private NodeListStub(Node[] n) {
            nodes = n;
        }

        public Node item(int index) {
            return nodes[index];
        }

        public int getLength() {
            return nodes.length;
        }
    }
}
