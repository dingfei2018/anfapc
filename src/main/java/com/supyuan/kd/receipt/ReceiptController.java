package com.supyuan.kd.receipt;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.waybill.KdShip;

import java.util.List;

/**
 * 我的回单
 * @author dingfei
 *
 * @date 2018年1月17日 上午9:49:52
 */
@ControllerBind(controllerKey = "/kd/receipt")
public class ReceiptController extends BaseProjectController {
    private static final String path = "/pages/kd/receipt/";
	public void index(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("allnetWorks", allnetWorks);
		renderJsp(path+"receipt.jsp");
	}

	/**
	 * 我的回单列表
	 */
	public void search(){
	 	SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		ReceiptListSearchModel model = ReceiptListSearchModel.getBindModel(ReceiptListSearchModel.class, getRequest());
		Page<KdShip> ships = new ReceiptSvc().queryMyReceiptList(paginator,user,model);
		renderJson(ships);

	}

	/**
	 * 回单接收
	 */
	public  void  receiveReceipt(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			String shipIds = getPara("shipIds");
			String loadworkId=getPara("loadworkId");
			boolean res = new ReceiptSvc().receiveReceipt(shipIds,loadworkId,user);
			if(res){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);

	}


	/**
	 * 回单发放
	 */
	public  void  grantReceipt(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			//运单id
			String shipIds = getPara("shipIds");
			//代收网点
			String loadworkId=getPara("loadworkId");
			//发放邮寄单号
			String sendNo=getPara("sendNo");
			boolean res = new ReceiptSvc().grantReceipt(shipIds,loadworkId,sendNo,user);
			if(res){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);

	}



}
