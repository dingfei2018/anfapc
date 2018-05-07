package com.supyuan.kd.common;


/**
 * 运单状态
 * @author liangxp
 *
 * Date:2017年12月12日下午1:59:26 
 * 
 * @email liangxp@anfawuliu.com
 */
public enum ShipStatus {
	
	STORAGE(1, "已入库"),
	DUANBOING(2, "短驳中"),
	DUANBOED(3, "短驳到达"),
	SENDING(4, "已发车"),
	ARRIVIED(5, "已到达"),
	RTRANSFER(6, "收货中转中"),
	ATRANSFER(7, "到货中转中"),
	DELIVERY(8, "送货中"),
	SIGN(9, "已签收");
	
	public int type;
	
	private String name;
	
	private ShipStatus(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
