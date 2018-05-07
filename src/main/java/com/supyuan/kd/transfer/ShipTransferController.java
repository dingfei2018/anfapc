package com.supyuan.kd.transfer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.excel.poi.POIUtils;
/**
 * 中转运单controller
 * @author chenan
 * Date:2017年12月14日下午17:30:08 
 */
@ControllerBind(controllerKey = "/kd/transfer")
public class ShipTransferController extends BaseProjectController {
	private static final String path = "/pages/kd/transfer/";

	public void index() {
		SessionUser user=getSessionSysUser();
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		BaseResult baseResult=getSessionAttr("result");
		removeSessionAttr("result");
		setAttr("networks", networks);
		setAttr("result", baseResult);
		
		renderJsp(path + "index.jsp");
	}
	
/**
 *  已转运单分页列表	
 */
	public void transShipMent() {
		SessionUser user=getSessionSysUser();
		
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		
		BaseResult result=getSessionAttr("result");
		removeSessionAttr("result");
		
		setAttr("networks", networks);
		setAttr("result",result);
		
		renderJsp(path + "transshipment.jsp");
	}
	
	public void getTransShipMentData(){
		
		SessionUser user=getSessionSysUser();
		
		TransferMentSearchModel search=TransferMentSearchModel.getBindModel(TransferMentSearchModel.class, getRequest());
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		
		Page<ShipTransfer> page=new ShipTransferSvc().getShipTransferList(paginator, search, user);
		
		renderJson(page);
		
	}
	
	
	public void goUpdate(){
		String shipId=getPara("shipId");
		String flag=getPara("flag")==null?"":getPara("flag");
		ShipTransfer shipTransfer = new ShipTransferSvc().getTransferByshipId(shipId);
		setAttr("shipTransfer",shipTransfer);
		
		if(flag.equals("payUpdate")){
		renderJsp(path + "update-paytran.jsp");	
		}else{
			renderJsp(path + "update.jsp");
		}
			
	}
	
	public void updateTransfer(){
		BaseResult baseResult=new BaseResult(ResultType.UPDATE_FAIL);
		ShipTransfer shipTransfer=getModel(ShipTransfer.class,true);
		try {
		boolean flag=new ShipTransferSvc().update(shipTransfer);;
			if(flag){
				baseResult.setResultType(ResultType.UPDATE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setSessionAttr("result", baseResult);
		redirect("/kd/transfer/transShipMent");
	}
	
	public void delTransfer(){
		SessionUser user=getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.DELETE_FAIL);
		String shipId=getPara("shipId");
		String transferNetworkId=getPara("transferNetworkId");
		try {
		boolean flag=new ShipTransferSvc().delete(shipId,transferNetworkId,user);
			if(flag){
				baseResult.setResultType(ResultType.DELETE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
/*
	 old-获取运单状态为未签收且未中转的运单列表

	public void getWayBill(){
		SessionUser sessionUser = getSessionUser();
		
		
		TransferSearchModel transferSearchModel=TransferSearchModel.getBindModel(TransferSearchModel.class, getRequest());
	
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<KdShip> page=new KdShipSvc().getNoSignKdShipPage(paginator, transferSearchModel ,sessionUser);
		renderJson(page);
	}*/


	/*
	 *获取获取运单状态为未签收且未中转的运单列表
	 */

	public void getWayBill(){
		SessionUser sessionUser = getSessionUser();


		TransferSearchModel transferSearchModel=TransferSearchModel.getBindModel(TransferSearchModel.class, getRequest());

		List<KdShip> list=new KdShipSvc().getNoSignKdShip(transferSearchModel ,sessionUser);
		renderJson(list);
	}

	public void save(){
		SessionUser sessionUser = getSessionUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		String time=getPara("time");
		List<ShipTransfer> shipTransfers =getModels(ShipTransfer.class,"shipTransfer");
		Customer customer=getModel(Customer.class,true);
		customer.set("customer_type", 3);
		customer.set("company_id", sessionUser.getCompanyId());
		customer.set("network_id", 1);
		customer.set("create_time", getNow());
		
		try {
			int id=shipTransfers.get(0).get("ship_id");
			int netWork=new KdShip().findFirst("SELECT load_network_id from kd_ship where ship_id=?",id).get("load_network_id");
			
			boolean flag=new ShipTransferSvc().save(shipTransfers, customer,time,netWork+"", sessionUser);
			
				if(flag){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
			 catch (Exception e) {
			e.printStackTrace();
		}

		renderJson(baseResult);

	}
	
	/**
	 * 批量接收model
	 * @param modelClass
	 * @param modelName
	 * @return
	 */
	public <T> List<T> getModels(Class<T> modelClass, String modelName) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; true; i++) {
			T m = (T) getModel(modelClass, modelName + "[" + i + "]");
			if (m == null || m.toString().equals("{}")) {
				break;
			} else {
			
				    list.add(m);
			}
		}
		return list;
	}
	
	/**
	 * 下载excel列表
	 */
	public void downLoad(){
		SessionUser  sessionUser = getSessionUser();
		TransferMentSearchModel search = TransferMentSearchModel.getBindModel(TransferMentSearchModel.class, getRequest());
		Paginator paginator = getPaginator();
		paginator.setPageSize(9999);
		List<ExcelTransfer> excelList=new ArrayList();
		
		Page<ShipTransfer> page=new ShipTransferSvc().getShipTransferList(paginator, search,sessionUser );
		
		List<ShipTransfer> list=page.getList();
		int i=1;
		for (ShipTransfer transfer : list) {
			ExcelTransfer excel=new ExcelTransfer();
			excel.setOrderNum(i);
			excel.setTransferSn(transfer.get("ship_transfer_sn"));
			excel.setTransferName(transfer.get("transferName"));
			excel.setTransferFee(transfer.get("ship_transfer_fee"));
			excel.setTransferTime(transfer.get("ship_transfer_time").toString());
			excel.setTranNetName(transfer.get("tranNetName"));
			excel.setShipSn(transfer.get("ship_sn"));
			excel.setShipNetName(transfer.get("shipNetName"));
			excel.setFromAdd(transfer.get("fromAdd"));
			excel.setToAdd(transfer.get("toAdd"));
			excel.setSenderName(transfer.get("senderName"));
			excel.setReceiverName(transfer.get("receiverName"));
			excel.setShipVolume(transfer.get("ship_volume").toString());
			excel.setShipWeight(transfer.get("ship_weight").toString());
			excel.setShipAmount(transfer.get("ship_amount").toString());
			excelList.add(excel);
			i++;
		}
		
		try {
			String filename = new String("已转运单列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excelList, "已转运单列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
