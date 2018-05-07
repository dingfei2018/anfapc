package com.supyuan.component.base;

import java.util.HashMap;
import java.util.Map;

/**
 * ajax返回结果实体
 * 
 * @author liangxp
 *
 * Date:2017年10月11日上午9:50:33 
 * 
 * @email liangxp@anfawuliu.com
 */
public class MBaseResult {
	
	private int type;
	
	private String msg;
	
	private Map<String,Object> data = new HashMap<String, Object>();

	public MBaseResult() {
		this.type = MResultType.SUCCESS.getType();
		this.msg = MResultType.SUCCESS.getMsg();
	}
	
	public MBaseResult(MResultType resultType) {
		this.type = resultType.getType();
		this.msg = resultType.getMsg();
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

	public boolean isSuccess() {
		return type==200;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void putData(String key, Object value) {
		this.data.put(key, value);
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
	public void setResultType(MResultType resultType){
		this.type = resultType.getType();
		this.msg = resultType.getMsg();
	}

}
