package com.supyuan.kd.report;


import com.supyuan.util.excel.poi.FieldComment;

/**
 * 应收汇总实体
 * @author chenan
 *
 * Date:2018年3月20日下午4:09:30
 * 
 */
public class RePayModel {
	
	@FieldComment(name="序号")
	private int orderNum;

	@FieldComment(name="费用类型")
	private int Feetype;

	@FieldComment(name="费用名称")
	private String FeeName;

	@FieldComment(name="应收数量")
	private long  receivablesShipCount;

	@FieldComment(name="应付数量")
	private long  payShipCount;

	@FieldComment(name="应收合计")
	private double  receivablesTotal;
	
	@FieldComment(name="应收已收")
	private double  receivablesGet;

	@FieldComment(name="应收未收")
	private double  receivablesNOGet;

	@FieldComment(name="应付合计")
	private double  payTotal;

	@FieldComment(name="应付已收")
	private double  payGet;

	@FieldComment(name="应付未收")
	private double  payNOGet;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getFeetype() {
		return Feetype;
	}

	public void setFeetype(int feetype) {
		Feetype = feetype;
	}

	public String getFeeName() {
		return FeeName;
	}

	public void setFeeName(String feeName) {
		FeeName = feeName;
	}

	public long getReceivablesShipCount() {
		return receivablesShipCount;
	}

	public void setReceivablesShipCount(long receivablesShipCount) {
		this.receivablesShipCount = receivablesShipCount;
	}

	public long getPayShipCount() {
		return payShipCount;
	}

	public void setPayShipCount(long payShipCount) {
		this.payShipCount = payShipCount;
	}

	public double getReceivablesTotal() {
		return receivablesTotal;
	}

	public void setReceivablesTotal(double receivablesTotal) {
		this.receivablesTotal = receivablesTotal;
	}

	public double getReceivablesGet() {
		return receivablesGet;
	}

	public void setReceivablesGet(double receivablesGet) {
		this.receivablesGet = receivablesGet;
	}

	public double getReceivablesNOGet() {
		return receivablesNOGet;
	}

	public void setReceivablesNOGet(double receivablesNOGet) {
		this.receivablesNOGet = receivablesNOGet;
	}

	public double getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(double payTotal) {
		this.payTotal = payTotal;
	}

	public double getPayGet() {
		return payGet;
	}

	public void setPayGet(double payGet) {
		this.payGet = payGet;
	}

	public double getPayNOGet() {
		return payNOGet;
	}

	public void setPayNOGet(double payNOGet) {
		this.payNOGet = payNOGet;
	}
}
