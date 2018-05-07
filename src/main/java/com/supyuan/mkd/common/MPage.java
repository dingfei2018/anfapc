package com.supyuan.mkd.common;

import java.util.List;

import com.supyuan.component.base.KdBaseModel;

public class MPage <T extends KdBaseModel<?>>{
	
	private List<T> list;	
	
	private int pageNumber;	
	
	private int totalRow;
	
	private boolean hasMore;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}			
	

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	
}
