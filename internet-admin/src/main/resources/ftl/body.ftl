<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<#macro indexTopicList indexTopic titleSize showDate=true hasH=false hasDt=true showDot=true df="MM/dd" divId="" hrefClz="">
	<div ${(divId=="")?string("","id='${divId}'")}>
		<#if hasH>
			<#nested/>
		</#if>
		<dl>
			<#if hasDt>
				<dt><#nested/></dt>
			</#if>
			<#list indexTopic.topics as topic>
				<dd>
					<a title="${topic.title}" href="topic/${topic.id}" ${(hrefClz=="")?string("","class='${hrefClz}'")}>
						<#if showDate>[${(topic.publishDate)?string("${df}")}]</#if>
						<#if topic.title?length gt titleSize>
							${topic.title[0..titleSize]}<#if showDot>...</#if>
						<#else>
							${topic.title}
						</#if>
					</a>
				</dd>
			</#list>
		</dl>
	</div>
</#macro>
<div id="content">
	<div id="content_con">
		<div id="xiaoxun"></div>
		<div id="notice_rollpic">
			<@indexTopicList indexTopic=ts["1"] titleSize=12 divId="notice" hrefClz="index_link">
				<span><a href="channel/${ts["1"].cid}" class="index_title_href">${ts["1"].cname}</a></span>
			</@indexTopicList>
			<div id="rollpic">
				<div id="rollCaption"><span></span></div>
				<div id="rollPager"></div>
				<#list pics as pic>
					<a href="${pic.linkUrl}" title="${pic.title}"><img src="<%=request.getContextPath()%>/resources/indexPic/${pic.newName}" border="0"/></a>
				</#list>
			</div>
		</div>
		<div id="split_line"></div>
		<div id="xwgk_xxgk">
			<@indexTopicList indexTopic=ts["2"] hasH=true hasDt=false titleSize=37 divId="xwgk" hrefClz="index_link">
				<h3><a href="channel/${ts["2"].cid}" class="index_title_href">${ts["2"].cname}</a></h3>
				<div id="xwgk_bg"></div>
			</@indexTopicList>
			<div id="xxgk">
				<h3><a href="channel/7" class="index_title_href">学校概况</a></h3>
				<div id="xxgk_bg"></div>
			</div>
		</div>
		<div id="hdjx_jyky">
			<@indexTopicList indexTopic=ts["3"] titleSize=31 divId="hdjx" hrefClz="index_link">
				<span class="t_title">${ts["3"].cname}</span><span class="more"><a href="channel/${ts['3'].cid}">更多</a></span>
			</@indexTopicList>
			
			<@indexTopicList indexTopic=ts["4"] titleSize=31 divId="jyky" hrefClz="index_link">
				<span class="t_title">${ts["4"].cname}</span><span class="more"><a href="channel/${ts['4'].cid}">更多</a></span>
			</@indexTopicList>
		</div>
		
		
		<div id="notice_rollpic">
			<@indexTopicList indexTopic=ts["5"] titleSize=12 divId="notice" hrefClz="index_link">
				<span><a href="channel/${ts["5"].cid}" class="index_title_href">${ts["5"].cname}</a></span>
			</@indexTopicList>
		</div>
		<div id="split_line"></div>
		<div id="xwgk_xxgk">
			<@indexTopicList indexTopic=ts["6"] hasH=true hasDt=false titleSize=37 divId="xwgk" hrefClz="index_link">
				<h3><a href="channel/${ts["6"].cid}" class="index_title_href">${ts["6"].cname}</a></h3>
				<div id="xwgk_bg"></div>
			</@indexTopicList>
		</div>
		<div id="hdjx_jyky">
			<@indexTopicList indexTopic=ts["7"] titleSize=31 divId="hdjx" hrefClz="index_link">
				<span class="t_title">${ts["7"].cname}</span><span class="more"><a href="channel/${ts['7'].cid}">更多</a></span>
			</@indexTopicList>
			
			<@indexTopicList indexTopic=ts["8"] titleSize=31 divId="jyky" hrefClz="index_link">
				<span class="t_title">${ts["8"].cname}</span><span class="more"><a href="channel/${ts['8'].cid}">更多</a></span>
			</@indexTopicList>
		</div>
		
		
		
		<div id="notice_rollpic">
			<@indexTopicList indexTopic=ts["9"] titleSize=12 divId="notice" hrefClz="index_link">
				<span><a href="channel/${ts["9"].cid}" class="index_title_href">${ts["9"].cname}</a></span>
			</@indexTopicList>
		</div>
		<div id="split_line"></div>
		<div id="xwgk_xxgk">
			<@indexTopicList indexTopic=ts["10"] hasH=true hasDt=false titleSize=37 divId="xwgk" hrefClz="index_link">
				<h3><a href="channel/${ts["10"].cid}" class="index_title_href">${ts["10"].cname}</a></h3>
				<div id="xwgk_bg"></div>
			</@indexTopicList>
		</div>
		<div id="hdjx_jyky">
			<@indexTopicList indexTopic=ts["11"] titleSize=31 divId="hdjx" hrefClz="index_link">
				<span class="t_title">${ts["11"].cname}</span><span class="more"><a href="channel/${ts['11'].cid}">更多</a></span>
			</@indexTopicList>
			
			<@indexTopicList indexTopic=ts["12"] titleSize=31 divId="jyky" hrefClz="index_link">
				<span class="t_title">${ts["12"].cname}</span><span class="more"><a href="channel/${ts['12'].cid}">更多</a></span>
			</@indexTopicList>
		</div>
		
		
		<div id="notice_rollpic">
			<@indexTopicList indexTopic=ts["13"] titleSize=12 divId="notice" hrefClz="index_link">
				<span><a href="channel/${ts["13"].cid}" class="index_title_href">${ts["13"].cname}</a></span>
			</@indexTopicList>
		</div>
		<div id="split_line"></div>
		<div id="xwgk_xxgk">
			<@indexTopicList indexTopic=ts["14"] hasH=true hasDt=false titleSize=37 divId="xwgk" hrefClz="index_link">
				<h3><a href="channel/${ts["14"].cid}" class="index_title_href">${ts["14"].cname}</a></h3>
				<div id="xwgk_bg"></div>
			</@indexTopicList>
		</div>
		<div id="hdjx_jyky">
			<@indexTopicList indexTopic=ts["15"] titleSize=31 divId="hdjx" hrefClz="index_link">
				<span class="t_title">${ts["15"].cname}</span><span class="more"><a href="channel/${ts['15'].cid}">更多</a></span>
			</@indexTopicList>
			
			<@indexTopicList indexTopic=ts["16"] titleSize=31 divId="jyky" hrefClz="index_link">
				<span class="t_title">${ts["16"].cname}</span><span class="more"><a href="channel/${ts['16'].cid}">更多</a></span>
			</@indexTopicList>
		</div>
		
		
		<div id="notice_rollpic">
			<@indexTopicList indexTopic=ts["17"] titleSize=12 divId="notice" hrefClz="index_link">
				<span><a href="channel/${ts["17"].cid}" class="index_title_href">${ts["17"].cname}</a></span>
			</@indexTopicList>
		</div>
		<div id="split_line"></div>
		<div id="xwgk_xxgk">
			<@indexTopicList indexTopic=ts["18"] hasH=true hasDt=false titleSize=37 divId="xwgk" hrefClz="index_link">
				<h3><a href="channel/${ts["18"].cid}" class="index_title_href">${ts["18"].cname}</a></h3>
				<div id="xwgk_bg"></div>
			</@indexTopicList>
		</div>
		<div id="hdjx_jyky">
			<@indexTopicList indexTopic=ts["19"] titleSize=31 divId="hdjx" hrefClz="index_link">
				<span class="t_title">${ts["19"].cname}</span><span class="more"><a href="channel/${ts['19'].cid}">更多</a></span>
			</@indexTopicList>
			
			<@indexTopicList indexTopic=ts["20"] titleSize=31 divId="jyky" hrefClz="index_link">
				<span class="t_title">${ts["20"].cname}</span><span class="more"><a href="channel/${ts['20'].cid}">更多</a></span>
			</@indexTopicList>
		</div>
		
		
		<div id="chief_keyword">
			<div>
				<#list keywords as kw>
					<span class="keyword" href="keyword/${kw.name}">${kw.name}</span>
				</#list>
			</div>
		</div>
	</div>
</div>