package com.supyuan.component.base;

public enum ResultType {
	
	SUCCESS(200, "请求成功"),
	FAIL(-1, "请求失败"),
	INVIDATE(-2, "请求已失效"),
	ILLEGAL(0, "您长时间没有操作,页面已失效了"),
	PARAM_ERROR(-3, "请求参数错误"),
	ORDEROFF(1000, "该订单所在的专线已下线，请选择其他专线"),
	USERNAME_NOT_NULL(1001, "用户名不能为空"),
	PASSWORD_NOT_NULL(1002, "密码不能为空"),
	USERNAME_NOT_EXIST(1003, "账号不存在，请重新输入"),
	PASSWORD_IS_WRONG(1004, "密码错误"),
	PHONE_NOT_NULL(1005, "手机号码不能为空"),
	PHONE_IS_WRONG(1006, "手机号码不合法"),
	PHONE_IS_EXIST(1007, "手机号码已存在"),
	PASSWORD_NOT_SAME(1008, "两次密码输入不一样"),
	PHONE_CODE_WRONG(1009, "手机验证码错误"),
	PHONE_LIMIT_ERROR(1010, "您今天发送短信过多，明天再来吧"),
	PHONE_NOT_EXIST(1011, "手机号码不存在"),
	OLDPASSWORD_IS_WRONG(1012, "旧密码错误"),
	IMAGES_SAVE_ERROR(2001, "保存失败,未上传图片"),
	
	//配载单
	SHIP_IS_SIGN_ERROR(3001, "配载单包含已签收的运单，不能删除"),
	SHIP_IS_ARRIVAL_ERROR(3002, "已到达的配载单，不能删除"),
	SHIP_IS_DELETE_ERROR(3003, "配载的运单不能全部删减"),
	
	//费用
	FEE_IS_SETTLE_ERROR(4001, "费用已结算不能修改"),
	
	NOTLOGIN(-9, "用户未登录"),
	
	INSERT_SUCCESS(1,"新增成功"),
	INSERT_FAIL(-1,"新增失败"),
	DELETE_SUCCESS(1,"删除成功"),
	DELETE_FAIL(-1,"删除失败"),
	UPDATE_SUCCESS(1,"更新成功"),
	UPDATE_FAIL(-1,"更新失败")
	
	;
	
	
	
	int type;
	
	String msg;

	private ResultType(int type, String msg) {
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
