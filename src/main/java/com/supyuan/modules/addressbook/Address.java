package com.supyuan.modules.addressbook;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 地此表
 * 
 * @author TFC <2016-6-25>
 */
@ModelBind(table = "address_book" , key = "uuid")
public class Address extends BaseProjectModel<Address> {

	private static final long serialVersionUID = 1L;
	public static final Address dao = new Address();

	public Integer getAddress() {
		return getInt("uuid") == null ? -1 : getInt("uuid");
	}
	
	public String getFullAddress() {
		return "";
	}

}
