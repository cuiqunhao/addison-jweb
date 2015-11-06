package com.internet.cms.service.spider;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ParagraphTag;

import com.internet.cms.basic.util.HttpUtils;
import com.internet.cms.basic.util.ParseUtils;
import com.internet.cms.model.Attachment;
import com.internet.cms.model.Topic;


public class SpiderForIBM extends Spider{

	@Override
	public void execute() {
		
		try {
			//根据URL地址，获取网页内容
			String html = HttpUtils.getHtml(httpclient, url);
			
			if(html == null){
				throw new RuntimeException("无法获取【"+url+"】网址的内容");
			}
			
			Topic a = new Topic();
			
			//设置文章的来源
			a.setSource("www.ibm.com");
			
			//对网页内容进行分析和提取
			//设置文章的标题
			MetaTag titleTag = ParseUtils.parseTag(html, MetaTag.class, "name", "title");
			a.setTitle(titleTag.getMetaContent());

			//设置文章的关键字
			MetaTag keywordTag = ParseUtils.parseTag(html, MetaTag.class, "name", "Keywords");
			if(keywordTag.getMetaContent().length() > 255){
				a.setKeyword(keywordTag.getMetaContent().substring(0, 255));
			}
			
			//设置文章的简介
			MetaTag introTag = ParseUtils.parseTag(html, MetaTag.class, "name", "Abstract");
			a.setSummary(introTag.getMetaContent());
			
			//设置文章的作者
			List<Div> authors = ParseUtils.parseTags(html, Div.class, "class", "author");
			String author = "";
			for(int i=0; i<authors.size(); i++){
				if(i != 0){
					author = author + ",";
				}
				Div div = authors.get(i);
				author = author + ParseUtils.parseTag(div.getStringText(), LinkTag.class).getStringText();
			}
			a.setAuthor(author);
			
			//设置文章的内容
			String content = StringUtils.substringBetween(html, "<!-- MAIN_COLUMN_CONTENT_BEGIN -->", "<!-- CMA");
			
			//查询文章的内容中所包含的图片，并下载到upload目录，然后创建Attachment对象，设置到Article对象中
			List<ImageTag> imageTags = ParseUtils.parseTags(content, ImageTag.class);
			if(imageTags != null){
				for(ImageTag it:imageTags){
					
					//得到图片所在的路径目录
					String baseUrl = url.substring(0, url.lastIndexOf("/")+1);
					
					//这个是<img>标签中的src的值
					String imageUrl = it.getImageURL();
					
					//图片的绝对路径
					String absoluteUrl = baseUrl + imageUrl;

					//:   "文章标题/xxx.jpg"
					String imageName = a.getTitle().replaceAll("/|\\\\|\\:|\\*|\\?|\\||\\<|>", "_")+"/" + imageUrl;
					
					//把图片保存到upload目录
					//首先确定，保存到本地的图片的路径
					String imageLocalFile = "";//Attachment.ATTACHMENT_DIR + imageName;
					
					//如果图片已经被下载到本地，则不再下载
					if(!new File(imageLocalFile).exists()){
						//下载图片的信息
						byte[] image = HttpUtils.getImage(httpclient, absoluteUrl);
						//直接使用new FileOutputStream(imageLocalFile)这种方式，创建一个
						//文件输出流，存在的问题就是：如果这个文件所在的目录不存在，则创建不了
						//输出流，会抛出异常！
						//所以，使用辅助的工具类来创建一个文件输出流:FileUtils.openOutputStream(new File(imageLocalFile))
						//通过这个方法，当文件所在的父目录不存在的时候，将自动创建其所有的父目录
						IOUtils.write(image, FileUtils.openOutputStream(new File(imageLocalFile)));
						System.out.println("图片【"+absoluteUrl+"】已下载");
					}
					
					//针对每张图片，创建一个Attachment对象
					Attachment attachment = new Attachment();
					attachment.setType("image/jpeg");
					attachment.setOldName(imageName);
					//a.addAttachment(attachment);
				}
			}
			
			//修改content中的所有图片的src的值
			//将src的值，加上前缀：upload_image/文章标题/图片.jpg
			content = ParseUtils.modifyImageUrl(content, "upload_image/"+a.getTitle().replaceAll("/|\\\\|\\:|\\*|\\?|\\||\\<|>", "_")+"/");
			
			//删除<hr>和"回首页"的链接标签
			content = ParseUtils.reomveTags(content, Div.class, "class", "ibm-alternate-rule");
			content = ParseUtils.reomveTags(content, ParagraphTag.class, "class", "ibm-ind-link ibm-back-to-top");
			
			a.setContent(content);
			
			//将文章对象放入HttpContext
			List<Topic> articles = new ArrayList<Topic>();
			articles.add(a);
			
			context.setAttribute("articles", articles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
