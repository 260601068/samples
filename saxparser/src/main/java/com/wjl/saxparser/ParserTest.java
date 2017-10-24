package com.wjl.saxparser;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ParserTest {
public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
	
	XMLReader xr = XMLReaderFactory.createXMLReader();
	MyHandler handler = new MyHandler();
	xr.setContentHandler(handler);
	xr.parse(new InputSource(ParserTest.class.getClassLoader().getResourceAsStream("myxml.xml")));
	List<Book> books=handler.getBooks();
	for(Book book : books){
		System.out.println(book);
	}
	
	
/*	老版本的方法，无法获取uri和localName参数
	SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
    MyHandler handler=new MyHandler();  
    InputStream in=ParserTest.class.getClassLoader().getResourceAsStream("myxml.xml");  
    saxParser.parse(in, handler); 
	List<Book> books=handler.getBooks();
	for(Book book : books){
		System.out.println(book);
	}*/
}
}
