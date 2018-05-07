package com.supyuan.kd.finance.flow;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.common.FeeType;
import com.supyuan.util.excel.poi.POIUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 收支流水控制类
 *
 * @author yuwen
 **/
@ControllerBind(controllerKey = "/kd/finance/flow")
public class FlowController extends BaseProjectController {
    private static final String path = "/pages/kd/finance/flow/";

    public void index() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "index.jsp");

    }

    /**
     * 流水搜索
     */
    public void search() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        FlowSearchModel model = FlowSearchModel.getBindModel(FlowSearchModel.class, getRequest());
        Page<KdFlow> page = new FlowSvc().queryFlowList(paginator, user, model);
        setAttr("model", model);
        renderJson(page);
    }

    /**
     * 收支明细页面
     */
    public void detail() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        FlowSearchModel model = FlowSearchModel.getBindModel(FlowSearchModel.class, getRequest());
        String type = getPara("type");
        if (type != null && "open".equals(type.toString())) {
            getSession().setAttribute("flowSn", model.getFlowSn());
        }

        renderJsp(path + "detail.jsp");

    }

    /**
     * 收支明细搜索
     */
    public void detailSearch() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        paginator.setPageSize(10);
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        FlowSearchModel model = FlowSearchModel.getBindModel(FlowSearchModel.class, getRequest());
        Object flowSn = getSession().getAttribute("flowSn");
        if (flowSn != null && flowSn.toString().length() > 0) {
            model.setFlowSn(flowSn.toString());
            getSession().removeAttribute("flowSn");
        }
        Page<KdFlow> page = new FlowSvc().queryFlowListDetail(paginator, user, model);
        getFeeType(page);
        setAttr("model", model);
        renderJson(page);
    }

    /**
     * 获取费用类型的方法
     */
    private void getFeeType(Page<KdFlow> load) {
        List<KdFlow> list = load.getList();
        // List<KdFlow> newList = new ArrayList<>();
        for (KdFlow kdFlow : list) {
            int feeType = kdFlow.get("fee_type");
            String feetype = "";
            switch (feeType) {
                case 1:
                    feetype = FeeType.NOWPAY.getName();
                    break;
                case 2:
                    feetype = FeeType.PICKUPPAY.getName();
                    break;
                case 3:
                    feetype = FeeType.RECEIPTPAY.getName();
                    break;
                case 4:
                    feetype = FeeType.MONTHLYPAY.getName();
                    break;
                case 5:
                    feetype = FeeType.OWE.getName();
                    break;
                case 6:
                    feetype = FeeType.LOANSBUCKLE.getName();
                    break;
                case 7:
                    feetype = FeeType.PICKUPFEE.getName();
                    break;
                case 8:
                    feetype = FeeType.DELIVERYFEE.getName();
                    break;
                case 9:
                    feetype = FeeType.DRAYAGE.getName();
                    break;
                case 10:
                    feetype = FeeType.TRANSFERFEE.getName();
                    break;
                case 11:
                    feetype = FeeType.BROKERAGE.getName();
                    break;
                case 12:
                    feetype = FeeType.NOWTRANSFEE.getName();
                    break;
                case 13:
                    feetype = FeeType.NOWOILFEE.getName();
                    break;
                case 14:
                    feetype = FeeType.BACKTRANSFEE.getName();
                    break;
                case 15:
                    feetype = FeeType.ALLSAFEFEE.getName();
                    break;
                case 16:
                    feetype = FeeType.STARTFEE.getName();
                    break;
                case 17:
                    feetype = FeeType.OTHERFEE.getName();
                    break;
                case 18:
                    feetype = FeeType.ATTRANSFEE.getName();
                    break;
                case 19:
                    feetype = FeeType.ATUNLOADFEE.getName();
                    break;
                case 20:
                    feetype = FeeType.ATOTHERFEE.getName();
                    break;
                case 21:
                    feetype = FeeType.DUANBOED.getName();
                    break;
                case 22:
                    feetype = FeeType.LOANRECOVERY.getName();
                    break;
                case 23:
                    feetype = FeeType.LOANSACCOUNT.getName();
                    break;
                case 24:
                    feetype = FeeType.LOANORIGINATION.getName();
                    break;
                case 25:
                    feetype = FeeType.CHECKFORINCOME.getName();
                    break;
                case 26:
                    feetype = FeeType.CHECKFORSPENDING.getName();
                    break;
                case 27:
                    feetype = FeeType.ABNORMALPLUS.getName();
                    break;
                case 28:
                    feetype = FeeType.ABNORMALMINUS.getName();
                    break;
                default:
                    break;
            }
            kdFlow.set("fee_type", feetype);

        }
    }

    /**
     * 下载excel收支流水列表
     */
    public void downFlow() {
        SessionUser user = getSessionSysUser();
        FlowSearchModel model = FlowSearchModel.getBindModel(FlowSearchModel.class, getRequest());
        Paginator paginator = getPaginator();
        paginator.setPageSize(100);

        List<ExcelFlow> excelList = new ArrayList();
        Page<KdFlow> page = new FlowSvc().queryFlowList(paginator, user, model);

        List<KdFlow> list = page.getList();
        int row = 1;
        String settlementType;//结算类型
        String intOutType;//收入支出类型
        String payType;//支付方式
        for (KdFlow kdflow : list) {
            ExcelFlow excel = new ExcelFlow();
            excel.setOrderNum(row);
            excel.setFlow_sn(kdflow.get("flow_sn"));
            excel.setNetwordName(kdflow.get("networdName"));
            if (kdflow.getInt("settlement_type") == 0) {
                settlementType = "应付结算";
            } else if (kdflow.getInt("settlement_type") == 1) {
                settlementType = "应收结算";
            } else if (kdflow.getInt("settlement_type") == 2) {
                settlementType = "贷款结算";
            } else {
                settlementType = "网点对账";
            }
            excel.setSettlement_type(settlementType);
            if (!kdflow.getBoolean("inout_type")) {
                intOutType = "收入";
                excel.setFee(kdflow.getDouble("fee").toString());
                excel.setIncome(kdflow.getDouble("fee").toString());
                excel.setExpenditure("");
            } else {
                intOutType = "支出";
                excel.setFee("-" + kdflow.get("fee"));
                excel.setIncome("");
                excel.setExpenditure(kdflow.get("fee").toString());
            }
            excel.setInout_type(intOutType);
            if (kdflow.getInt("pay_type") == 1) {
                payType = "现付";
            } else if (kdflow.getInt("pay_type") == 2) {
                payType = "油卡";
            } else if (kdflow.getInt("pay_type") == 3) {
                payType = "支票";
            } else if (kdflow.getInt("pay_type") == 4) {
                payType = "银行卡";
            } else if (kdflow.getInt("pay_type") == 5) {
                payType = "微信";
            } else {
                payType = "支付宝";
            }
            excel.setPay_type(payType);
            excel.setFlow_no(kdflow.get("flow_no"));
            excel.setVoucher_no(kdflow.get("voucher_no"));
            excel.setCreate_time(kdflow.getDate("create_time").toString());
            excel.setUserName(kdflow.get("userName"));
            excel.setRemark(kdflow.get("remark"));
            excelList.add(excel);
            row++;
        }

        try {
            String filename = new String("收支流水列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(excelList, "收支流水列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载excel收支明细列表
     */
    public void downFlowDetail() {
        SessionUser user = getSessionSysUser();
        FlowSearchModel model = FlowSearchModel.getBindModel(FlowSearchModel.class, getRequest());
        Paginator paginator = getPaginator();
        paginator.setPageSize(100);

        List<ExcelFlowDetail> excelList = new ArrayList();
        Page<KdFlow> page = new FlowSvc().queryFlowListDetail(paginator, user, model);
        getFeeType(page);
        List<KdFlow> list = page.getList();
        int row = 1;
        String settlementType;//结算类型
        String feeType;//费用类型
        String intOutType;//收入支出类型
        String payType;//支付方式
        for (KdFlow kdflow : list) {
            ExcelFlowDetail excel = new ExcelFlowDetail();
            excel.setOrderNum(row);
            excel.setFlow_sn(kdflow.get("flow_sn"));
            excel.setNetwordName(kdflow.get("networdName"));
            if (kdflow.getInt("settlement_type") == 0) {
                settlementType = "应付结算";
            } else if (kdflow.getInt("settlement_type") == 1) {
                settlementType = "应收结算";
            } else if (kdflow.getInt("settlement_type") == 2) {
                settlementType = "贷款结算";
            } else {
                settlementType = "网点对账";
            }
            excel.setSettlement_type(settlementType);

            excel.setFee_type(kdflow.get("fee_type"));
            if (!kdflow.getBoolean("inout_type")) {
                intOutType = "收入";
                excel.setFee(kdflow.getDouble("fee").toString());

            } else {
                intOutType = "支出";
                excel.setFee("-" + kdflow.getDouble("fee").toString());
            }
            excel.setInout_type(intOutType);
            if (kdflow.getInt("pay_type") == 1) {
                payType = "现付";
            } else if (kdflow.getInt("pay_type") == 2) {
                payType = "油卡";
            } else if (kdflow.getInt("pay_type") == 3) {
                payType = "支票";
            } else if (kdflow.getInt("pay_type") == 4) {
                payType = "银行卡";
            } else if (kdflow.getInt("pay_type") == 5) {
                payType = "微信";
            } else {
                payType = "支付宝";
            }
            excel.setPay_type(payType);
            excel.setFlow_no(kdflow.get("flow_no"));
            excel.setVoucher_no(kdflow.get("voucher_no"));
            excel.setCreate_time(kdflow.get("create_time").toString());
            excel.setUserName(kdflow.get("userName"));

            if (kdflow.getInt("id_type") == 1) {
                excel.setShip_sn(kdflow.get("ship_sn"));
                excel.setShipNetWordName(kdflow.get("shipNetWordName"));
                excel.setLoad_sn("");
                excel.setLoadNetWordName("");
            } else if (kdflow.getInt("id_type") == 2) {
                excel.setShip_sn("");
                excel.setShipNetWordName("");
                excel.setLoad_sn(kdflow.get("load_sn"));
                excel.setLoadNetWordName(kdflow.get("loadNetWordName"));
            }

            excelList.add(excel);
            row++;
        }

        try {
            String filename = new String("收支明细列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(excelList, "收支明细列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
