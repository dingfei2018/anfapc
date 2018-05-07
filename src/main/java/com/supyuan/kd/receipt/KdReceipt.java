package com.supyuan.kd.receipt;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;
/**
 * 回单
 *
 * @author dingfei
 * @create 2018-01-17 13:56
 **/

@ModelBind(table = "kd_receipt", key="ship_id")
public class KdReceipt extends BaseProjectModel<KdReceipt> {
    private static final long serialVersionUID = 1L;
    public static final KdReceipt dao = new KdReceipt();
}
