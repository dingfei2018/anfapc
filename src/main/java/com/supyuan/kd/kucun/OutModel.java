package com.supyuan.kd.kucun;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 出库列表实体
 * @author liangxp
 *
 * Date:2018年1月2日上午11:44:43 
 * 
 * @email liangxp@anfawuliu.com
 */
public class OutModel {
	
	@FieldComment(name="运单号")
	private String shipSn;
	
	@FieldComment(name="库存网点")
	private String  snetworkName;
	
	@FieldComment(name="入库时间")
	private String  creatTime;
	
	@FieldComment(name="出库时间")
	private String  outtime;
	
	@FieldComment(name="在库时长（小时）")
	private String  hashours;
	
	@FieldComment(name="货物名称")
	private String  proname;
	
	@FieldComment(name="体积")
	private double  shipVolume;
	
	@FieldComment(name="重量")
	private double  shipWeight;
	
	@FieldComment(name="件数")
	private double  shipAmount;
	
	@FieldComment(name="开单网点")
	private String  enetworkName;
	
	@FieldComment(name="托运方")
	private String  sendName;
	
	@FieldComment(name="收货方")
	private String  receiveName;
	
	@FieldComment(name="出发地")
	private String  fromAddr;
	
	@FieldComment(name="到达地")
	private String  toAddr;

	public String getShipSn() {
		return shipSn;
	}

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

	public String getSnetworkName() {
		return snetworkName;
	}

	public void setSnetworkName(String snetworkName) {
		this.snetworkName = snetworkName;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}


	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public String getHashours() {
		return hashours;
	}

	public void setHashours(String hashours) {
		this.hashours = hashours;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public double getShipVolume() {
		return shipVolume;
	}

	public void setShipVolume(double shipVolume) {
		this.shipVolume = shipVolume;
	}

	public double getShipWeight() {
		return shipWeight;
	}

	public void setShipWeight(double shipWeight) {
		this.shipWeight = shipWeight;
	}

	public double getShipAmount() {
		return shipAmount;
	}

	public void setShipAmount(double shipAmount) {
		this.shipAmount = shipAmount;
	}

	public String getEnetworkName() {
		return enetworkName;
	}

	public void setEnetworkName(String enetworkName) {
		this.enetworkName = enetworkName;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

}
