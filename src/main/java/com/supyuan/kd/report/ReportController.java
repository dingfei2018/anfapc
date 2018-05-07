package com.supyuan.kd.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.stat.ast.Return;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.DateUtils;
import com.supyuan.util.excel.poi.POIUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 报表相关
 * @author liangxp
 *
 * Date:2017年12月20日下午4:27:06 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/report")
public class ReportController extends BaseProjectController {

	private static final String path = "/pages/kd/report/";
	
/*	public void index() {
		renderJsp(path + "demo.jsp");
	}
	
	
	public void preProfit() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		List<String> monthList = DateUtils.getMonthList(start, end);
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		setAttr("startTime", start);
		setAttr("endTime", end);
		renderJsp(path + "profitall.jsp");
	}
	
	
	
	*//**
	 * 毛利汇总
	 * @author liangxp
	 *//*
	public void preProfitSearch() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int networkId = getParaToInt("networkId");
		List<ProfitCollectModel> profits = new ReportSvc().profitCollect(startTime, endTime, networkId, user);
		renderJson(profits);
	}
	
	*//**
	 * 导出excel
	 * @author liangxp
	 *//*
	public void exportPreProfit() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		int networkId = getParaToInt("networkId");
		List<ProfitCollectModel> profits = new ReportSvc().profitCollect(startTime, endTime, networkId, user);
		try {
			String filename = new String("毛利汇总表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(profits, "毛利汇总表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	
	*//**
	 * 应收汇总表 
	 * @author chenan
	 *//*
	public void  reSummary(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		List<String> monthList = DateUtils.getMonthList(start, end);
		
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		setAttr("startTime", start);
		setAttr("endTime", end);
		setAttr("networks", netWorkList);
		renderJsp(path + "resummary.jsp");
	}
	
	*//**
	 * 应收汇总表json data
	 * @author chenan
	 *//*
	public void getReSummaryData() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String networkId = getPara("networkId");
		List<ReSummaryModel> data = new ReportSvc().getReSummaryData(startTime, endTime, networkId, user);
		renderJson(data);
	}
	
	*//**
	 * 导出应收汇总 excel报表
	 * @author chenan
	 *//*
	public void exportReSummary() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String networkId = getPara("networkId");
		List<ReSummaryModel> excel = new ReportSvc().getReSummaryData(startTime, endTime, networkId, user);
		try {
			String filename = new String("应收汇总表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excel, "应收汇总表", response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 应付汇总表 
	 * @author chenan
	 *//*
	public void  paySummary(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		
		String start = DateUtils.getCurrBeforeYearMonth();
		String end = DateUtils.getCurrYearMonth();
		List<String> monthList = DateUtils.getMonthList(start, end);
		
		if(monthList.size()>12){
			start = monthList.get(11);
		}
		setAttr("startTime", start);
		setAttr("endTime", end);
		setAttr("networks", netWorkList);
		renderJsp(path + "paySummary.jsp");
	}
	
	*//**
	 * 应付汇总表json data
	 * @author chenan
	 *//*
	public void getPaySummaryData() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String networkId = getPara("networkId");
		List<PaySummaryModel> data = new ReportSvc().getPaySummaryData(startTime, endTime, networkId, user);
		renderJson(data);
	}
	
	*//**
	 * 导出应付汇总 excel报表
	 * @author chenan
	 *//*
	public void exportPaySummary() {
		SessionUser user = getSessionSysUser();
		String startTime = getPara("startTime");
		String endTime = getPara("endTime");
		String networkId = getPara("networkId");
		List<PaySummaryModel> excel = new ReportSvc().getPaySummaryData(startTime, endTime, networkId, user);
		try {
			String filename = new String("应付汇总表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excel, "应付汇总表", response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 运作明细
	 *//*
	  public void operationList(){
		  SessionUser user = getSessionSysUser();
		  List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		  setAttr("networks", netWorkList);
		  renderJsp(path+"operation.jsp");

	  }

	*//**
	 * 运作明细列表
	 *//*
	public void search(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		Page<KdShip> ships=new ReportSvc().queryShipOperation(paginator,user,model);
		renderJson(ships);
	  }

	*//**
	 * 运作明细excel导出
	 * @return 
	 * @throws IOException 
	 *//*
	  public void exportOperation() throws IOException {
		  SessionUser user = getSessionSysUser();
		  OperationSearchModel model = OperationSearchModel.getBindModel(OperationSearchModel.class, getRequest());

		  Paginator paginator = getPaginator();
		  paginator.setPageSize(100);

		  List<ExcelOperation> excelList = new ArrayList();

		  Page<KdShip> page = new ReportSvc().queryShipOperation(paginator, user, model);

		  List<KdShip> list = page.getList();
		  int i = 1;
		  String payWay;//付款方式
		  for (KdShip kdShip : list) {
			  ExcelOperation excel = new ExcelOperation();
			  excel.setOrderNum(i);
			  excel.setShipSn(kdShip.get("ship_sn"));
			  excel.setNetWorkName(kdShip.get("netWorkName"));
			  excel.setCustomerNumber(kdShip.get("ship_customer_number"));
			  excel.setCreateTime(kdShip.get("create_time")== null ? "" : kdShip.get("create_time").toString());
			  excel.setSenderName(kdShip.get("senderName"));
			  excel.setReceiverName(kdShip.get("receiverName"));
			  excel.setFromAdd(kdShip.get("fromAdd"));
			  excel.setToAdd(kdShip.get("toAdd"));
			  excel.setShipVolume(kdShip.getDouble("ship_volume"));
			  excel.setShipWeight(kdShip.getDouble("ship_weight"));
			  excel.setShipAmount(kdShip.getInt("ship_amount"));
			  excel.setAgencyFund(kdShip.getBigDecimal("ship_agency_fund").doubleValue());
			  if (kdShip.getInt("ship_pay_way") == 1) {
				  payWay = "现付";
			  } else if (kdShip.getInt("ship_pay_way") == 2) {
				  payWay = "提付";
			  } else if (kdShip.getInt("ship_pay_way") == 3) {
				  payWay = "到付";
			  } else if (kdShip.getInt("ship_pay_way") == 4) {
				  payWay = "回单付";
			  } else {
				  payWay = "月付";
			  }
			  excel.setPayWay(payWay);
			  excel.setTotalFee(kdShip.getBigDecimal("ship_total_fee").doubleValue());
			  excel.setFee(kdShip.getDouble("ship_fee"));
			  excel.setPickupFee(kdShip.getDouble("ship_pickup_fee"));
			  excel.setDeliveryFee(kdShip.getDouble("ship_delivery_fee"));
			  excel.setInsuranceFee(kdShip.getDouble("ship_insurance_fee"));
			  excel.setPackageFee(kdShip.getDouble("ship_package_fee"));
			  excel.setAddonFee(kdShip.getDouble("ship_addon_fee"));
			  String thFee = kdShip.get("thFee");
			  if (StringUtils.isNotBlank(thFee)) {
				  String[] str = thFee.split(",");
				  for (int j = 0; j < str.length; j++) {
					  System.out.println(str[j]);
					  if (str[j].endsWith("types1")) {
						  excel.setTruckTH(str[j].replace("types1", ""));
					  } else if (str[j].endsWith("types2")) {
						  excel.setGetTruckDB(str[j].replace("types2", ""));
					  } else if (str[j].endsWith("types3")) {
						  excel.setGetTruckGX(str[j].replace("types3", ""));
					  } else {
						  excel.setGetTruckSH(str[j].replace("types4", ""));
					  }
				  }
			  }
			  excel.setTransferName(kdShip.get("transferName"));
			  excel.setTransferTime(kdShip.get("ship_transfer_time")== null ? "" : kdShip.get("ship_transfer_time").toString());
			  excel.setTransferFee(kdShip.getBigDecimal("ship_transfer_fee").doubleValue());
			  excel.setSignPerson(kdShip.get("sign_person"));
			  excel.setSignTime(kdShip.get("sign_time") == null ? "" : kdShip.get("sign_time").toString());
			  excel.setProfit(kdShip.getDouble("profit"));
			  excel.setRate(kdShip.getDouble("rate"));
			  excelList.add(excel);
			  i++;
		  }
		  try {
			  String filename = new String("运作明细表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			  HttpServletResponse response = getResponse();
			  response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			  POIUtils.generateXlsxExcelStream(excelList, "运作明细表", response.getOutputStream());
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }*/

