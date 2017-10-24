package com.wjl.saxparser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/*从上到下依次解析xml文档的每个部分并调用相应的处理方法；
解析xml文档正式（非<xml>标签）的第一个标签前和最后一个标签后会分别调用startDocument()和endDocument()仅一次；
解析到每一个前置标签时会调用startElement()方法一次，对应的结束标签时会调用endElement()方法一次；
解析到每一对标签之间内容或结束标签之后内容（注意）时，只要该内容存在（包括空格和换行，即前后标签非紧密直接相连）都会调用characters()方法一次*/
public class MyHandler extends DefaultHandler2{
	
	private List<Book> books;
	private Book book;
	private String preTag;
	
	public List<Book> getBooks() {
		return books;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("start preTag: "+preTag);
		books=new ArrayList<Book>();
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("end preTag: "+preTag);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//uri为xmlns="http://www.megginson.com/ns/exp/poetry"中的"http://www.megginson.com/ns/exp/poetry"，如果该语句存在
		if("book".equals(qName)){
			book=new Book();
			book.setId(Integer.parseInt(attributes.getValue("id")));
		}
		preTag=qName;
		System.out.println("startElement uri: "+uri+" localName: "+localName+" qName: "+qName+" attributes: "+attributes.getValue("id")+" preTag: "+preTag);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if("book".equals(qName)){
			books.add(book);
			book=null;
		}
		preTag=null;
		System.out.println("endElement uri: "+uri+" localName: "+localName+" qName: "+qName+" preTag: "+preTag);
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
        if(preTag!=null){  
            String content = new String(ch,start,length);  
            if("name".equals(preTag)){  
                book.setName(content);  
            }else if("price".equals(preTag)){  
                book.setPrice(Float.parseFloat(content));  
            }  
        }  
		System.out.println("characters preTag: "+preTag+" string: "+new String(ch,start,length));
	}
}
