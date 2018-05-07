package com.supyuan.kd.finance.examine;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.waybill.ExcelkdShip;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSearchModel;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.excel.poi.POIUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 运单审核
 *
 * @author dingfei
 * @date 2018-03-19 14:56
 **/

@ControllerBind(controllerKey = "/kd/finance/examine")
public class ExamineController extends BaseProjectController {
    private static final String path = "/pages/kd/finance/examine/";

    public void index(){
        SessionUser user = getSessionSysUser();
        String types=getPara("types");
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        setAttr("startTime", sdf.format(new Date()));
        setAttr("endTime",sdf.format(new Date()));
        setAttr("networks", networks);
        if(StringUtils.isBlank(types)){
            renderJsp(path+"unaudited.jsp");
        }else{
            renderJsp(path+"audited.jsp");
        }

    }

    public void search(){
        SessionUser user = getSessionSysUser();
        int status=getParaToInt("status");
        Paginator paginator = getPaginator();
        KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
        Page<KdShip> ships=new KdShipSvc().getExamineShip(model,status,user,paginator);
        renderJson(ships);
    }

    /**
     * 审核通过
     */
    public void successExamine(){
        BaseResult result = new BaseResult();
        String  shipIds=getPara("shipIds");
        boolean flag=new KdShipSvc().successExamine(shipIds);
        if (flag){
            result.setResultType(ResultType.SUCCESS);
        }else{
            result.setResultType(ResultType.FAIL);
        }
        renderJson(result);

    }

    /**
     * 取消审核
     */
    public void cancelExamine(){
        BaseResult result = new BaseResult();
        String  shipIds=getPara("shipIds");
        boolean flag=new KdShipSvc().cancelExamine(shipIds);
        if (flag){
            result.setResultType(ResultType.SUCCESS);
        }else{
            result.setResultType(ResultType.FAIL);
        }
        renderJson(result);

    }



    /**
     * 导出未审核运单excel
     */
    public void exportUnShip(){
        SessionUser user = getSessionSysUser();
        Paginator paginator=getPaginator();
        paginator.setPageSize(100);
        List<ExcelExamine> exList=new ArrayList<>();
        KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
        int status=0;
        Page<KdShip> page=new KdShipSvc().getExamineShip(model,status,user,paginator);
        List<KdShip> list=page.getList();
        Integer row=1;
        for (KdShip kdship: list) {
            ExcelExamine excelExamine=new ExcelExamine();
            excelExamine.setRowNum(row);
            excelExamine.setShipSn(kdship.get("ship_sn"));
            excelExamine.setNetWorkName(kdship.get("netWorkName"));
            excelExamine.setCreateTime(kdship.get("create_time").toString());
            excelExamine.setSenderName(kdship.get("senderName"));
            excelExamine.setToAdd(kdship.get("toAdd"));
            excelExamine.setNowpayFee(kdship.getDouble("ship_nowpay_fee"));
            excelExamine.setPickuppayFee(kdship.getDouble("ship_pickuppay_fee"));
            excelExamine.setReceiptpayFee(kdship.getDouble("ship_receiptpay_fee"));
            excelExamine.setMonthpayFee(kdship.getDouble("ship_monthpay_fee"));
            excelExamine.setArrearspayFee(kdship.getDouble("ship_arrearspay_fee"));
            excelExamine.setTotalFee(kdship.getBigDecimal("ship_total_fee").doubleValue());
            excelExamine.setRebateFee(kdship.getBigDecimal("ship_rebate_fee").doubleValue());
            excelExamine.setReceiptsFee(kdship.getBigDecimal("receiptsFee").doubleValue());
            excelExamine.setGoodsSn(kdship.get("goods_sn"));
            excelExamine.setProductName(kdship.get("productName"));
            excelExamine.setUserName(kdship.get("shipName"));
            exList.add(excelExamine);
            row++;
        }
        try {
            String filename = new String("运单未审核列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename="+filename);
            POIUtils.generateXlsxExcelStream(exList, "运单未审核列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出已审核运单excel
     */
    public void exportAuShip(){
        SessionUser user = getSessionSysUser();
        Paginator paginator=getPaginator();
        paginator.setPageSize(100);
        int status=1;
        List<ExcelExamine> exList=new ArrayList<>();
        KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
        Page<KdShip> page=new KdShipSvc().getExamineShip(model,status,user,paginator);
        List<KdShip> list=page.getList();
        Integer row=1;
        String state;
        for (KdShip kdship: list) {
            if (kdship.getInt("ship_status")==1){
                state="已入库";
            }else if(kdship.getInt("ship_status")==2){
                state="短驳中";
            }else if(kdship.getInt("ship_status")==3){
                state="短驳到达";
            }else if(kdship.getInt("ship_status")==4){
                state="已发车";
            }else if(kdship.getInt("ship_status")==5){
                state="已到达";
            }else if(kdship.getInt("ship_status")==6){
                state="收货中转中";
            }else if(kdship.getInt("ship_status")==7){
                state="到货中转中";
            }else if(kdship.getInt("ship_status")==8) {
                state = "送货中";
            }else{
                state = "已签收";
            }
            ExcelExamine excelExamine=new ExcelExamine();
            excelExamine.setRowNum(row);
            excelExamine.setShipSn(kdship.get("ship_sn"));
            excelExamine.setNetWorkName(kdship.get("netWorkName"));
            excelExamine.setGoodsSn(kdship.get("goods_sn"));
            excelExamine.setCreateTime(kdship.get("create_time").toString());
            excelExamine.setFormAdd(kdship.get("fromAdd"));
            excelExamine.setToAdd(kdship.get("toAdd"));
            excelExamine.setSenderName(kdship.get("senderName"));
            excelExamine.setReceiverName(kdship.get("receiverName"));
            excelExamine.setShipState(state);
            excelExamine.setExamineState("已审核");
            excelExamine.setTotalFee(kdship.getBigDecimal("ship_total_fee").doubleValue());
            excelExamine.setRebateFee(kdship.getBigDecimal("ship_rebate_fee").doubleValue());
            excelExamine.setReceiptsFee(kdship.getBigDecimal("receiptsFee").doubleValue());
            excelExamine.setNowpayFee(kdship.getDouble("ship_nowpay_fee"));
            excelExamine.setPickuppayFee(kdship.getDouble("ship_pickuppay_fee"));
            excelExamine.setReceiptpayFee(kdship.getDouble("ship_receiptpay_fee"));
            excelExamine.setMonthpayFee(kdship.getDouble("ship_monthpay_fee"));
            excelExamine.setArrearspayFee(kdship.getDouble("ship_arrearspay_fee"));
            excelExamine.setGoodspayFee(kdship.getDouble("ship_goodspay_fee"));
            exList.add(excelExamine);
            row++;
        }
        try {
            String filename = new String("已审核运单列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename="+filename);
            POIUtils.generateXlsxExcelStream(exList, "已审核运单列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
