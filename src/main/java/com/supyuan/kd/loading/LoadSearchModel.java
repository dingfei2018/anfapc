package com.supyuan.kd.loading;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.supyuan.jfinal.base.BindModel;

public class LoadSearchModel extends BindModel{
	
	private String startCode;
	
	private String endCode;
	
	private String shipSn;
	
	private int sendName;
	
	private int receiveName;

	private int networkId;
	
	private int loadNetworkId;
	
	private int type;
	
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

	public void setShipSn(String shipSn) {
		this.shipSn = shipSn;
	}

	public int getSendName() {
		return sendName;
	}

	public void setSendName(int sendName) {
		this.sendName = sendName;
	}

	public int getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(int receiveName) {
		this.receiveName = receiveName;
	}
	

	public int getLoadNetworkId() {
		return loadNetworkId;
	}

	public void setLoadNetworkId(int loadNetworkId) {
		this.loadNetworkId = loadNetworkId;
	}

	public int getNetworkId() {
		return networkId;
	}

	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
