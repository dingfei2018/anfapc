package com.supyuan.modules.message;

public class SendInfo {
	
	private String time;
	
	private int num;
	
	public SendInfo() {
	}

	public SendInfo(String time, int num) {
		super();
		this.time = time;
		this.num = num;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
