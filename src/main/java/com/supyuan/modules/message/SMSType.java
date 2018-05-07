package com.supyuan.modules.message;

public enum SMSType {
	
	
	//手机注册，每天一个账号短信发送次数上限
	REGISTER_LIMIT_NUM("r", 3),
	//忘记密码，每天一个账号短信发送次数上限
	PASSWORD_LIMIT_NUM("p", 3),
	//系统短信，每天一个账号短信发送次数上限
	SYSTEM_LIMIT_NUM("s", 3);
	
	
	private String name;
	
	private int num;

	private SMSType(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	

}
