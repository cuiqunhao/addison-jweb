package com.internet.cms.service.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.internet.cms.basic.util.PinyinUtil;
import com.internet.cms.dao.topic.IKeywordDao;
import com.internet.cms.dao.topic.ITopicDao;
import com.internet.cms.model.Keyword;
import com.internet.cms.page.Pager;

@Service("keywordService")
public class KeywordService implements IKeywordService {
	private IKeywordDao keywordDao;
	private ITopicDao topicDao;
	
	
	public IKeywordDao getKeywordDao() {
		return keywordDao;
	}

	@Inject
	public void setKeywordDao(IKeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	public ITopicDao getTopicDao() {
		return topicDao;
	}

	@Inject
	public void setTopicDao(ITopicDao topicDao) {
		this.topicDao = topicDao;
	}

	/**
	 * 添加或者更新关键字
	 * 如果这个关键字不存在就进行添加，如果存在就进行更新，让引用次数加1
	 * @param name 关键字
	 */
	@Override
	public void addOrUpdate(String name) {
		//根据关键字查询在当前数据库中是否存在
		Keyword k = keywordDao.findKeywordByName(name);
		if(k==null) {
			k = new Keyword();
			k.setName(name);
			k.setNameFullPy(PinyinUtil.str2Pinyin(name, null));
			k.setNameShortPy(PinyinUtil.strFirst2Pinyin(name));
			k.setTimes(1);
			keywordDao.add(k);
		} else {
			k.setTimes(k.getTimes()+1);
			keywordDao.update(k);
		}
	}
	
	@Override
	public List<Keyword> getKeywordByTimes(int num) {
		List<Keyword> ks = keywordDao.findUseKeyword();
		List<Keyword> kks = new ArrayList<Keyword>();
		for(Keyword k:ks) {
			if(k.getTimes()>=num) kks.add(k);
			else break;
		}
		return kks;
	}

	@Override
	public List<Keyword> getMaxTimesKeyword(int num) {
		Map<String,Integer> allKeys = this.getExistKeyword2Map();
		List<Keyword> ks = this.findUseKeyword(allKeys);
		List<Keyword> kks = new ArrayList<Keyword>();
		if(ks.size()<=num) return ks;
		for(int i=0;i<=num;i++) {
			kks.add(ks.get(i));
		}
		return kks;
	}
	
	private Map<String,Integer> getExistKeyword2Map() {
		List<String> allKeys = topicDao.getExistKeyword2Map();
		Map<String,Integer> keys = new HashMap<String,Integer>();
		for(String ak:allKeys) {
			String[] ks = ak.split("\\|");
			for(String k:ks) {
				if("".equals(k.trim())) continue;
				if(keys.containsKey(k)) {
					keys.put(k, keys.get(k)+1);
				} else {
					keys.put(k, 1);
				}
			}
		}
		return keys;
	}

	private List<Keyword> findUseKeyword(Map<String,Integer> allKeys ) {
		Set<String> keys = allKeys.keySet();
		List<Keyword> ks = new ArrayList<Keyword>();
		for(String k:keys) {
			ks.add(new Keyword(k,allKeys.get(k)));
		}
		Collections.sort(ks);
		return ks;
	}

	@Override
	public Pager<Keyword> findNoUseKeyword() {
		return keywordDao.findNoUseKeyword();
	}

	@Override
	public void clearNoUseKeyword() {
		keywordDao.clearNoUseKeyword();
	}

	@Override
	public List<Keyword> findUseKeyword() {
		return keywordDao.findUseKeyword();
	}

	@Override
	public List<Keyword> listKeywordByCon(String con) {
		return keywordDao.listKeywordByCon(con);
	}

	/**
	 * 根据关键字联想已经在数据库中存在的关键字（创建文档时输入关键字并联想）
	 * @param name 创建文档输入的关键字
	 * @return 关键字
	 */
	@Override
	public List<String> listKeywordStringByCon(String con) {
		return keywordDao.listKeywordStringByCon("%"+con+"%");
	}

}
