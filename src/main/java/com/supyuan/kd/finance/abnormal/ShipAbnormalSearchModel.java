package com.supyuan.kd.finance.abnormal;

import com.supyuan.jfinal.base.BindModel;

/**
 * 异动管理的查询类
 */
public class ShipAbnormalSearchModel extends BindModel{
	/**
	 * 开单网点
	 */
	private String netWorkId;

	/**
	 * 运单id
	 */
	private String id;

	/**
	 * 异动结算网点
	 */
	private String abnormalNetWorkId;


	/**
	 * 开单开始时间
	 */
	private String startTime;

	/**
	 * 开单结束时间
	 */
	private String endTime;

	/**
	 * 出发地
	 */
	private String fromCode;

	/**
	 * 到达地
	 */
	private String toCode;

	/**
	 * 托运方
	 */
	private String senderId;

	/**
	 * 收货方
	 */
	private String receiverId;


	/**
	 * 异动结算状态
	 */
	private  String feeStatus;

	/**
	 * 运单号
	 */
	private String shipSn;

	public String getNetWorkId() {
		return netWorkId;
	}

	public void setNetWorkId(String netWorkId) {
		this.netWorkId = netWorkId;
	}

	public String getAbnormalNetWorkId() {
		return abnormalNetWorkId;
	}

	public void setAbnormalNetWorkId(String abnormalNetWorkId) {
		this.abnormalNetWorkId = abnormalNetWorkId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
