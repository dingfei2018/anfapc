package com.supyuan.component.base;

public enum MResultType {
	
	SUCCESS(200, "请求成功"),
	FAIL(-1, "请求失败"),
	PARAM_ERROR(-3, "请求参数错误"),
	NOTLOGIN(-9, "用户未登录"),
	
	USERNAME_NOT_EXIST(1000, "账号不存在，请重新输入"),
	PASSWORD_IS_WRONG(1001, "用户名或密码错误"),
	USERKEY_IS_INVALID(1002, "登录无效,请重新登录"),
	
	
	INSERT_SUCCESS(1,"新增成功"),
	INSERT_FAIL(-1,"新增失败"),
	DELETE_SUCCESS(1,"删除成功"),
	DELETE_FAIL(-1,"删除失败"),
	UPDATE_SUCCESS(1,"更新成功"),
	UPDATE_FAIL(-1,"更新失败")
	
	;
	
	
	
	int type;
	
	String msg;

	private MResultType(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
