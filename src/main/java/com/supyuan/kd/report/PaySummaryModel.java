package com.supyuan.kd.report;


import com.supyuan.util.excel.poi.FieldComment;

/**
 * 应付汇总实体
 * @author chenan
 *
 * Date:2017年12月27日下午7:09:30 
 * 
 */
public class PaySummaryModel {
	
	@FieldComment(name="月份")
	private String time;
	
	@FieldComment(name="中转单量")
	private long  transferCount;
	
	@FieldComment(name="中转应付")
	private double  transferfee;
	
	@FieldComment(name="配载车次")
	private long  countLoad;
	
	@FieldComment(name="配载应付")
	private double  loadFee;
	
	@FieldComment(name="网点对账应付")
	private double  netPayfee;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(long transferCount) {
		this.transferCount = transferCount;
	}

	public double getTransferfee() {
		return transferfee;
	}

	public void setTransferfee(double transferfee) {
		this.transferfee = transferfee;
	}

	public long getCountLoad() {
		return countLoad;
	}

	public void setCountLoad(long countLoad) {
		this.countLoad = countLoad;
	}

	public double getLoadFee() {
		return loadFee;
	}

	public void setLoadFee(double loadFee) {
		this.loadFee = loadFee;
	}

	public double getNetPayfee() {
		return netPayfee;
	}

	public void setNetPayfee(double netPayfee) {
		this.netPayfee = netPayfee;
	}

	


	

}
