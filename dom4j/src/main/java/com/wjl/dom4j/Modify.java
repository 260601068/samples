package com.wjl.dom4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.print.attribute.standard.Media;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class Modify {
	
@Test
public void createXML() throws IOException{
	Document document = DocumentHelper.createDocument();
	Element root=document.addElement("family");
	Element father=root.addElement("member");
	father.addAttribute("relationship", "father");
	Element fatherName = father.addElement("name");
	fatherName.addText("父亲");
	Element fatherAge = father.addElement("age");
	fatherAge.addText("50");
	Element mother=root.addElement("member");
	mother.addAttribute("relationship", "mother");
	Element motherName = mother.addElement("name");
	motherName.addText("母亲");
	Element motherAge = mother.addElement("age");
	motherAge.addText("47");
	Element children=root.addElement("member");
	children.addAttribute("relationship", "children");
	Element childrenName = children.addElement("name");
	childrenName.addText("孩子");
	Element childrenAge = children.addElement("age");
	childrenAge.addText("27");
	
	//以方便阅读的xml格式写入document到文件中
	FileWriter fileWriter = new FileWriter(new File("src/main/resources/conf/mycreatexml.xml").getAbsolutePath());
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter writer = new XMLWriter(fileWriter, format);
    writer.write( document );
    writer.flush();
    writer.close();
}

@Test
public void modifyXML() throws DocumentException{
    SAXReader reader = new SAXReader();
    Document document = reader.read(new File(Select.class.getClassLoader().getResource("conf/mycreatexml.xml").getPath()));
    Element root=document.getRootElement();
    //node(3)实际上是取得是第二个子节点
    System.out.println("-------测试添加元素和设置属性-------");
    Node mynode=root.node(3);
    if(mynode instanceof Element){
    	Element myelement=(Element)mynode;
    	System.out.println(myelement.getName());
    	//dom4j新版本中设置属性也是用addAttribute()方法
    	myelement.addAttribute("relationship", "grandma");
    	myelement.addAttribute("gender", "woman");
    	Element email=myelement.addElement("email");
    	email.addText("abc.163.com");
    }
    Node _mynode=root.node(3);
    if(_mynode instanceof Element){
    	Element _myelement=(Element)_mynode;
    	System.out.println("name: "+_myelement.getName()+" relationship: "+_myelement.attributeValue("relationship")+" gender: "+_myelement.attributeValue("gender"));
    	Element _email = _myelement.element("email");
    	System.out.println("_email: "+_email.getText());
    }
    
    System.out.println("------测试删除元素和属性-------");
    Node mynode2=root.node(5);
    if(mynode2 instanceof Element){
    	Element myelement2=(Element)mynode2;
    	myelement2.remove(myelement2.attribute("relationship"));
    	myelement2.remove(myelement2.element("age"));
    }
    System.out.println(mynode2.asXML());
}
}
