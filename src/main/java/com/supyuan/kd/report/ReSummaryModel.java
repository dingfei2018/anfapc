package com.supyuan.kd.report;


import com.supyuan.util.excel.poi.FieldComment;

/**
 * 应收汇总实体
 * @author chenan
 *
 * Date:2017年12月26日下午4:09:30 
 * 
 */
public class ReSummaryModel {
	
	@FieldComment(name="月份")
	private String time;
	
	@FieldComment(name="运单量")
	private long  shipCount;
	
	@FieldComment(name="营业额")
	private double  allfee;
	
	@FieldComment(name="网点对账应收")
	private double  netRefee;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getShipCount() {
		return shipCount;
	}

	public void setShipCount(long shipCount) {
		this.shipCount = shipCount;
	}

	public double getAllfee() {
		return allfee;
	}

	public void setAllfee(double allfee) {
		this.allfee = allfee;
	}

	public double getNetRefee() {
		return netRefee;
	}

	public void setNetRefee(double netRefee) {
		this.netRefee = netRefee;
	}
		


	

}
