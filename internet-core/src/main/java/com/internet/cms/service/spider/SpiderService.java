package com.internet.cms.service.spider;

import java.util.List;

import com.internet.cms.model.Topic;

public interface SpiderService {
	public List<Topic> collect(String url,String[] channelIds);
}
