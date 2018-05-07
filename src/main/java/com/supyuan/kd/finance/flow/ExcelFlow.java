package com.supyuan.kd.finance.flow;

import com.supyuan.util.excel.poi.FieldComment;

import java.math.BigDecimal;

/**
 *收支流水打印类
 */
public class ExcelFlow {
	
	@FieldComment(name = "序号")
	private int orderNum;
	
	@FieldComment(name = "结算流水号")
	private String flow_sn;
	
	@FieldComment(name = "网点")
	private String networdName;
	
	@FieldComment(name = "结算类型")
	private String settlement_type;
	
	@FieldComment(name = "方向")
	private String inout_type;
	
	@FieldComment(name = "金额")
	private String fee;
	
	@FieldComment(name = "收入")
	private String income;
	
	@FieldComment(name = "支出")
	private String expenditure ;
	
	@FieldComment(name = "收支方式")
	private String pay_type;
	
	@FieldComment(name = "收支账号")
	private String flow_no;
	
	@FieldComment(name = "交易凭证号")
	private String voucher_no;
	
	@FieldComment(name = "结算时间")
	private String create_time;
	
	@FieldComment(name = "结算人")
	private String userName;
	
	@FieldComment(name = "备注")
	private String remark;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFlow_sn() {
		return flow_sn;
	}

	public void setFlow_sn(String flow_sn) {
		this.flow_sn = flow_sn;
	}

	public String getNetwordName() {
		return networdName;
	}

	public void setNetwordName(String networdName) {
		this.networdName = networdName;
	}

	public String getSettlement_type() {
		return settlement_type;
	}

	public void setSettlement_type(String settlement_type) {
		this.settlement_type = settlement_type;
	}

	public String getInout_type() {
		return inout_type;
	}

	public void setInout_type(String inout_type) {
		this.inout_type = inout_type;
	}


	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getFlow_no() {
		return flow_no;
	}

	public void setFlow_no(String flow_no) {
		this.flow_no = flow_no;
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
