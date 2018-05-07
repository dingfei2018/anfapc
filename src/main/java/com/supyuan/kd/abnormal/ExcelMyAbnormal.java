package com.supyuan.kd.abnormal;


import com.supyuan.util.excel.poi.FieldComment;

public class ExcelMyAbnormal {
	
	@FieldComment(name = "序号")
	private int orderNum;
	
	@FieldComment(name = "异常编号")
	private String abnormalSn;
	
	@FieldComment(name = "运单号")
	private String shipSn;
	
	@FieldComment(name = "异常状态")
	private String abnormalStatus;
	
	@FieldComment(name = "异常类型")
	private String abnormalType;
	
	@FieldComment(name = "运单开单网点")
	private String shipNet;
	
	@FieldComment(name = "出发地")
	private String fromAdd;
	
	@FieldComment(name = "到达地")
	private String toAdd;
	
	@FieldComment(name = "托运方")
	private String senderName;
	
	@FieldComment(name = "收货方")
	private String receiverName;
	
	@FieldComment(name = "登记日期")
	private String create_time;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getAbnormalSn() {
		return abnormalSn;
	}

	public void setAbnormalSn(String abnormalSn) {
		this.abnormalSn = abnormalSn;
	}

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

	public String getAbnormalStatus() {
		return abnormalStatus;
	}

	public void setAbnormalStatus(String abnormalStatus) {
		this.abnormalStatus = abnormalStatus;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getShipNet() {
		return shipNet;
	}

	public void setShipNet(String shipNet) {
		this.shipNet = shipNet;
	}

	public String getFromAdd() {
		return fromAdd;
	}

	public void setFromAdd(String fromAdd) {
		this.fromAdd = fromAdd;
	}

	public String getToAdd() {
		return toAdd;
	}

	public void setToAdd(String toAdd) {
		this.toAdd = toAdd;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
