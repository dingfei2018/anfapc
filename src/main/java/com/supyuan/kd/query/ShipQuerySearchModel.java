package com.supyuan.kd.query;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.supyuan.jfinal.base.BindModel;

/**
 * 运单查询
 * @author liangxp
 *
 * Date:2017年12月19日下午4:26:57 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ShipQuerySearchModel extends BindModel {
	
private String startCode;
	
	private String endCode;
	
	private String shipSn;
	private String senderId;
	private String receiverId;

	private int networkId;
	private String createTimeStart;
	private String createTimeEnd;
	private String shipStatus;
	private String shipCustomerNumber;
	
	

	public int getNetworkId() {
		return networkId;
	}

	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}

	

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public String getShipCustomerNumber() {
		return shipCustomerNumber;
	}

	public void setShipCustomerNumber(String shipCustomerNumber) {
		this.shipCustomerNumber = shipCustomerNumber;
	}

	public String getStartCode() {
		return startCode;
	}

	public void setStartCode(String startCode) {
		this.startCode = startCode;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getShipSn() {
		try {
			return StringUtils.isNotBlank(shipSn)? URLDecoder.decode(shipSn , "UTF-8").trim():null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
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

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

}
