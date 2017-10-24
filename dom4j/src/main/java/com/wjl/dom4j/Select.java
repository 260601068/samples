package com.wjl.dom4j;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Select {
public static void main(String[] args) throws DocumentException, IOException {
    SAXReader reader = new SAXReader();
    Document document = reader.read(new File(Select.class.getClassLoader().getResource("conf/myxml.xml").getPath()));
    Element root=document.getRootElement();
    System.out.println("root: "+root.getName());
    System.out.println("-----遍历元素（默认只有一层子元素）-----");
    //elementIterator()方法不带参数会选取所有直接子节点（一层）的遍历迭代器，带参数会选取仅匹配当前参数的直接子节点的遍历迭代器
    Iterator<Element> rootIterator = root.elementIterator("book");
    while(rootIterator.hasNext()){
    	System.out.println(rootIterator.next().getName());
    }
    System.out.println("------获取、遍历属性------");
    Element manager=root.element("manager");
    Element boss=manager.element("boss");
    System.out.println("boss num: "+boss.attributeValue("num"));
    Iterator<Attribute> attributeIterator = boss.attributeIterator();
    while(attributeIterator.hasNext()){
    	System.out.println(attributeIterator.next().getName());
    }
    System.out.println("------使用XPath路径表达式在 XML 文档中进行导航(Node)------");
/*	xpath表达式中"//x"表示文档中所有匹配x的节点，"/x"仅表示直接子元素（一层）中匹配x的节点，
 *  并且如果以"/x"开头表示从根节点开始，x必须为根节点，直接以x开头表示当前节下的直接子元素（一层）中配置x的节点；
 * 	*/
    List<Node> employees=root.selectNodes("manager//employee[@gender='male'] | manager/boss[age>25]/name");
    System.out.println("employees size: "+employees.size());
    Iterator<Node> iterator = employees.iterator();
    while(iterator.hasNext()){
    	Element employee=(Element)iterator.next();	//Node转Element，也可以不转直接获取属性
    	System.out.println("name: "+employee.getName()+" atribute gender: "+employee.attributeValue("gender")+" text: "+employee.getText().trim());
    }
    Node bossAttributeNode=(Node)document.selectObject("//bookstore/manager/boss/@master");
    System.out.println("node type: "+bossAttributeNode.getNodeTypeName()+" node value: "+bossAttributeNode.getStringValue());

    /*特别注意，nodeCount和nodeIndex方法，其中node索引的0,2，4,6等的节点是换行符
    System.out.println("nodecount: "+root.nodeCount());
    System.out.println("nodeindex(0,2,4...): "+root.node(2));
    Iterator it = root.nodeIterator();  
    int i = 1;  
    while (it.hasNext()) {  
        Node node = (Node) it.next();  
        System.out.println("==========" + i);  
        System.out.println(node.asXML());  
        i++;  
    } */
}
}
