package com.supyuan.kd.finance.collect;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.finance.flow.KdFlow;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.excel.poi.POIUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代收货款
 *
 * @author dingfei
 * @create 2017-12-25 15:03
 **/
@ControllerBind(controllerKey = "/kd/finance/collect")
public class CollectController extends BaseProjectController {
    private static final String path = "/pages/kd/finance/collect/";

    /**
     * 我的货款
     */
    public void index() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "index.jsp");
    }

    /**
     * 我的贷款列表
     */
    public void search() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryMyLoanList(paginator, user, model);
        renderJson(ships);
    }

    /**
     * 代收货款
     */
    public void collectLoan() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "collect.jsp");
    }

    /**
     * 代收货款列表
     */
    public void collectList() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryCollectList(paginator, user, model);
        renderJson(ships);
    }

    /**
     * 货款到账
     */
    public void moneyAccount() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "hkdz.jsp");

    }

    /**
     * 货款到账列表
     */
    public void getmoneyAccountList() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryAccountList(paginator, user, model);
        renderJson(ships);
    }

    /**
     * 货款发放
     */
    public void moneyGrant() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "hkff.jsp");

    }

    /**
     * 货款发放列表
     */
    public void getmoneyGrantList() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryGrantList(paginator, user, model);
        renderJson(ships);
    }

    /**
     * 货款回收
     */
    public void  moneyCallBack(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "hkhs.jsp");

    }

    /**
     * 货款回收列表
     */
    public void getmoneyCallBackList(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryCallBackList(paginator, user, model);
        renderJson(ships);
    }

    /**
     * 货款汇款
     */
    public void moneyRemittance(){

        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "hkhk.jsp");

    }

    /**
     * 货款汇款列表
     */
    public void getmoneyRemittanceList(){
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Page<KdShip> ships = new CollectSvc().queryRemittanceList(paginator, user, model);
        renderJson(ships);

    }



    /**
     * 代收货款确认按钮
     */
    public void saveShipFlow() {
        BaseResult baseResult = new BaseResult(ResultType.FAIL);
        SessionUser user = getSessionSysUser();
        int payType = getParaToInt("payType");
        String flowNo = getPara("flowNo");
        String voucherNo = getPara("voucherNo");
        String remark = getPara("remark");
        int type=getParaToInt("type");
        try {
            String ids = getPara("ids");
            boolean res = new CollectSvc().saveShipFlow(user, ids, payType, flowNo, voucherNo, remark,type);
            if (res) {
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
    public void downLoad() {
        SessionUser user = getSessionSysUser();
        CollectSearchModel model = CollectSearchModel.getBindModel(CollectSearchModel.class, getRequest());
        Paginator paginator = getPaginator();
        paginator.setPageSize(100);

        List<ExcelCollect> excelList = new ArrayList();

        Page<KdShip> page = new CollectSvc().queryCollectList(paginator, user, model);

        List<KdShip> list = page.getList();
        int row = 1;
        String fundStatus;//货款状态
        String shipState;//运单状态
        for (KdShip kdShip : list) {
            ExcelCollect excel = new ExcelCollect();
            excel.setRowNum(row);
            excel.setShipSn(kdShip.get("ship_sn"));
            excel.setNetWorkName(kdShip.get("netWrokName"));
            if (kdShip.getInt("ship_agency_fund_status") == 0) {
                fundStatus = "正常";
            } else if (kdShip.getInt("ship_agency_fund_status") == 1) {
                fundStatus = "已代收";
            } else if (kdShip.getInt("ship_agency_fund_status") == 2) {
                fundStatus = "已回收";
            } else {
                fundStatus = "已返款";
            }
            excel.setAgencyFundStatus(fundStatus);
            excel.setAgencyFund(kdShip.getDouble("ship_agency_fund"));
            excel.setAgencyWorkName(kdShip.get("endWorkName"));
            if (kdShip.getInt("ship_status") == 1) {
                shipState = "出库中";
            } else if (kdShip.getInt("ship_status") == 2) {
                shipState = "运输中";
            } else if (kdShip.getInt("ship_status") == 3) {
                shipState = "已中转";
            } else {
                shipState = "已签收";
            }
            excel.setShipStatus(shipState);
            excel.setCreateTime(kdShip.get("create_time").toString());
            excel.setSenderName(kdShip.get("senderName"));
            excel.setReceiverName(kdShip.get("receiverName"));
            excel.setFromAdd(kdShip.get("fromAdd"));
            excel.setToAdd(kdShip.get("toAdd"));
            excel.setVolume(kdShip.getDouble("ship_volume"));
            excel.setWeight(kdShip.getDouble("ship_weight"));
            excel.setAmount(kdShip.getInt("ship_amount"));
            excelList.add(excel);
            row++;
        }

        try {
            String filename = new String("代收货款列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(excelList, "代收货款列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
