package com.supyuan.kd.finance.account;





import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.util.excel.poi.POIUtils;

/**
 * 对账管理Controller
 * @author chenan
 * Date:2017年12月19日 下午3:41:08 
 */
@ControllerBind(controllerKey = "/kd/finance/account")
public class AccountController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/finance/account/";
	//提付对账应收index
	public void index() {
		SessionUser user =getSessionSysUser();
		
		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);
		
		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "index.jsp");
	}

	//提付对账结算页面
	public void goRePickupJS(){
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "rethjs.jsp");
	}

	//操作对账应收index
	public void reDetailedIndex() {
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "redetailed.jsp");
	}

	//操作对账结算页面
	public void goReDetailedJS(){
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "reczdzjs.jsp");
	}


	/**
	 * 提付对账应收列表json接口
	 */
	public void getRePickuppayAccountData(){
		String flag=getPara("flag")==null?"":getPara("flag");
		SessionUser user =getSessionSysUser();
		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());
		if(flag.equals("all")){
			List<Record> list=(List)new AccountSvc().getRePickuppayAccountList(user,search,true);
			renderJson(list);
		}else{
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<Record> page=(Page)new AccountSvc().getRePickuppayAccountList(user,search,false,paginator);
			renderJson(page);
		}
	}

	/**
	 * 操作对账应收列表json接口
	 */
	public void getReDetailedAccountList(){
		String flag=getPara("flag")==null?"":getPara("flag");
		SessionUser user =getSessionSysUser();
		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		if(flag.equals("all")){
			List<Record> list=(List)new AccountSvc().getReDetailedAccountList(user,search,true);
			renderJson(list);
		}else{
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<Record> page=(Page)new AccountSvc().getReDetailedAccountList(user,search,false,paginator);
			renderJson(page);
		}

	}

	//提付对账应付index
	public void payIndex() {
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "payaccount.jsp");
	}

	//提付对账结应付算页面
	public void goPayPickupJS(){
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "paythjs.jsp");
	}

	//操作对账应付index
	public void payDetailedIndex() {
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "paydetails.jsp");
	}

	//操作对账应付结算页面
	public void goPayDetailedJS(){
		SessionUser user =getSessionSysUser();

		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "payczdzjs.jsp");
	}


	/**
	 * 提付对账应付列表json接口
	 */
	public void getPayPickuppayAccountData(){
		String flag=getPara("flag")==null?"":getPara("flag");
		SessionUser user =getSessionSysUser();
		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());
		if(flag.equals("all")){
			List<Record> list=(List)new AccountSvc().getPayPickuppayAccountList(user,search,true);
			renderJson(list);
		}else{
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<Record> page=(Page)new AccountSvc().getPayPickuppayAccountList(user,search,false,paginator);
			renderJson(page);
		}
	}

	/**
	 * 操作对账应付列表json接口
	 */
	public void getPayDetailedAccountList(){
		String flag=getPara("flag")==null?"":getPara("flag");
		SessionUser user =getSessionSysUser();
		AccountSearchModel search=AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		if(flag.equals("all")){
			List<Record> list=(List)new AccountSvc().getPayDetailedAccountList(user,search,true);
			renderJson(list);
		}else{
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<Record> page=(Page)new AccountSvc().getPayDetailedAccountList(user,search,false,paginator);
			renderJson(page);
		}

	}

	/**
	 * 应收提付结算
	 */
	public void confirmRePickuppay(){
		SessionUser user=getSessionSysUser();
		String payType=getPara("payType");
		String flowNo=getPara("flowNo");
		String voucherNo=getPara("voucherNo");
		String remark=getPara("remark");
		String shipIds=getPara("shipIds");

		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			boolean flag= new AccountSvc().confirmRePickuppay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}

	/**
	 * 应收操作结算
	 */
	public void confirmReDetailed(){
		SessionUser user=getSessionSysUser();
		String payType=getPara("payType");
		String flowNo=getPara("flowNo");
		String voucherNo=getPara("voucherNo");
		String remark=getPara("remark");
		String feeIds=getPara("feeIds");
		String shipIds=getPara("shipIds");


		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			boolean flag= new AccountSvc().confirmReDetailed(shipIds,feeIds,payType,flowNo,voucherNo,remark,getNow(),user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}

	/**
	 * 应付提付结算
	 */
	public void confirmPayPickuppay(){
		SessionUser user=getSessionSysUser();
		String payType=getPara("payType");
		String flowNo=getPara("flowNo");
		String voucherNo=getPara("voucherNo");
		String remark=getPara("remark");
		String shipIds=getPara("shipIds");

		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			boolean flag= new AccountSvc().confirmPayPickuppay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}

	/**
	 * 应付操作结算
	 */
	public void confirmPayDetailed(){
		SessionUser user=getSessionSysUser();
		String payType=getPara("payType");
		String flowNo=getPara("flowNo");
		String voucherNo=getPara("voucherNo");
		String remark=getPara("remark");
		String feeIds=getPara("feeIds");
		String shipIds=getPara("shipIds");


		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			boolean flag= new AccountSvc().confirmPayDetailed(shipIds,feeIds,payType,flowNo,voucherNo,remark,getNow(),user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}


	
	/**
	 * 下载提付应收excel列表
	 */
	public void downExcelRePickup(){
		SessionUser  sessionUser = getSessionUser();
		AccountSearchModel search = AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());

		
		List<ExcelRePickup> excelList=new ArrayList<>();
		
		List<Record> list=(List)new AccountSvc().getRePickuppayAccountList(sessionUser,search,false);
		int i=1;
		for (Record record : list) {
			ExcelRePickup excel=new ExcelRePickup();
			excel.setOrderNum(i);
			excel.setShipSn(record.get("ship_sn"));
			excel.setReNetName(record.get("netWorkName"));
			excel.setPayNetName(record.get("toNetWorkName"));
			excel.setShipPickuppayFee(record.getDouble("ship_pickuppay_fee"));
			excel.setState(record.getBoolean("fee_in_fill"));
			excel.setShipState(record.get("ship_status").toString());
			excel.setGoodsSn(record.get("goods_sn"));
			excel.setTime(record.get("create_time").toString());
			excel.setFromAdd(record.get("fromAdd"));
			excel.setToAdd(record.get("toAdd"));
			excel.setSenderName(record.get("senderName"));
			excel.setReceiverName(record.get("receiverName"));
			excel.setGoodsName(record.get("productName"));
			excel.setShipVolume(record.getDouble("ship_volume"));
			excel.setShipWeight(record.getDouble("ship_weight"));
			excelList.add(excel);
			i++;
		}
		
		try {
			String filename = new String("提付对账应收列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excelList, "提付对账应收列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载操作对账应收excel列表
	 */
	public void downExcelReDetailed(){
		SessionUser  sessionUser = getSessionUser();
		AccountSearchModel search = AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());


		List<ExcelReDetailed> excelList=new ArrayList<>();

		List<Record> list=(List)new AccountSvc().getReDetailedAccountList(sessionUser,search,false);
		int i=1;
		for (Record record : list) {
			ExcelReDetailed excel=new ExcelReDetailed();
			excel.setOrderNum(i);
			excel.setShipSn(record.get("ship_sn"));
			excel.setReNetName(record.get("netWorkName"));
			excel.setPayNetName(record.get("toNetWorkName"));
			excel.setTotalFee(record.getDouble("totalFee"));
			excel.setState(record.get("fee_in_fill").toString());
			excel.setShipState(record.get("ship_status").toString());
			excel.setThFee(record.getDouble("thFee"));
			excel.setShFee(record.getDouble("shFee"));
			excel.setDbFee(record.getDouble("dbFee"));
			excel.setZzFee(record.getDouble("zzFee"));
			excel.setXfysFee(record.getDouble("xfysFee"));
			excel.setXfykFee(record.getDouble("xfykFee"));
			excel.setHfysFee(record.getDouble("hfysFee"));
			excel.setZcbxFee(record.getDouble("zcbxFee"));
			excel.setFzzcFee(record.getDouble("fzzcFee"));
			excel.setFzqtFee(record.getDouble("fzqtFee"));
			excel.setDfysFee(record.getDouble("dfysFee"));
			excel.setDzxcFee(record.getDouble("dzxcFee"));
			excel.setDzqtFee(record.getDouble("dzqtFee"));

			excel.setGoodsSn(record.get("goods_sn"));
			excel.setTime(record.get("create_time").toString());
			excel.setFromAdd(record.get("fromAdd"));
			excel.setToAdd(record.get("toAdd"));
			excel.setSenderName(record.get("senderName"));
			excel.setReceiverName(record.get("receiverName"));
			excel.setGoodsName(record.get("productName"));
			excel.setShipVolume(record.getDouble("ship_volume"));
			excel.setShipWeight(record.getDouble("ship_weight"));
			excelList.add(excel);
			i++;
		}

		try {
			String filename = new String("操作对账应收列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excelList, "操作对账应收列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载提付应付excel列表
	 */
	public void downExcelPayPickup(){
		SessionUser  sessionUser = getSessionUser();
		AccountSearchModel search = AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());


		List<ExcelPayPickup> excelList=new ArrayList<>();

		List<Record> list=(List)new AccountSvc().getPayPickuppayAccountList(sessionUser,search,false);
		int i=1;
		for (Record record : list) {
			ExcelPayPickup excel=new ExcelPayPickup();
			excel.setOrderNum(i);
			excel.setShipSn(record.get("ship_sn"));
			excel.setReNetName(record.get("netWorkName"));
			excel.setPayNetName(record.get("toNetWorkName"));
			excel.setShipPickuppayFee(record.getDouble("ship_pickuppay_fee"));
			excel.setState(record.getBoolean("fee_in_fill"));
			excel.setShipState(record.get("ship_status").toString());
			excel.setGoodsSn(record.get("goods_sn"));
			excel.setTime(record.get("create_time").toString());
			excel.setFromAdd(record.get("fromAdd"));
			excel.setToAdd(record.get("toAdd"));
			excel.setSenderName(record.get("senderName"));
			excel.setReceiverName(record.get("receiverName"));
			excel.setGoodsName(record.get("productName"));
			excel.setShipVolume(record.getDouble("ship_volume"));
			excel.setShipWeight(record.getDouble("ship_weight"));
			excelList.add(excel);
			i++;
		}

		try {
			String filename = new String("提付对账应付列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excelList, "提付对账应付列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载操作对账应付excel列表
	 */
	public void downExcelPayDetailed(){
		SessionUser  sessionUser = getSessionUser();
		AccountSearchModel search = AccountSearchModel.getBindModel(AccountSearchModel.class, getRequest());


		List<ExcelPayDetailed> excelList=new ArrayList<>();

		List<Record> list=(List)new AccountSvc().getReDetailedAccountList(sessionUser,search,false);
		int i=1;
		for (Record record : list) {
			ExcelPayDetailed excel=new ExcelPayDetailed();
			excel.setOrderNum(i);
			excel.setShipSn(record.get("ship_sn"));
			excel.setReNetName(record.get("netWorkName"));
			excel.setPayNetName(record.get("toNetWorkName"));
			excel.setTotalFee(record.getDouble("totalFee"));
			excel.setState(record.get("fee_in_fill").toString());
			excel.setShipState(record.get("ship_status").toString());
			excel.setThFee(record.getDouble("thFee"));
			excel.setShFee(record.getDouble("shFee"));
			excel.setDbFee(record.getDouble("dbFee"));
			excel.setZzFee(record.getDouble("zzFee"));
			excel.setXfysFee(record.getDouble("xfysFee"));
			excel.setXfykFee(record.getDouble("xfykFee"));
			excel.setHfysFee(record.getDouble("hfysFee"));
			excel.setZcbxFee(record.getDouble("zcbxFee"));
			excel.setFzzcFee(record.getDouble("fzzcFee"));
			excel.setFzqtFee(record.getDouble("fzqtFee"));
			excel.setDfysFee(record.getDouble("dfysFee"));
			excel.setDzxcFee(record.getDouble("dzxcFee"));
			excel.setDzqtFee(record.getDouble("dzqtFee"));

			excel.setGoodsSn(record.get("goods_sn"));
			excel.setTime(record.get("create_time").toString());
			excel.setFromAdd(record.get("fromAdd"));
			excel.setToAdd(record.get("toAdd"));
			excel.setSenderName(record.get("senderName"));
			excel.setReceiverName(record.get("receiverName"));
			excel.setGoodsName(record.get("productName"));
			excel.setShipVolume(record.getDouble("ship_volume"));
			excel.setShipWeight(record.getDouble("ship_weight"));
			excelList.add(excel);
			i++;
		}

		try {
			String filename = new String("操作对账应付列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excelList, "操作对账应付列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	



}
