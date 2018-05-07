package com.supyuan.jfinal.base;

import java.io.Serializable;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;

public class EasyUIPage <T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7456624931937899950L;

	private int total;
	
	private List<T> rows;
	
	public EasyUIPage() {
		// TODO Auto-generated constructor stub
	}

	public EasyUIPage(int total, List<T> list) {
		super();
		this.total = total;
		this.rows = list;
	}
	
	public EasyUIPage(Page<T> page){
		this.total = page.getTotalRow();
		this.rows = page.getList();
	}
	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}


}
