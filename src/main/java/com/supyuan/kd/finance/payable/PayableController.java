package com.supyuan.kd.finance.payable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

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
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.DateUtils;
import com.supyuan.util.excel.poi.POIUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 应付
 *
 * @author liangxp
 * <p>
 * Date:2017年12月20日下午6:00:14
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/finance/payable")
public class PayableController extends BaseProjectController {

    private static final String path = "/pages/kd/finance/payable/";


    public void index() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("networks", netWorkList);
        List<LogisticsNetwork> tonetWorks = new NetWorkSvc().getNetWorkList(user);
        setAttr("tonetWorks", tonetWorks);
        renderJsp(path + "loadingpay.jsp");
    }


    /**
     * 发车汇总搜索
     *
     * @author yuwen
     */
    public void search() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(20);
        }

        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        Page<KdTrunkLoad> loads = new PayableSvc().queryTrunkLoadList(paginator, user, model, "", false);
        renderJson(loads);
    }


    /**
     * 到车汇总页面
     *
     * @author yuwen
     */
    public void toSum() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("networks", netWorkList);
        List<LogisticsNetwork> tonetWorks = new NetWorkSvc().getNetWorkList(user);
        setAttr("tonetWorks", tonetWorks);
        renderJsp(path + "ToSum.jsp");
    }
    /**
     * 到车汇总搜索
     *
     * @author yuwen
     */
    public void toSumSearch() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        Page<KdTrunkLoad> loads = new PayableSvc().queryTrunkLoadList(paginator, user, model, "ToSum", false);
        setAttr("loads", loads);
        renderJson(loads);
    }


    /**
     * 发车汇总excel导出
     */
    public void exportStartSumlist() {
        SessionUser user = getSessionSysUser();
        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        try {
            Page<KdTrunkLoad> queryList = new PayableSvc().queryTrunkLoadList(null, user, model, "", true);
            List<ExcelStartSumModel> res = new ArrayList<ExcelStartSumModel>();
            int row = 1;
            for (KdTrunkLoad record : queryList.getList()) {
                ExcelStartSumModel mo = new ExcelStartSumModel();
                mo.setOrderNum(row++);
                mo.setLoadSn(record.getStr("load_sn"));
                mo.setSnetworkName(record.getStr("snetworkName"));
                mo.setEnetworkName(record.getStr("enetworkName"));
                mo.setTruckIdNumber(record.getStr("truck_id_number"));
                mo.setDepartTime(DateUtils.format(record.getDate("load_depart_time"), DateUtils.DEFAULT_REGEX));
                mo.setTruckIdNumber(record.getStr("truck_id_number"));
                mo.setDriverName(record.getStr("truck_driver_name"));
                mo.setDriverMobile(record.get("truck_driver_mobile"));
                int transportType = record.getInt("load_transport_type");


                int deliveryStatus = record.getInt("load_delivery_status");
                if (deliveryStatus == 1) {
                    mo.setDeliveryStatus("配载中");
                } else if (deliveryStatus == 2) {
                    mo.setDeliveryStatus("已发车");
                } else if (deliveryStatus == 3) {
                    mo.setDeliveryStatus("已到达");
                } else {
                    mo.setDeliveryStatus("已完成");
                }

                mo.setFromAddr(record.getStr("fromCity"));
                mo.setToAddr(record.getStr("toCity"));
                String load_fee = record.get("load_fee", 0).toString();
                String load_fee_fill = record.getBoolean("load_fee_fill")!= null && record.getBoolean("load_fee_fill")? "" : load_fee;

                String load_nowtrans_fee = record.get("load_nowtrans_fee", 0).toString();
                String load_nowtrans_fill =record.getBoolean("load_nowtrans_fill")!= null &&  record.getBoolean("load_nowtrans_fill")? "" : load_nowtrans_fee;

                String load_nowoil_fee = record.get("load_nowoil_fee", 0).toString();
                String load_nowoil_fill =record.getBoolean("load_nowoil_fill")!= null &&  record.getBoolean("load_nowoil_fill")? "" : load_nowoil_fee;

                String load_backtrans_fee = record.get("load_backtrans_fee", 0).toString();
                String load_backtrans_fill =record.getBoolean("load_backtrans_fill")!= null &&  record.getBoolean("load_backtrans_fill")? "" : load_backtrans_fee;

                String load_attrans_fee = record.get("load_attrans_fee", 0).toString();
                String load_attrans_fill =record.getBoolean("load_attrans_fill")!= null &&  record.getBoolean("load_attrans_fill")? "" : load_attrans_fee;

                String load_allsafe_fee = record.get("load_allsafe_fee", 0).toString();
                String load_allsafe_fill =record.getBoolean("load_allsafe_fill")!= null &&  record.getBoolean("load_allsafe_fill")? "" : load_allsafe_fee;

                String load_start_fee = record.get("load_start_fee", 0).toString();
                String load_start_fill =record.getBoolean("load_start_fill")!= null &&  record.getBoolean("load_start_fill")? "" : load_start_fee;

                String load_other_fee = record.get("load_other_fee", 0).toString();
                String load_other_fill =record.getBoolean("load_other_fill")!= null &&  record.getBoolean("load_other_fill")? "" : load_other_fee;

                String load_atunload_fee = record.get("load_atunload_fee", 0).toString();
                String load_atunload_fill =record.getBoolean("load_atunload_fill")!= null &&  record.getBoolean("load_atunload_fill")? "" : load_atunload_fee;

                String load_atother_fee = record.get("load_atother_fee", 0).toString();
                String load_atother_fill =record.getBoolean("load_atother_fill")!= null &&  record.getBoolean("load_atother_fill")? "" : load_atother_fee;

                if (transportType == 1) {
                    mo.setTransportType("提货");
                    mo.setUpfee(load_fee);
                    mo.setNoUpfee(load_fee_fill);
                    mo.setDrayage("");
                    mo.setNoDrayage("");
                    mo.setSendfee("");
                    mo.setNoSendfee("");


                    mo.setNowtransfee("");
                    mo.setNoNowtransfee("");

                    mo.setNowoilfee("");
                    mo.setNoNowoilfee("");

                    mo.setBacktransfee("");
                    mo.setNoBacktransfee("");

                    mo.setAttransfee("");
                    mo.setNoAttransfee("");

                    mo.setAllsafefee("");
                    mo.setNoAllsafefee("");

                    mo.setStartfee("");
                    mo.setNoStartfee("");

                    mo.setOtherfee("");
                    mo.setNoOtherfee("");
                } else if (transportType == 2) {
                    mo.setTransportType("短驳");
                    mo.setDrayage(load_fee);
                    mo.setNoDrayage(load_fee_fill);
                    mo.setUpfee("");
                    mo.setNoUpfee("");
                    mo.setSendfee("");
                    mo.setNoSendfee("");


                    mo.setNowtransfee("");
                    mo.setNoNowtransfee("");

                    mo.setNowoilfee("");
                    mo.setNoNowoilfee("");

                    mo.setBacktransfee("");
                    mo.setNoBacktransfee("");

                    mo.setAttransfee("");
                    mo.setNoAttransfee("");

                    mo.setAllsafefee("");
                    mo.setNoAllsafefee("");

                    mo.setStartfee("");
                    mo.setNoStartfee("");

                    mo.setOtherfee("");
                    mo.setNoOtherfee("");
                } else if (transportType == 3) {
                    mo.setTransportType("干线");
                    mo.setUpfee("");
                    mo.setNoUpfee("");
                    mo.setDrayage("");
                    mo.setNoDrayage("");
                    mo.setSendfee("");
                    mo.setNoSendfee("");

                    mo.setNowtransfee(load_nowtrans_fee);
                    mo.setNoNowtransfee(load_nowtrans_fill);

                    mo.setNowoilfee(load_nowoil_fee);
                    mo.setNoNowoilfee(load_nowoil_fill);

                    mo.setBacktransfee(load_backtrans_fee);
                    mo.setNoBacktransfee(load_backtrans_fill);

                    mo.setAttransfee(load_attrans_fee);
                    mo.setNoAttransfee(load_attrans_fill);

                    mo.setAllsafefee(load_allsafe_fee);
                    mo.setNoAllsafefee(load_allsafe_fill);

                    mo.setStartfee(load_start_fee);
                    mo.setNoStartfee(load_start_fill);

                    mo.setOtherfee(load_other_fee);
                    mo.setNoOtherfee(load_other_fill);

                } else {
                    mo.setTransportType("送货");
                    mo.setSendfee(load_fee);
                    mo.setNoSendfee(load_fee_fill);
                    mo.setDrayage("");
                    mo.setNoDrayage("");
                    mo.setUpfee("");
                    mo.setNoUpfee("");

                    mo.setNowtransfee("");
                    mo.setNoNowtransfee("");

                    mo.setNowoilfee("");
                    mo.setNoNowoilfee("");

                    mo.setBacktransfee("");
                    mo.setNoBacktransfee("");

                    mo.setAttransfee("");
                    mo.setNoAttransfee("");

                    mo.setAllsafefee("");
                    mo.setNoAllsafefee("");

                    mo.setStartfee("");
                    mo.setNoStartfee("");

                    mo.setOtherfee("");
                    mo.setNoOtherfee("");
                }
                mo.setAtunloadfee(load_atunload_fee);
                mo.setNoAtunloadfee(load_atunload_fill);

                mo.setAtotherfee(load_atother_fee);
                mo.setNoAtotherfee(load_atother_fill);
                /*mo.setTotalCost(load_fee + load_nowtrans_fee + load_nowoil_fee + load_backtrans_fee + load_attrans_fee +
						load_allsafe_fee + load_start_fee + load_other_fee + load_atunload_fee + load_atother_fee);*/
                mo.setTotalCost(record.getDouble("totalCost"));

                res.add(mo);
            }
            String filename = new String("发车汇总列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(res, "发车汇总列表.xlsx", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        renderNull();
    }


    /**
     * 到车汇总excel导出
     */
    public void exportToSumlist() {
        SessionUser user = getSessionSysUser();
        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        try {
            Page<KdTrunkLoad> queryList = new PayableSvc().queryTrunkLoadList(null, user, model, "ToSum", true);
            List<ExcelToSumModel> res = new ArrayList<ExcelToSumModel>();
            int row = 1;
            for (KdTrunkLoad record : queryList.getList()) {
                ExcelToSumModel mo = new ExcelToSumModel();
                mo.setOrderNum(row++);
                mo.setLoadSn(record.getStr("load_sn"));
                mo.setSnetworkName(record.getStr("snetworkName"));
                mo.setEnetworkName(record.getStr("enetworkName"));
                mo.setTruckIdNumber(record.getStr("truck_id_number"));
                mo.setDepartTime(DateUtils.format(record.getDate("load_depart_time"), DateUtils.DEFAULT_REGEX));
                mo.setArrivalTime(DateUtils.format(record.getDate("load_arrival_time"), DateUtils.DEFAULT_REGEX));
                mo.setTruckIdNumber(record.getStr("truck_id_number"));
                mo.setDriverName(record.getStr("truck_driver_name"));
                mo.setDriverMobile(record.get("truck_driver_mobile"));
                int deliveryStatus = record.getInt("load_delivery_status");
                if (deliveryStatus == 3) {
                    mo.setDeliveryStatus("已到达");
                } else {
                    mo.setDeliveryStatus("已完成");
                }

                String load_attrans_fee = record.get("load_attrans_fee", 0).toString();
                String load_attrans_fill =record.getBoolean("load_attrans_fill")!= null &&  record.getBoolean("load_attrans_fill")? "" : load_attrans_fee;

                String load_start_fee = record.get("load_start_fee", 0).toString();
                String load_start_fill =record.getBoolean("load_start_fill")!= null &&  record.getBoolean("load_start_fill")? "" : load_start_fee;

                String load_atunload_fee = record.get("load_atunload_fee", 0).toString();
                String load_atunload_fill =record.getBoolean("load_atunload_fill")!= null &&  record.getBoolean("load_atunload_fill")? "" : load_atunload_fee;

                String load_atother_fee = record.get("load_atother_fee", 0).toString();
                String load_atother_fill =record.getBoolean("load_atother_fill")!= null &&  record.getBoolean("load_atother_fill")? "" : load_atother_fee;

                if (record.getInt("load_transport_type") == 3) {
                    mo.setAttransfee(load_attrans_fee);
                    mo.setNoAttransfee(load_attrans_fill);
                    mo.setAtunloadfee(load_atunload_fee);
                    mo.setNoAtunloadfee(load_atunload_fill);
                    mo.setAtotherfee(load_atother_fee);
                    mo.setNoAtotherfee(load_atother_fill);
                }else {
                    mo.setAttransfee("");
                    mo.setNoAttransfee("");
                    mo.setAtunloadfee(load_atunload_fee);
                    mo.setNoAtunloadfee(load_atunload_fill);
                    mo.setAtotherfee(load_atother_fee);
                    mo.setNoAtotherfee(load_atother_fill);
                }
				/*mo.setTotalCost(load_fee + load_nowtrans_fee + load_nowoil_fee + load_backtrans_fee + load_attrans_fee +
						load_allsafe_fee + load_start_fee + load_other_fee + load_atunload_fee + load_atother_fee);*/
                mo.setTotalCost(record.getDouble("totalFee"));

                res.add(mo);
            }
            String filename = new String("到车汇总列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(res, "到车汇总列表.xlsx", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        renderNull();
    }



    /**
     * 根据运输类型页面
     * @author huangym
     * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费
     * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
     */
    public void loadTransportPage(){
        SessionUser user = getSessionSysUser();
        String transportType = getPara("transportType");

        String view = null;
        List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("networks", netWorkList);
        setAttr("transportType",transportType);
        if("1".equals(transportType)){
            view = "pickuppay.jsp";
        }else if("2".equals(transportType)){
            view = "drayage.jsp";
        }else if("4".equals(transportType)){
            view = "delivery.jsp";
        }else if("3".equals(transportType)){
            String trunkLineType = getPara("trunkLineType");
            if("1".equals(trunkLineType)){
                view = "transport.jsp";
            }else if("2".equals(trunkLineType)){
                view = "oilcard.jsp";
            }else if("3".equals(trunkLineType)){
                view = "backpay.jsp";
            }else if("4".equals(trunkLineType)){
                view = "allsafe.jsp";
            }else if("5".equals(trunkLineType)){
                view = "start.jsp";
            }else if("6".equals(trunkLineType)){
                view = "other.jsp";
            }else if("7".equals(trunkLineType)){
                view = "totransport.jsp";
            }
        }else{
            String loadAtFeeFlag = getPara("loadAtFeeFlag");
            if("0".equals(loadAtFeeFlag)){
                view = "tounload.jsp";
            }else if("1".equals(loadAtFeeFlag)){
                view = "toother.jsp";
            }
        }
        renderJsp(path+view);
    }

    /**
     * 根据运输类型搜索
     * @author huangym
     * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费
     * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
     */
    public void trunkLoadListSearch(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        String flag = getPara("flag");
        boolean serrlement = false;
        if (!"all".equals(flag)){
            paginator.setPageSize(10);
        }else{
            paginator.setPageSize(1000);
            serrlement = true;
        }
        String transportType = getPara("transportType");
        String trunkLineType = null;
        if("3".equals(transportType)){
            trunkLineType = getPara("trunkLineType");
        }
        String loadAtFeeFlag = null;
        if(transportType == null){
            loadAtFeeFlag = getPara("loadAtFeeFlag");
        }
        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        Page<KdTrunkLoad> loads = new PayableSvc().queryTrunkLoadListByTransportType(paginator,user,model,transportType,trunkLineType,loadAtFeeFlag,false,serrlement);
        renderJson(loads);
    }



    /**
     * 根据运输类型搜索导出到excel
     * @author huangym
     * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费
     * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
     */
    public void trunkLoadListExport() {
        SessionUser user = getSessionSysUser();
        String[] head = null;
        String[] attrs = null;
        String filename="";

        String trunkLineType = null;
        String loadAtFeeFlag = null;

        int feeFillIndex = 9;//结算状态索引
        int deliveryStatusIndex = 8;//配载状态索引
        int noFillIndex= 11; //未结算费用索引


        String transportType = getPara("transportType");

        if("1".equals(transportType)){
            head = new String[]{"配载单号","配载网点","发车日期","车牌号","司机","司机电话","出发地","到达地","配载状态","结算状态","提货费","未结提货费","提货运单数","提货体积","提货重量","提货件数"};
            attrs = new String[]{"load_sn","snetworkName","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity","load_delivery_status","load_fee_fill","load_fee","load_fee_fill","load_count","load_volume","load_weight","load_amount"};
            filename="提货费列表";
        }else if("4".equals(transportType)){
            head = new String[]{"配载单号","配载网点","发车日期","车牌号","司机","司机电话","出发地","到达地","配载状态","结算状态","送货费","未结送货费","送货运单数","送货体积","送货重量","送货件数"};
            attrs = new String[]{"load_sn","snetworkName","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity","load_delivery_status","load_fee_fill","load_fee","load_fee_fill","load_count","load_volume","load_weight","load_amount"};
            filename="送货费列表";
        }else if("2".equals(transportType)){
            head = new String[]{"配载单号","配载网点","发车日期","车牌号","司机","司机电话","出发地","到达地","配载状态","结算状态","短驳费","未结短驳费","短驳运单数","短驳体积","短驳重量","短驳件数"};
            attrs = new String[]{"load_sn","snetworkName","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity","load_delivery_status","load_fee_fill","load_fee","load_fee_fill","load_count","load_volume","load_weight","load_amount"};
            filename="短驳费列表";
        }else if("3".equals(transportType)){//干线
            feeFillIndex = 5;//结算状态索引
            deliveryStatusIndex = 10;//配载状态索引
            noFillIndex= 4; //未结算费用索引
            trunkLineType = getPara("trunkLineType");
            if("1".equals(trunkLineType)){//现付运输费
                head = new String[]{"配载单号","配载网点","到货网点","现付运输费","未结现付运输费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_nowtrans_fee","load_nowtrans_fill","load_nowtrans_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="现付运输费列表";
            }else if("2".equals(trunkLineType)){//现付油卡费
                head = new String[]{"配载单号","配载网点","到货网点","现付油卡费","未结现付油卡费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_nowoil_fee","load_nowoil_fill","load_nowoil_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="现付油卡费列表";
            }else if("3".equals(trunkLineType)){//回付运输费
                head = new String[]{"配载单号","配载网点","到货网点","回付运输费","未结回付运输费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_backtrans_fee","load_backtrans_fill","load_backtrans_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="回付运输费列表";
            }else if("4".equals(trunkLineType)){//整车保险费
                head = new String[]{"配载单号","配载网点","到货网点","整车保险费","未结整车保险费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_allsafe_fee","load_allsafe_fill","load_allsafe_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="整车保险费列表";
            }else if("5".equals(trunkLineType) ){//发站装车费
                head = new String[]{"配载单号","配载网点","到货网点","发站装车费","未结发站装车费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_start_fee","load_start_fill","load_start_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="发站装车费列表";
            }else if("6".equals(trunkLineType) ){//发站其他费
                head = new String[]{"配载单号","配载网点","到货网点","发站其他费","未结发站其他费","结算状态","发车日期","车牌号","司机","司机电话","配载状态","出发地","到达地","装车运单数","装车体积","装车重量","装车件数"};
                attrs = new String[]{"load_sn","snetworkName","enetworkName","load_other_fee","load_other_fill","load_other_fill","load_depart_time","truck_id_number","truck_driver_name","truck_driver_mobile","load_delivery_status","fromCity","toCity","load_count","load_volume","load_weight","load_amount"};
                filename="发站其他费列表";
            }else if("7".equals(trunkLineType) ){//到付运输费
                head = new String[]{"配载单号","到货网点","配载网点","配载状态","结算状态","到付运输费","未结到付运输费","发车日期","到车日期","车牌号","司机","司机电话","出发地","到达地"};
                attrs = new String[]{"load_sn","enetworkName","snetworkName","load_delivery_status","load_attrans_fill","load_attrans_fee","load_attrans_fill","load_depart_time","load_arrival_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity"};
                filename="到付运输费列表";
                feeFillIndex = 4;//结算状态索引
                deliveryStatusIndex = 3;//配载状态索引
                noFillIndex= 6; //未结算费用索引
            }
        }else{
            loadAtFeeFlag = getPara("loadAtFeeFlag");
            feeFillIndex = 4;//结算状态索引
            deliveryStatusIndex = 3;//配载状态索引
            noFillIndex= 6; //未结算费用索引
            if("0".equals(loadAtFeeFlag) ){//到站卸车费
                head = new String[]{"配载单号","到货网点","配载网点","配载状态","结算状态","到站卸车费","未结到站卸车费","发车日期","到车日期","车牌号","司机","司机电话","出发地","到达地"};
                attrs = new String[]{"load_sn","enetworkName","snetworkName","load_delivery_status","load_atunload_fill","load_atunload_fee","load_atunload_fill","load_depart_time","load_arrival_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity"};
                filename="到站卸车费列表";
            }if("1".equals(loadAtFeeFlag)){//到站其他费
                head = new String[]{"配载单号","到货网点","配载网点","配载状态","结算状态","到站其他费","未结到站其他费","发车日期","到车日期","车牌号","司机","司机电话","出发地","到达地"};
                attrs = new String[]{"load_sn","enetworkName","snetworkName","load_delivery_status","load_atother_fill","load_atother_fee","load_atother_fill","load_depart_time","load_arrival_time","truck_id_number","truck_driver_name","truck_driver_mobile","fromCity","toCity"};
                filename="到站其他费列表";
            }
        }

        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        Page<KdTrunkLoad> loads = new PayableSvc().queryTrunkLoadListByTransportType(null,user,model,transportType,trunkLineType,loadAtFeeFlag,true,false);
        exportExcel(head,attrs,loads.getList(),filename,feeFillIndex,deliveryStatusIndex,noFillIndex);

        renderNull();
    }


    /**
     * 导出excel
     * @author huangym
     * @param head 表头数组
     * @param attrs 属性名
     * @param  list 数据列表
     * @param feeFillIndex
     * @param deliveryStatusIndex
     */
    public void exportExcel(String[] head, String[] attrs, List<KdTrunkLoad> list, String filename, int feeFillIndex, int deliveryStatusIndex,int noFillIndex) {



        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for(int i=0;i<head.length;i++){
            header.createCell(i).setCellValue(head[i]);
        }

        //数据列表
        for(int i=0;i<list.size();i++){
            KdTrunkLoad load = list.get(i);
            HSSFRow row = sheet.createRow(i+1);
            for(int j=0;j<attrs.length;j++) {
                String str = load.get(attrs[j])+"";
                if(j==feeFillIndex){
                    if("true".equals(str)) str = "已结算";
                    else str = "未结算";
                }
                if(j==deliveryStatusIndex){
                    if("1".equals(str)) str = "配载中";
                    else if("2".equals(str)) str = "已发车";
                    else if("3".equals(str)) str = "已到达";
                    else if("4".equals(str)) str = "已完成";
                }
                if(j==noFillIndex){
                    if("true".equals(str)) str="0";
                    else str = load.get(attrs[j-1])+"";
                }
                row.createCell(j).setCellValue(str);
            }
        }

        //把excel文件写出给用户（浏览器，响应，等用于文件下载）
        HttpServletResponse response = getResponse();


        try {
            //设置弹出框（content-disposition)
            response.setHeader("content-disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1")+".xls");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * @author huangym
     * 应付中转
     */
    public void transfer(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("userNetworks", userNetworks);


        renderJsp(path + "trains.jsp");
    }

    /**
     * @author huangym
     * 应付中转搜索
     */
    public void transferSearch(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if(paginator.getPageSize()>10){
            paginator.setPageSize(10);
        }

        PayTransferSearchModel search=PayTransferSearchModel.getBindModel(PayTransferSearchModel.class, getRequest());
        Page<Record> page=new PayableSvc().getTransferPayList(paginator, user,search,false);

        renderJson(page);
    }

    /**
     * 中转费导出excel
     * @author huangym
     */
    public void downLoadTransferPay(){
        SessionUser user = getSessionSysUser();

        String[] head = new String[]{"中转单号","中转网点","中转方","中转联系人","中转日期","结算状态","中转费","运单号","提付","代收货款","开单网点","出发地","到达地","托运方","收货方","中转体积","中转重量","中转件数"};
        String[] attrs = new String[]{"ship_transfer_sn","transferNetName","transferCorpName","transferName","ship_transfer_time","pay_status","ship_transfer_fee","ship_sn","ship_pickuppay_fee","ship_agency_fund","shipNetName","fromAdd","toAdd","senderName","receiverName","ship_volume","ship_weight","ship_amount"};
        String filename="中转费列表";
        PayTransferSearchModel search=PayTransferSearchModel.getBindModel(PayTransferSearchModel.class, getRequest());
        List<Record> list = new PayableSvc().getTransferPayList(null, user, search, true).getList();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for(int i=0;i<head.length;i++){
            header.createCell(i).setCellValue(head[i]);
        }

        //数据列表
        for(int i=0;i<list.size();i++){
            Record trans = list.get(i);
            HSSFRow row = sheet.createRow(i+1);
            for(int j=0;j<attrs.length;j++) {
                if (j==5){
                    if("1".equals(trans.get(attrs[j]))) row.createCell(j).setCellValue("已结算");
                    else row.createCell(j).setCellValue("未结算");
                    continue;
                }
                row.createCell(j).setCellValue(trans.get(attrs[j])+"");
            }
        }

        //把excel文件写出给用户（浏览器，响应，等用于文件下载）
        HttpServletResponse response = getResponse();


        try {
            //设置弹出框（content-disposition)
            response.setHeader("content-disposition", "attachment;filename="+new String(filename.getBytes(),"iso-8859-1")+".xls");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        renderNull();
    }

    /**
     * @author huangym
     * 应付回扣页面
     */
    public void rebate(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("userNetworks", userNetworks);

        renderJsp(path + "rebate.jsp");
    }

    /**
     * @author huangym
     * 应付回扣搜索
     */
    public void rebateSearch(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if(paginator.getPageSize()>10){
            paginator.setPageSize(10);
        }

        PayRebateSearchModel search=PayRebateSearchModel.getBindModel(PayRebateSearchModel.class, getRequest());
        Page<KdShip> page=new PayableSvc().getRebatePayList(paginator, user,search,false,false);
        renderJson(page);
    }

    /**
     * 应付回扣结算页面
     */
    public void goRebateJs(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks = new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("userNetworks", userNetworks);

        renderJsp(path + "rebate-settlement.jsp");
    }

    /**
     * @author huangym
     * 应付回扣搜索
     */
    public void getRebateSettlement(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if(paginator.getPageSize()>10){
            paginator.setPageSize(10);
        }

        PayRebateSearchModel search=PayRebateSearchModel.getBindModel(PayRebateSearchModel.class, getRequest());
        Page<KdShip> page=new PayableSvc().getRebatePayList(paginator, user,search,true,true);
        renderJson(page);
    }

    /**
     * 应付回扣结算
     */
    public void confirmRebate(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new PayableSvc().confirmRebate(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);

    }



    /**
     * 下载应付回扣excel列表
     *
     * @author huangym
     */
    public void downRebatePay() {
        SessionUser user = getSessionUser();

        PayRebateSearchModel search=PayRebateSearchModel.getBindModel(PayRebateSearchModel.class, getRequest());


        List<ExcelPayRebate> excelList = new ArrayList();

        Page<KdShip> page=new PayableSvc().getRebatePayList(null, user,search,true,false);
        renderJson(page);

        List<KdShip> list = page.getList();
        int i = 1;
        for (KdShip record : list) {
            ExcelPayRebate excel = new ExcelPayRebate();
            excel.setRebateNum(i);
            excel.setShipSn(record.get("ship_sn"));
            excel.setNetworkName(record.get("networkName"));
            excel.setGoodSn(record.get("goods_sn"));
            excel.setCreateime(record.get("create_time").toString());
            excel.setToAdd(record.get("fromCity"));
            excel.setFromAdd(record.get("toCity"));
            excel.setSenderName(record.get("senderName"));
            excel.setReceiverName(record.get("receiverName"));


            String shipState = "";
            if ("1".equals(record.get("ship_status").toString())) shipState = "已入库";
            else if ("2".equals(record.get("ship_status").toString())) shipState = "短驳中";
            else if ("3".equals(record.get("ship_status").toString())) shipState = "短驳到达";
            else if ("4".equals(record.get("ship_status").toString())) shipState = "已发车";
            else if ("5".equals(record.get("ship_status").toString())) shipState = "已到达";
            else if ("6".equals(record.get("ship_status").toString())) shipState = "收货中转中";
            else if ("7".equals(record.get("ship_status").toString())) shipState = "到货中转中";
            else if ("8".equals(record.get("ship_status").toString())) shipState = "送货中";
            else if ("9".equals(record.get("ship_status").toString())) shipState = "已签收";
            excel.setShipStatus(shipState);

            excel.setShipRebateFee(record.getBigDecimal("ship_rebate_fee")==null?new BigDecimal("0.0"):record.getBigDecimal("ship_rebate_fee"));

            String shipFeeState = "未结算";
            if (record.get("ship_fee_status") != null) if (record.get("ship_fee_status").toString().equals("1")) shipFeeState = "已结算";
            excel.setShipFeeStatus(shipFeeState);

            excel.setShipTotalFee(record.get("ship_total_fee",0.0));
            excel.setProductName(record.get("productName"));

            excel.setShipVolume(record.get("ship_volume").toString());
            excel.setShipWeight(record.get("ship_weight").toString());
            excel.setShipAmount(record.get("ship_amount").toString());
            excel.setUserName(record.get("userName").toString());
            excelList.add(excel);
            i++;
        }

        try {
            String filename = new String("应付回扣列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(excelList, "应付回扣列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据运输类型跳转到结算页面
     * @author yuwen
     * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
     */
    public void settlement(){
        SessionUser user = getSessionSysUser();
        String transportType = getPara("transportType");
        HashMap<String, String> map = new HashMap<>();
        if("1".equals(transportType)){
            map.put("feeType","提货费");
            map.put("transportType","1");
        }else if ("2".equals(transportType)){
            map.put("feeType","短驳费");
            map.put("transportType","2");
        }else{
            map.put("feeType","送货费");
            map.put("transportType","4");
        }
        setAttr("Map",map);
        renderJsp(path+"settlement.jsp");
    }

    /**
     * 根据干线类型跳转到结算页面
     * @author huangym
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费 8:到站卸车费 9:到站其他费
     */
    public void trunkLineSettlement(){
        SessionUser user = getSessionSysUser();
        String trunkLineType = getPara("trunkLineType");
        HashMap<String, String> map = new HashMap<>();
        if("1".equals(trunkLineType)){
            map.put("feeType","现付运输费");
            map.put("trunkLineType","1");
        }else if ("2".equals(trunkLineType)){
            map.put("feeType","现付油卡费");
            map.put("trunkLineType","2");
        }else if("3".equals(trunkLineType)){
            map.put("feeType","回付运输费");
            map.put("trunkLineType","3");
        }else if("4".equals(trunkLineType)){
            map.put("feeType","整车保险费");
            map.put("trunkLineType","4");
        }else if("5".equals(trunkLineType)){
            map.put("feeType","发站装车费");
            map.put("trunkLineType","5");
        }else if("6".equals(trunkLineType)){
            map.put("feeType","发站其他费");
            map.put("trunkLineType","6");
        }else if("7".equals(trunkLineType)){
            map.put("feeType","到付运输费");
            map.put("trunkLineType","7");
        }else if("8".equals(trunkLineType)){
            map.put("feeType","到站卸车费");
            map.put("trunkLineType","8");
        }else if("9".equals(trunkLineType)){
            map.put("feeType","到站其他费");
            map.put("trunkLineType","9");
        }
        setAttr("Map",map);
        renderJsp(path+"trunkLine-settlement.jsp");
    }
    /**
     * 根据干线类型结算搜索
     * @author huangym
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费 8:到站卸车费 9:到站其他费
     * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
     */
    public void trunkLoadSearch(){
        SessionUser user = getSessionSysUser();


        String transportType = "3";
        String trunkLineType = null;
        trunkLineType = getPara("trunkLineType");

        String loadAtFeeFlag = null;
        if( "8".equals(trunkLineType) ){
            transportType = null;
            loadAtFeeFlag = "0";
        }else if( "9".equals(trunkLineType)){
            transportType = null;
            loadAtFeeFlag = "1";
        }else{
            renderNull();
        }
        PayableSearchModel model = PayableSearchModel.getBindModel(PayableSearchModel.class, getRequest());
        Page<KdTrunkLoad> loads = new PayableSvc().queryTrunkLoadListByTransportType(null,user,model,transportType,trunkLineType,loadAtFeeFlag,true,false);
        Map<String,Object> loadsMap = new HashMap<>();
        loadsMap.put("loads",loads);
        loadsMap.put("trunkLineType",trunkLineType);
        renderJson(loadsMap);
    }

    /**
     * 根据运输类型进行结算
     * @author yuwen
     * transportType运输类型 1:提货，2:短驳，3:干线，4:送货
     */
    public void settlementLoads(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String loadIds=getPara("loadIds");
        String transportType=getPara("transportType");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag =  new PayableSvc().confirmNowPay(loadIds,transportType,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 根据干线类型进行结算
     * @author huangym
     * trunkLineType干线类型 1:现付运输费 2:现付油卡费 3:回付运输费 4:整车保险费 5:发站装车费 6:发站其他费 7:到付运输费 8:到站卸车费 9:到站其他费
     * loadAtFeeFlag到站费标记 0:到站卸车费 1:到站其他费
     */
    public void trunkSettlementLoads(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String loadIds=getPara("loadIds");
        String trunkLineType=getPara("trunkLineType");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag =  new PayableSvc().confirmtrunkPay(loadIds,trunkLineType,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }

    /**
     * 中转结算页面数据加载
     * @author huangym
     */
    public void getAllTransferPayList(){
        SessionUser user = getSessionUser();
        PayTransferSearchModel model = PayTransferSearchModel.getBindModel(PayTransferSearchModel.class, getRequest());
        Page<Record> page =new PayableSvc().getAllTransferPayList(user,model);
        renderJson(page);
    }
    /**
     * 中转结算页面
     * @author huangym
     */
    public void toTransferSettlement(){
        SessionUser user = getSessionUser();

        renderJsp(path +"trans-settlement.jsp");
    }


    /**
     * 中转结算
     * @author huangym
     */
    public void transferSettlement(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");
        String[] ids = shipIds.split(",");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new PayableSvc().confirmTransferPay(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }
}
    
