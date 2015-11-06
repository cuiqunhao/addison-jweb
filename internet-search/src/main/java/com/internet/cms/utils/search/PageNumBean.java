package com.internet.cms.utils.search;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PageNumBean {
	private Integer upPageNum; // 上一页页码

	private Integer downPageNum; // 下一页页码

	private Integer firstPageNum; // 首页

	private Integer endPageNum; // 尾页

	private List pages; // 需要显示连续的页码

	private Integer perPageRecordCount; // 每页显示记录数

	private Integer currentNum; // 当前页码

	private Integer pageCount; // 总页数

	private Integer recordCount; // 总记录数

	private Integer beginRecordIndex; // 当前页开始显示的记录数 从0开始

	private Integer perPageNumCount; // 每页显示的页码数

	public PageNumBean(int currentNum, int recordCount, int perPageRecordCount,
			int perPageNumCount) {
		this.currentNum = new Integer(currentNum);// 设置当前页码
		this.perPageRecordCount = new Integer(perPageRecordCount);// 每页显示记录数
		this.recordCount = new Integer(recordCount); // 设置总的记录数
		this.perPageNumCount = new Integer(perPageNumCount);// 每页显示的页码数
		setValue();
	}

	public final void setValue() {

		int _page = -1;
		int _perPageRecordCount = -1;
		int _recordCount = -1;
		int _pageCount = -1;
		int _pageNumBegin = -1;
		int _beginIndex = -1;
		int _upPageNum = -1;
		int _downPageNum = -1;
		int _perPageNumCount = -1;
		int _firstPageNum = -1;
		int _endPageNum = -1;

		List _pages = new ArrayList();
		_page = this.currentNum.intValue();
		_perPageRecordCount = this.perPageRecordCount.intValue();
		_recordCount = this.recordCount.intValue();
		_perPageNumCount = this.perPageNumCount.intValue();
		_pageCount = _recordCount / _perPageRecordCount
				+ (_recordCount % _perPageRecordCount == 0 ? 0 : 1); // 计算出总的页数
		_pageNumBegin = ((_page - 1) / _perPageNumCount) * _perPageNumCount + 1;

		for (int i = 0; i < _perPageNumCount && _pageNumBegin <= _pageCount; i++) {
			String pageNum = String.valueOf(_pageNumBegin);
			_pages.add(pageNum);
			_pageNumBegin++;
		}

		_beginIndex = (_page - 1) * _perPageRecordCount;

		if (_pageCount != -1 && _page != 1) {
			_firstPageNum = 1;
		}

		if (_page > 1) {
			_upPageNum = _page - 1;
		}

		if (_page + 1 <= _pageCount) {
			_downPageNum = _page + 1;
		}

		if (_pageCount != -1) {
			this.pageCount = new Integer(_pageCount);
		}

		if (_beginIndex != -1) {
			this.beginRecordIndex = new Integer(_beginIndex);
		}

		if (_upPageNum != -1) {
			this.upPageNum = new Integer(_upPageNum);
		}

		if (_downPageNum != -1) {
			this.downPageNum = new Integer(_downPageNum);
		}

		if (_firstPageNum != -1) {
			this.firstPageNum = new Integer(_firstPageNum);
		}

		if (_pageCount != -1 && _page != _pageCount) {
			this.endPageNum = new Integer(_pageCount);
		}

		this.pages = _pages;
	}

	public Integer getBeginRecordIndex() {
		return beginRecordIndex;
	}

	public void setBeginRecordIndex(Integer beginRecordIndex) {
		this.beginRecordIndex = beginRecordIndex;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

	public Integer getDownPageNum() {
		return downPageNum;
	}

	public void setDownPageNum(Integer downPageNum) {
		this.downPageNum = downPageNum;
	}

	public List getPages() {
		return pages;
	}

	public void setPages(List pages) {
		this.pages = pages;
	}

	public Integer getPerPageRecordCount() {
		return perPageRecordCount;
	}

	public void setPerPageRecordCount(Integer perPageRecordCount) {
		this.perPageRecordCount = perPageRecordCount;
	}

	public Integer getUpPageNum() {
		return upPageNum;
	}

	public void setUpPageNum(Integer upPageNum) {
		this.upPageNum = upPageNum;
	}

	public Integer getFirstPageNum() {
		return firstPageNum;
	}

	public void setFirstPageNum(Integer firstPageNum) {
		this.firstPageNum = firstPageNum;
	}

	public Integer getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(Integer endPageNum) {
		this.endPageNum = endPageNum;
	}

	public Integer getPerPageNumCount() {
		return perPageNumCount;
	}

	public void setPerPageNumCount(Integer perPageNumCount) {
		this.perPageNumCount = perPageNumCount;
	}
}