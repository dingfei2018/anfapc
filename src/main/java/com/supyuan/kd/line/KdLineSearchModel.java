package com.supyuan.kd.line;

import com.supyuan.jfinal.base.BindModel;


/**
 * 专线查询参数
 *
 * @author yuwen
 **/
public class KdLineSearchModel extends BindModel {
    /**
     * 出发网点
     */
    private String netWorkId;
   
    /**
     * 到达网点
     */
    private String arriveNetWorkId;
    
  
    /**
     * 出发地
     */
    private String fromCode;

    /**
     * 到达地
     */
    private String toCode;

	public String getNetWorkId() {
		return netWorkId;
	}

	public void setNetWorkId(String netWorkId) {
		this.netWorkId = netWorkId;
	}

	public String getArriveNetWorkId() {
		return arriveNetWorkId;
	}

	public void setArriveNetWorkId(String arriveNetWorkId) {
		this.arriveNetWorkId = arriveNetWorkId;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}


}
