package com.internet.cms.basic.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class ParseUtils {
	
	/**
	 * 提取具有某个属性值的标签列表
	 * @param <T>
	 * @param html 被提取的HTML文本
	 * @param tagType 标签类型
	 * @param attributeName 某个属性的名称
	 * @param attributeValue 属性应取的值
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType,final String attributeName,final String attributeValue){
		try {
			//创建一个HTML解释器
			Parser parser = new Parser();
			parser.setInputHTML(html);

			NodeList tagList = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						
						if(node.getClass() == tagType){
							T tn = (T)node;
							if(attributeName == null){
								return true;
							}
							
							String attrValue = tn.getAttribute(attributeName);
							if(attrValue != null && attrValue.equals(attributeValue)){
								return true;
							}
						}

						return false;
					}
				}
			);
			
			List<T> tags = new ArrayList<T>();
			for(int i=0; i<tagList.size(); i++){
				T t = (T)tagList.elementAt(i);
				tags.add(t);
			}
			
			return tags;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType){
		return parseTags(html, tagType,null,null);
	}
	
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType,final String attributeName,final String attributeValue){
		List<T> tags = parseTags(html, tagType, attributeName, attributeValue);
		if(tags != null && tags.size() > 0){
			return tags.get(0);
		}
		return null;
	}
	
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType){
		return parseTag(html, tagType,null,null);
	}
	
	/**
	 * 修改HTML内容中所包含的所有图片的链接地址，加上指定的前缀
	 * @param html 要被修改的HTML内容
	 * @param prefix 要增加的前缀
	 * @return 被修改之后的HTML内容
	 */
	public static String modifyImageUrl(String html,String prefix){
		try {
			
			StringBuffer sb = new StringBuffer();
			
			//创建一个HTML解释器
			Parser parser = new Parser();
			parser.setInputHTML(html);
			
			//nodeList中，将包含网页中的所有内容
			NodeList nodeList = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						return true;
					}
				}
			);
			
			for(int i=0; i<nodeList.size(); i++){
				Node node = nodeList.elementAt(i);
				if(node instanceof ImageTag){
					//如果是<img>标签
					ImageTag it = (ImageTag)node;
					it.setImageURL(prefix+it.getImageURL());
					sb.append(it.toHtml());
				}else if(node instanceof TextNode){ //文本标签，原样输出
					TextNode text = (TextNode)node;
					sb.append(text.getText());
				}else{ //其它所有标签，原样输出
					sb.append("<");
					sb.append(node.getText());
					sb.append(">");
				}
			}
			
			return sb.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public static <T extends TagNode> String reomveTags(String html,final Class<T> tagType,String attributeName,String attributeValue){
		try {
			StringBuffer sb = new StringBuffer();
			Parser parser = new Parser();
			parser.setInputHTML(html);
			NodeList allNodes = parser.parse(
				new NodeFilter(){
					public boolean accept(Node node) {
						return true;
					}
				}
			);
			
			for(int i=0; i<allNodes.size(); i++){
				Node node = allNodes.elementAt(i);
				if(node.getClass() == tagType){
					TagNode tn = (TagNode)node;
					//如果是符合要求的tag节点标签
					if(StringUtils.equals(tn.getAttribute(attributeName),attributeValue)){
						if(!tn.isEndTag()){ //这是一个开始标签
							allNodes.remove(tn); //移除本节点
							allNodes.remove(tn.getEndTag()); //移除其对应的结束节点
							NodeList nl = tn.getChildren(); //移除其包含的所有子节点
							SimpleNodeIterator sni = nl.elements();
							while(sni.hasMoreNodes()){
								Node n = sni.nextNode();
								allNodes.remove(n);
							}
							i = i -1;
						}
					}else{ //如果不符合要求，原样输出
						sb.append("<");
						sb.append(node.getText());
						sb.append(">");
					}
				}else if(node instanceof TextNode){
					TextNode text = (TextNode)node;
					sb.append(text.getText());
				}else if(node instanceof RemarkNode){
					RemarkNode rn = (RemarkNode)node;
					sb.append(rn.toHtml());
				}
				else{
					sb.append("<");
					sb.append(node.getText());
					sb.append(">");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
}
