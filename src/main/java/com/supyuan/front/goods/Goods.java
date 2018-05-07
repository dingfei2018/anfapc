package com.supyuan.front.goods;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 货物对应表
 */
@ModelBind(table = "shipping_goods", key = "id")
public class Goods extends BaseProjectModel<Goods> {
	private static final long serialVersionUID = 1L;
	public static final Goods dao = new Goods();

	
	
	public String fromaddr;
	public String toaddr;

	public Integer getOrdergoods() {
		return getInt("id") == null ? -1 : getInt("id");
	}



	public String getFromaddr() {
		return fromaddr;
	}



	public void setFromaddr(String fromaddr) {
		this.fromaddr = fromaddr;
	}



	public String getToaddr() {
		return toaddr;
	}



	public void setToaddr(String toaddr) {
		this.toaddr = toaddr;
	}
	
	
	
}
