package com.internet.cms.service.spider;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.internet.cms.model.Topic;


public abstract class Spider {
	private static Map<String,Class<? extends Spider>> spiders = new HashMap<String,Class<? extends Spider>>();
	static{
		spiders.put("www.ibm.com", SpiderForIBM.class);
		spiders.put("www.oracle.com", SpiderForIBM.class);
	}
	
	protected HttpClient httpclient;
	protected String url;
	//存放在解释过程中的状态信息，比如：Article对象列表
	protected HttpContext context; 
	
	//根据URL网址，创建相应的Spider对象
	public static Spider getInstance(String url){
		try {
			//根据URL选择不同的子类
			URL u = new URL(url);
			String host = u.getHost(); //得到的是：www.ibm.com这样的主机地址串
			return spiders.get(host).newInstance(); //创建Spider对象
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无法找到【"+url+"】对应的爬虫！");
		}
	}
	
	//收集文章
	public List<Topic> collect(String url){
		
		//创建HttpClient
		this.httpclient = new DefaultHttpClient();
		this.context = new BasicHttpContext();
		this.url = url;
		
		//设置网络代理
//		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//				new HttpHost("192.168.1.1", 808));
		
		//执行收集过程
		execute();
		
		httpclient.getConnectionManager().shutdown();
		
		//获取收集到的文章
		List<Topic> articles = (List<Topic>)context.getAttribute("articles");
		
		//返回文章列表
		return articles;
	}
	
	public abstract void execute();
}
