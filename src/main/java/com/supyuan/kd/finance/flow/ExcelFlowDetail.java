package com.supyuan.kd.finance.flow;

import com.supyuan.util.excel.poi.FieldComment;

/**
 *收支明细打印类
 */
public class ExcelFlowDetail {
	
	@FieldComment(name = "序号")
	private int orderNum;
	
	@FieldComment(name = "结算流水号")
	private String flow_sn;
	
	@FieldComment(name = "网点")
	private String networdName;
	
	@FieldComment(name = "结算类型")
	private String settlement_type;


	@FieldComment(name = "费用类型")
	private String fee_type;
	
	@FieldComment(name = "方向")
	private String inout_type;
	
	@FieldComment(name = "金额")
	private String fee;
	
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

	@FieldComment(name = "运单号")
	private String ship_sn;

	@FieldComment(name = "运单网点")
	private String shipNetWordName;

	@FieldComment(name = "配载单号")
	private String load_sn;

	@FieldComment(name = "配载网点")
	private String loadNetWordName;

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

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
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

	public String getShip_sn() {
		return ship_sn;
	}

	public void setShip_sn(String ship_sn) {
		this.ship_sn = ship_sn;
	}

	public String getShipNetWordName() {
		return shipNetWordName;
	}

	public void setShipNetWordName(String shipNetWordName) {
		this.shipNetWordName = shipNetWordName;
	}

	public String getLoad_sn() {
		return load_sn;
	}

	public void setLoad_sn(String load_sn) {
		this.load_sn = load_sn;
	}

	public String getLoadNetWordName() {
		return loadNetWordName;
	}

	public void setLoadNetWordName(String loadNetWordName) {
		this.loadNetWordName = loadNetWordName;
	}
}