	/**
	 * 营业额月报表页面
	 * @author huangym
	 */
	public void toTurnoverReportPage(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("netWorkList",netWorkList);
		renderJsp(path + "turnover.jsp");
	}

	/**
	 * 营业额月报表列表
	 * @author huangym
	 */
	public void  turnoverReport(){
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<KdShip> kdShips = new ReportSvc().queryTurnoverReportList(user, model);
		renderJson(kdShips);
	}


	/**
	 * 导出营业额月报表 excel报表
	 * @author huangym
	 */
	public void exportTurnoverReport() {
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<ExcelTurnoverReport> res = new ArrayList<>();
		List<KdShip> kdShips = new ReportSvc().queryTurnoverReportList(user, model);
		int i = 0;
		for (KdShip kdship : kdShips){
			ExcelTurnoverReport exc = new ExcelTurnoverReport();
			exc.setNum(i++);
			exc.setMonth(kdship.get("month"));
			exc.setNetWorkName(kdship.getStr("netName"));
			exc.setTotalAmount(kdship.get("total_amount"));
			exc.setTotalShipNowpayFee(kdship.getDouble("total_ship_nowpay_fee"));
			exc.setTotalShipPickuppayFee(kdship.getDouble("total_ship_pickuppay_fee"));
			exc.setTotalShipReceiptpayFee(kdship.getDouble("total_ship_receiptpay_fee"));
			exc.setTotalShipMonthpayFee(kdship.getDouble("total_ship_monthpay_fee"));
			exc.setTotalShipArrearspayFee(kdship.getDouble("total_ship_arrearspay_fee"));
			exc.setTotalShipGoodspayFee(kdship.getDouble("total_ship_goodspay_fee"));
			exc.setTotalPlusFee(kdship.getDouble("total_plus_fee"));
			exc.setTotalMinusFee(kdship.getDouble("total_minus_fee"));
			exc.setTotalShipRebateFee(kdship.getBigDecimal("total_ship_rebate_fee").doubleValue());
			res.add(exc);

		}
		try {
			String filename = new String("营业额月报表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "营业额月报表", response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 应收应付汇总表
	 */
	public void  rePayIndex(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String nowMonth=format.format(new Date());

		setAttr("netWorkList",netWorkList);
		setAttr("nowMonth",nowMonth);
		renderJsp(path+"repay.jsp");
	}



	/**
	 * 发车毛利表页面
	 * @author huangym
	 */
	public void toLoadGrossProfitPage(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("netWorkList",netWorkList);
		renderJsp(path + "start.jsp");
	}


	/**
	 * 发车车次毛利表列表
	 * @author huangym
	 */
	public void  loadGrossProfit(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		Page<KdTrunkLoad> loads=new ReportSvc().queryLoadGrossProfit(paginator,user,model,false);
		renderJson(loads);
	}



	/**
	 * 导出发车车次毛利表列表 excel报表
	 * @author huangym
	 */
	public void exportLoadGrossProfit() {
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<ExcelLoadGrossProfit> res = new ArrayList<>();
		Page<KdTrunkLoad> loads=new ReportSvc().queryLoadGrossProfit(null,user,model,true);
		int i = 0;
		for (KdTrunkLoad load : loads.getList()){
			ExcelLoadGrossProfit exc = new ExcelLoadGrossProfit();
			//营业额合计
			double totalTurnover = load.getDouble("total_ship_nowpay_fee")+load.getDouble("total_ship_pickuppay_fee")+load.getDouble("total_ship_receiptpay_fee")
					+load.getDouble("total_ship_monthpay_fee")+load.getDouble("total_ship_arrearspay_fee")+load.getDouble("total_ship_goodspay_fee")
					+load.getDouble("total_plus_fee")-load.getDouble("total_minus_fee")-load.getBigDecimal("total_ship_rebate_fee").doubleValue();
			//配载费合计
			double totalLoadFee = load.getDouble("total_load_nowtrans_fee")+load.getDouble("total_load_nowoil_fee")+load.getDouble("total_load_backtrans_fee")
					+load.getDouble("total_load_attrans_fee")+load.getDouble("total_load_allsafe_fee")+load.getDouble("total_load_start_fee")
					+load.getDouble("total_load_other_fee");
			exc.setNum(i++);
			exc.setLoadSn(load.get("load_sn"));
			exc.setNetName(load.getStr("netName"));
			exc.setNextNetName(load.get("nextNetName"));
			exc.setLoadDepartTime(new SimpleDateFormat("yyyy-MM-dd").format(load.get("load_depart_time")));
			exc.setTruckIdNumber(load.get("truck_id_number"));
			exc.setTruckDriverName(load.get("truck_driver_name"));
			exc.setTruckDriverMobile(load.get("truck_driver_mobile"));
			exc.setTotalTurnover(totalTurnover);
			exc.setTotalLoadFee(totalLoadFee);
			exc.setGrossProfit(totalTurnover-totalLoadFee);
			exc.setGrossProfitRate(String.format("%.2f",(totalTurnover-totalLoadFee)/totalTurnover*100)+"%");
			exc.setFromAdd(load.get("fromAdd"));
			exc.setToAdd(load.get("toAdd"));
			exc.setLoadCount(load.get("load_count"));
			exc.setLoadAmount(load.get("load_amount"));
			exc.setLoadVolume(load.get("load_volume"));
			exc.setLoadWeight(load.get("load_weight"));
			res.add(exc);
		}
		try {
			String filename = new String("发车车次毛利表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "发车车次毛利表", response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getRePayJson(){
		SessionUser user=getSessionUser();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String nowMonth=format.format(new Date());


		String startTime=getPara("startTime")==null?nowMonth:getPara("startTime");
		String endTime=getPara("endTime")==null?nowMonth:getPara("endTime");
		String networkId=getPara("networkId");

		List<RePayModel> dateList=new ReportSvc().getRePayModelData(startTime,endTime,networkId,user);

		renderJson(dateList);
	}

	
	


	/**
	 * 成本月报表
	 * @author yuwen
	 */
	public void costReportList(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		renderJsp(path+"cost.jsp");

	}


	/**
	 * 成本月报表列表
	 * @author yuwen
	 */
	public void  costReport(){
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<KdShip> kdShips = new ReportSvc().queryCostReportList(user, model);
		renderJson(kdShips);
	}

    /**
	 * 导出成本月报 excel报表
	 * @author yuwen
	 */
	public void exportCostReport() {
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<KdShip> kdShips = new ReportSvc().queryCostReportList(user, model);
		List<CostReportModel> res = new ArrayList<>();
		int i = 1;
		for (KdShip ship : kdShips){
			CostReportModel exc = new CostReportModel();
			exc.setNum(i++);
			exc.setTime(ship.get("ctime").toString());
			exc.setNetName(ship.get("netName"));
			exc.setCost(ship.getDouble("totalFee"));
			exc.setGxcount(ship.getInt("gxcount"));
			exc.setGxfee(ship.getDouble("gxfee"));
			exc.setThcount(ship.getInt("thcount"));
			exc.setThfee(ship.getDouble("thfee"));
			exc.setDbcount(ship.getInt("dbcount"));
			exc.setDbfee(ship.getDouble("dbfee"));
			exc.setShcount(ship.getInt("shcount"));
			exc.setShfee(ship.getDouble("shfee"));
			exc.setZzcount(ship.getInt("zzcount"));
			exc.setZzfee(ship.getInt("zzfee"));
			res.add(exc);
		}
		try {
			String filename = new String("成本月报表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "成本月报表", response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 利润月报表
	 * @author yuwen
	 */
	public void profitReportList(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		renderJsp(path+"profit.jsp");

	}


	/**
	 * 利润月报表列表
	 * @author yuwen
	 */
	public void  profitReport(){
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<Record> kdShips = new ReportSvc().getProfitReportDate(model.getStartTime(),model.getEndTime(),model.getNetWorkId(),user);
		renderJson(kdShips);
	}

	/**
	 * 导出利润月报 excel报表
	 * @author yuwen
	 */
	public void exportProfitReport() {
		SessionUser user = getSessionSysUser();
		OperationSearchModel model=OperationSearchModel.getBindModel(OperationSearchModel.class,getRequest());
		List<Record> kdShips = new ReportSvc().getProfitReportDate(model.getStartTime(),model.getEndTime(),model.getNetWorkId(),user);
		List<ProfitReportModel> res = new ArrayList<>();
		int i = 1;
		for (Record record :kdShips
			 ) {

			ProfitReportModel exc = new ProfitReportModel();
			exc.setNum(i++);
			exc.setTime(record.get("ctime").toString());
			exc.setNetName(record.get("netName"));
			double totalProfitFee =record.get("totalProfitFee");
			double totalCostFee =record.get("totalCostFee");
			exc.setTurnover(totalProfitFee);
			exc.setCost(totalCostFee);
			exc.setGrossProfit((totalProfitFee-totalCostFee));
			exc.setGrossProfitRate(String.format("%.2f",(totalProfitFee-totalCostFee)/totalProfitFee*100)+"%");
			exc.setAmount(record.get("count1"));
			exc.setShip_nowpay_fee(record.getDouble("ship_nowpay_fee"));
			exc.setShip_pickuppay_fee(record.getDouble("ship_pickuppay_fee"));
			exc.setShip_receiptpay_fee(record.getDouble("ship_receiptpay_fee"));
			exc.setShip_monthpay_fee(record.getDouble("ship_monthpay_fee"));
			exc.setShip_arrearspay_fee(record.getDouble("ship_arrearspay_fee"));
			exc.setShip_goodspay_fee(record.getDouble("ship_goodspay_fee"));
			exc.setPlus_fee(record.getDouble("plus_fee"));
			exc.setMinus_fee(record.getDouble("minus_fee"));
			exc.setThfee(record.getDouble("thfee"));
			exc.setDbfee(record.getDouble("dbfee"));
			exc.setShip_rebate_fee(record.getDouble("shfee"));
			exc.setZzfee(record.getDouble("zzfee"));
			exc.setLoad_nowtrans_fee(record.getDouble("load_nowtrans_fee"));
			exc.setLoad_nowoil_fee(record.getDouble("load_nowoil_fee"));
			exc.setLoad_backtrans_fee(record.getDouble("load_backtrans_fee"));
			exc.setLoad_attrans_fee(record.getDouble("load_attrans_fee"));
			exc.setLoad_allsafe_fee(record.getDouble("load_allsafe_fee"));
			exc.setLoad_start_fee(record.getDouble("load_start_fee"));
			exc.setLoad_other_fee(record.getDouble("load_other_fee"));
			exc.setLoad_atunload_fee(record.getDouble("load_atunload_fee"));
			exc.setLoad_atother_fee(record.getDouble("load_atother_fee"));
			res.add(exc);
		}
		try {
			String filename = new String("利润月报表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(res, "利润月报表", response.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
