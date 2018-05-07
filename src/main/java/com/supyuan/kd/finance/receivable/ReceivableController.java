package com.supyuan.kd.finance.receivable;

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
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务管理(应收管理)
 *
 * @author dingfei
 * @create 2017-12-20 11:30
 **/
@ControllerBind(controllerKey = "/kd/finance/receivable")
public class ReceivableController extends BaseProjectController {
    private static final String path = "/pages/kd/finance/receivable/";

    public void index(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"index.jsp");

    }

    /**
     * 现付index
     */
    public void nowpayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"nowpay.jsp");

    }

    /**
     * 现付结算页面
     */
    public void goNowpayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"nowpay_js.jsp");

    }

    /**
     * 提付index
     */
    public void pickupPayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"pickuppay.jsp");

    }

    /**
     * 提付结算页面
     */
    public void goPickupPayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"pickuppay_js.jsp");

    }

    /**
     * 回单付index
     */
    public void receiptPayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"receiptpay.jsp");

    }

    /**
     * 回单付结算页面
     */
    public void goReceiptPayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"receiptpay_js.jsp");

    }
    /**
     * 月付index
     */
    public void monthPayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"monthpay.jsp");

    }

    /**
     * 月付结算页面
     */
    public void goMonthPayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"monthpay_js.jsp");

    }
    /**
     * 短欠付index
     */
    public void arrearsPayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"arrearspay.jsp");

    }

    /**
     * 短欠付结算页面
     */
    public void goArrearsPayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"arrearspay_js.jsp");

    }
    /**
     * 货款付index
     */
    public void goodsPayIndex(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"goodspay.jsp");

    }

    /**
     * 货款付结算页面
     */
    public void goGoodsPayJS(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        setAttr("userNetworks", userNetworks);
        setAttr("comNetworks", comNetworks);
        renderJsp(path+"goodspay_js.jsp");

    }



    /**
     * 运单应收（全部）分页json接口
     */
    public void getAllShipReJson(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        paginator.setPageSize(10);
        if(paginator.getPageSize()>10){
            paginator.setPageSize(10);
        }
        ReceivableSearchModel searchModel=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());
        Page<Record> page=new ReceivableSvc().getAllShipReList(user,searchModel,paginator);
        renderJson(page);
    }

    /**
     * 运单应收（现付）json接口
     */
    public void getNowPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getNowPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getNowPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 运单应收（提付）json接口
     */
    public void getPickupPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getPickupPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getPickupPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 运单应收（回单付）json接口
     */
    public void getReceiptPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getReceiptPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getReceiptPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 运单应收（月付）json接口
     */
    public void getMonthPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getMonthPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getMonthPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 运单应收（短欠付）json接口
     */
    public void getArrearsPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getArrearsPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getArrearsPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 运单应收（货款付）json接口
     */
    public void getGoodsPayReJson(){
        String flag=getPara("flag")==null?"":getPara("flag");
        SessionUser user =getSessionSysUser();
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        if(flag.equals("all")){
            List<Record> list=(List)new ReceivableSvc().getGoodsPayShipReList(user,search,true);
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
            Page<Record> page=(Page)new ReceivableSvc().getGoodsPayShipReList(user,search,false,paginator);
            renderJson(page);
        }
    }

    /**
     * 现付结算
     */
    public void confirmNowPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmNowPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 提付结算
     */
    public void confirmPickupPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmPickupPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 回单付结算
     */
    public void confirmReceiptPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmReceiptPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 月付结算
     */
    public void confirmMonthPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmMonthPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 短欠付结算
     */
    public void confirmArrearsPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmArrearsPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 贷款付结算
     */
    public void confirmGoodsPay(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new ReceivableSvc().confirmGoodsPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 下载excel列表
     */
    public void downExcel() throws UnsupportedEncodingException {
        int flag=getParaToInt("flag");

        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        SessionUser user = getSessionSysUser();
        String[] head = null;
        String[] attrs = null;
        String fileName="";
        List<Record> list=null;

        ReceivableSvc svc=new ReceivableSvc();

        switch (flag){
           case 1:
               head = new String[]{"序号","运单号","网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","现付","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_nowpay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getNowPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="现付列表";
               break;
           case 2:
               head = new String[]{"序号","运单号","到货网点","开单网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","提付","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","toNetWorkName","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_pickuppay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getPickupPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="提付列表";
               break;
           case 3:
               head = new String[]{"序号","运单号","网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","回付","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_receiptpay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getReceiptPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="回单付列表";
               break;
           case 4:
               head = new String[]{"序号","运单号","网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","月结","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_nowpay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getMonthPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="月结付列表";
               break;
           case 5:
               head = new String[]{"序号","运单号","网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","短欠","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_nowpay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getArrearsPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="短欠付列表";
               break;
           case 6:
               head = new String[]{"序号","运单号","网点","货号","开单日期","出发地","到达地","托运方","收货方","运单状态","货款扣","结算状态","客户单号","货物名称","体积","重量","件数"};
               attrs = new String[]{"orderNum","ship_sn","netWorkName","goods_sn","create_time","fromAdd","toAdd","senderName","receiverName","shipStatus","ship_nowpay_fee","state","ship_customer_number","productName","ship_volume","ship_weight","ship_amount"};
               list=(List)svc.getGoodsPayShipReList(user,search,false);
               list=svc.changeExcelList(list);
               fileName="货款付列表";
               break;
           default:
               break;
        }

        exportExcel(head,attrs,list, URLEncoder.encode(fileName, "UTF-8"));
        renderNull();

    }

    public void downExcelAll(){
        ReceivableSearchModel search=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());

        List<ExcelReceivableAll> excelList=new ArrayList<>();

        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        paginator.setPageSize(9999);
        ReceivableSearchModel searchModel=ReceivableSearchModel.getBindModel(ReceivableSearchModel.class,getRequest());
        Page<Record> page=new ReceivableSvc().getAllShipReList(user,searchModel,paginator);
        int i=1;
        for (Record record : page.getList()) {
            ExcelReceivableAll excel=new ExcelReceivableAll();
            excel.setOrderNum(i);
            excel.setShipSn(record.get("ship_sn"));
            excel.setShipNetName(record.get("netWorkName"));
            excel.setGoodsSn(record.get("goods_sn"));
            excel.setTime(record.get("create_time").toString());
            excel.setFromAdd(record.get("fromAdd"));
            excel.setToAdd(record.get("toAdd"));
            excel.setSenderName(record.get("senderName"));
            excel.setReceiverName(record.get("receiverName"));
            excel.setToNetWorkName(record.get("toNetWorkName"));
            excel.setShipState(record.get("ship_status").toString());
            excel.setFeeState(record.get("state").toString());
            excel.setTotalFee(record.getBigDecimal("ship_total_fee").doubleValue());
            excel.setNotTotalFee(record.getDouble("not_total_fee"));
            if(StringUtils.isNotBlank(record.get("ship_nowpay_fee"))){
                excel.setNowpayFee(Double.parseDouble(record.get("ship_nowpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_nowpay_fee"))){
                excel.setNotNowpayFee(Double.parseDouble(record.get("not_nowpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("ship_pickuppay_fee"))){
                excel.setPickupPay(Double.parseDouble(record.get("ship_pickuppay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_pickuppay_fee"))){
                excel.setNotPickupPay(Double.parseDouble(record.get("not_pickuppay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("ship_receiptpay_fee"))){
                excel.setReceiptPay(Double.parseDouble(record.get("ship_receiptpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_receiptpay_fee"))){
                excel.setNotReceiptPay(Double.parseDouble(record.get("not_receiptpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("ship_monthpay_fee"))){
                excel.setMonthPay(Double.parseDouble(record.get("ship_monthpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_monthpay_fee"))){
                excel.setNotMonthPay(Double.parseDouble(record.get("not_monthpay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("ship_arrearspay_fee"))){
                excel.setArrearsPay(Double.parseDouble(record.get("ship_arrearspay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_goodspay_fee"))){
                excel.setNotArrearsPay(Double.parseDouble(record.get("not_goodspay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("ship_goodspay_fee"))){
                excel.setGoodsPay(Double.parseDouble(record.get("ship_goodspay_fee")));
            }
            if(StringUtils.isNotBlank(record.get("not_goodspay_fee"))){
                excel.setNotGoodsPay(Double.parseDouble(record.get("not_goodspay_fee")));
            }
            excel.setShipCustomerNumber(record.get("ship_customer_number"));
            excel.setGoodsName(record.get("productName"));
            excel.setShipVolume(record.getDouble("ship_volume"));
            excel.setShipWeight(record.getDouble("ship_weight"));
            excel.setShipAmount(record.getInt("ship_amount"));
            excelList.add(excel);
            i++;
        }

        try {
            String filename = new String("应收列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename="+filename);
            POIUtils.generateXlsxExcelStream(excelList, "应收列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 导出excel
     */
    public void exportExcel(String[] head, String[] attrs, List<Record> list,String fileName) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("area");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for(int i=0;i<head.length;i++){
            header.createCell(i).setCellValue(head[i]);
        }

        //数据列表
        for(int i=0;i<list.size();i++){
            Record record = list.get(i);
            HSSFRow row = sheet.createRow(i+1);
            for(int j=0;j<attrs.length;j++) {
                if(record.get(attrs[j])!=null){
                    row.createCell(j).setCellValue(record.get(attrs[j]).toString());
                }
            }
        }

        //把excel文件写出给用户（浏览器，响应，等用于文件下载）
        HttpServletResponse response = getResponse();

        //设置弹出框（content-disposition)
        response.setHeader("content-disposition", "attachment;filename="+fileName+".xls");

        try {
            wb.write(response.getOutputStream());
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
