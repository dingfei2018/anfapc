package com.supyuan.modules.result;

import java.io.Serializable;


public class WResult  implements Serializable {

	
	private static final long serialVersionUID = 1L;

    private int status;

	private boolean istrue;
	

	private String msg;
	
    private Object entity;

    public WResult() {
    }
   

    @Override
    public String toString() {
        return "response code:" +
                "status=" + getStatus() +
                ", msg='" + getMsg() + "'";
    }




	public boolean isIstrue() {
		return istrue;
	}


	public void setIstrue(boolean istrue) {
		this.istrue = istrue;
	}


	public String getMsg() {
		return msg;
		
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getEntity() {
		return entity;
	}


	public void setEntity(Object entity) {
		this.entity = entity;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
}