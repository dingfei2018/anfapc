package com.supyuan.modules.express;

import java.util.Date;

public class Locations {
	
	private String  address;
	
	private Date  time;
	
	private int status;
	
	public Locations() {}
	
	public Locations(String address, Date time, int status) {
		super();
		this.address = address;
		this.time = time;
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
