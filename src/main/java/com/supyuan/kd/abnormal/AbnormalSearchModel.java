package com.supyuan.kd.abnormal;

/**
 * 异常searchmodel
 */

import com.supyuan.jfinal.base.BindModel;

public class AbnormalSearchModel extends BindModel{
	String abnormalNet;
	String shipNet;
	String abnormalStatus;
	String abnormalType;
	String toAddr;
	String fromAddr;
	String sender;
	String receiver;
	public String getAbnormalNet() {
		return abnormalNet;
	}
	public void setAbnormalNet(String abnormalNet) {
		this.abnormalNet = abnormalNet;
	}
	public String getShipNet() {
		return shipNet;
	}
	public void setShipNet(String shipNet) {
		this.shipNet = shipNet;
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
	public String getToAddr() {
		return toAddr;
	}
	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
}
