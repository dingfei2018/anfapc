package com.supyuan.kd.bankcard;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 银行账号model
 * @author chenan
 * Date:2017年11月14日上午10:30:08 
 */
@ModelBind(table = "bank_card", key = "bank_card_id")
public class BankCard extends KdBaseModel<BankCard> {
	private static final long serialVersionUID = 1L;
	public static final BankCard dao = new BankCard();


}
