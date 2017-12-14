package com.xmj.tool.db.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xmj.tool.db.vo.DatabaseVO;

public class XMLConfigUtil {

	Map<String,DatabaseVO> map = null;
	public XMLConfigUtil(){
		if(map != null){
			System.out.println("数据库配置文件已加载完成。");
		}else{
			SAXReader reader = new SAXReader();  
	        String xmlPath = System.getenv("xmlpath");
			
	        try {
	        	map = new HashMap<>();
				Document doc = reader.read(new File(xmlPath));
				Element rootElt = doc.getRootElement();
				Element exmjdbtool = rootElt.element("xmjdbtool");
				Element edatabases = rootElt.element("databases");
				List<Element> edatabaseList = edatabases.elements();
			    for(Element edatabase : edatabaseList){
			    	DatabaseVO vo = new DatabaseVO();
			    	String name = edatabase.attributeValue("name");
			    	String type = edatabase.element("Type").getTextTrim();
			    	String username = edatabase.element("USERNAME").getTextTrim();
			    	String password = edatabase.element("PASSWORD").getTextTrim();
			    	String url = edatabase.element("URL").getTextTrim();
			    	vo.setName(name);
			    	vo.setType(type);
			    	vo.setUsername(username);
			    	vo.setPassword(password);
			    	vo.setUrl(url);
			    	map.put(name, vo);
			    }
				
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private String name = null;
	public XMLConfigUtil getName(String name){
		this.name = name;
		return this;
		
	}
	
	public String getType(){
		return map.get(name).getType();
	}
	public String getUsername(){
		return map.get(name).getUsername();	
	}
	public String getPassword(){
		return map.get(name).getPassword();
	}
	public String getUrl(){
		return map.get(name).getUrl();
	}
	
	
	
}
