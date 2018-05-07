package com.supyuan.kd.finance.abnormal;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.common.FeeType;
import com.supyuan.kd.finance.flow.*;
import com.supyuan.kd.finance.payable.ExcelPayRebate;
import com.supyuan.kd.finance.payable.PayRebateSearchModel;
import com.supyuan.kd.finance.payable.PayableSvc;
import com.supyuan.kd.finance.receivable.ReceivableSvc;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSearchModel;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.system.line.SysLine;
import com.supyuan.util.excel.poi.POIUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 异动管理控制类
 *
 * @author yuwen
 **/
@ControllerBind(controllerKey = "/kd/finance/abnormal")
public class ShipAbnormalController extends BaseProjectController {
    private static final String path = "/pages/kd/finance/abnormal/";

    public void index() {
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        renderJsp(path + "index.jsp");

    }

    /**
     * 异动搜索
     */
    public void search() {
        SessionUser user = getSessionSysUser();
        Paginator paginator = getPaginator();
        String flag = getPara("type");
        if ("all".equals(flag)){
            paginator.setPageSize(1000);
        }else {
            paginator.setPageSize(10);

        }
        ShipAbnormalSearchModel model = FlowSearchModel.getBindModel(ShipAbnormalSearchModel.class, getRequest());
        Page<KdShipAbnormal> page = new KdShipAbnormalSvc().getKdShipAbnormalPage(paginator,model,user,false,flag);
        renderJson(page);
    }

    /**
     * @author huangym
     * 异动新增页面
     */
    public void toSavePage(){
        SessionUser user = getSessionSysUser();
        List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
        setAttr("userNetworks",userNetworks);
        renderJsp(path + "add.jsp");
    }

    /**
     * 根据运单id获取开单网点和到货网点
     */
    public void getNetWorkByShipId(){
        SessionUser user = getSessionSysUser();
        String shipId=getPara("shipId");
        List<LogisticsNetwork> nets = new NetWorkSvc().getNetWorkByShipId(shipId);
        renderJson(nets);
    }

    /**
     * @author huangym
     * 异动新增
     */
    public void save(){
        JSONObject result = new JSONObject();
        SessionUser user = getSessionSysUser();

        String ship_id = getPara("ship_id");
        String plus_fee = getPara("plus_fee");
        String minus_fee = getPara("minus_fee");
        String network_id = getPara("network_id");
        String cause = getPara("cause");
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (plus_fee == null) plus_fee = "0";
        if (minus_fee == null) minus_fee = "0";

        KdShipAbnormal first = KdShipAbnormal.dao.findFirst("select id from kd_ship_abnormal where ship_id = ?", ship_id);
        if(first != null) {
            result.put("msg", "一个运单只能记录一次异动");
            result.put("state", "fail");
            renderJson(result);
            return;
        }else{
            boolean flag = new KdShipAbnormalSvc().saveShipAbnormal(user, ship_id, plus_fee, minus_fee, network_id, cause, time);

            if(flag){
                result.put("msg", "保存成功");
                result.put("state", "success");
                renderJson(result);
                return;
            }
        }
        result.put("msg", "保存失败");
        result.put("state", "fail");
        renderJson(result);

    }


    /**
     * 进入异动修改页面
     */
    public void update() {
        ShipAbnormalSearchModel model = FlowSearchModel.getBindModel(ShipAbnormalSearchModel.class, getRequest());
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkByShipId(model.getId());
        setAttr("networks", networks);
        KdShipAbnormal kdShipAbnormal = new KdShipAbnormalSvc().getKdShipAbnormalByShipId(model.getId());
        setAttr("KdShipAbnormal",kdShipAbnormal);
        renderJsp(path + "update.jsp");
    }

    /**
     * 异动修改
     */
    public void updateAbnormal(){
        JSONObject json = new JSONObject();
        try {
            int id = Integer.parseInt(getPara("id"));
            String networkId = getPara("networkId");
            String plus_fee = getPara("plus_fee");
            String minus_fee = getPara("minus_fee");
            String cause = getPara("cause");
            int flag =  Db.update("update kd_ship_abnormal set network_id = ?,plus_fee = ?,minus_fee = ?,cause= ?  where id = ?",networkId,plus_fee,minus_fee,cause,id);

            if(flag==1){
                json.put("state", "SUCCESS");
                json.put("msg", "修改成功");
            }else{
                json.put("state", "FAILED");
                json.put("msg", "修改失败");

            }
            renderJson(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 进入异动删除页面
     */
    public void delete() {
        ShipAbnormalSearchModel model = FlowSearchModel.getBindModel(ShipAbnormalSearchModel.class, getRequest());
        KdShipAbnormal kdShipAbnormal = new KdShipAbnormalSvc().getKdShipAbnormalByShipId(model.getId());
        setAttr("KdShipAbnormal",kdShipAbnormal);
        renderJsp(path + "delete.jsp");
    }


    /**
     * 异动删除
     */
    public void deleteAbnormal(){
        JSONObject json = new JSONObject();
        try {
            int ship_id = Integer.parseInt(getPara("ship_id"));
            int id = Integer.parseInt(getPara("id"));

            boolean flag = new KdShipAbnormalSvc().deleteShipAbnormal(ship_id ,id);
            if(flag){
                json.put("state", "SUCCESS");
                json.put("msg", "删除成功");
            }else{
                json.put("state", "FAILED");
                json.put("msg", "删除失败");

            }
            renderJson(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 导出excel列表
     * @author huangym
     */
    public void exportShipAbnormalExcel() {
        SessionUser user = getSessionUser();
        ShipAbnormalSearchModel search=ShipAbnormalSearchModel.getBindModel(ShipAbnormalSearchModel.class, getRequest());


        List<ExcelShipAbnormal> excelList = new ArrayList();

        Page<KdShipAbnormal> page = new KdShipAbnormalSvc().getKdShipAbnormalPage(null, search, user, true,"");
        renderJson(page);

        List<KdShipAbnormal> list = page.getList();
        int i = 1;
        for (KdShipAbnormal shipAbnormal : list) {
            ExcelShipAbnormal excel = new ExcelShipAbnormal();
            excel.setShipAbnormalNum(i);
            excel.setShipSn(shipAbnormal.get("ship_sn"));
            excel.setNetworkName(shipAbnormal.get("netWorkName"));
            excel.setAbnormalNetWorkName(shipAbnormal.get("abnormalNetWorkName"));
            excel.setGoodsSn(shipAbnormal.get("goods_sn").toString());


            String shipState = "";
            if ("1".equals(shipAbnormal.get("ship_status").toString())) shipState = "已入库";
            else if ("2".equals(shipAbnormal.get("ship_status").toString())) shipState = "短驳中";
            else if ("3".equals(shipAbnormal.get("ship_status").toString())) shipState = "短驳到达";
            else if ("4".equals(shipAbnormal.get("ship_status").toString())) shipState = "已发车";
            else if ("5".equals(shipAbnormal.get("ship_status").toString())) shipState = "已到达";
            else if ("6".equals(shipAbnormal.get("ship_status").toString())) shipState = "收货中转中";
            else if ("7".equals(shipAbnormal.get("ship_status").toString())) shipState = "到货中转中";
            else if ("8".equals(shipAbnormal.get("ship_status").toString())) shipState = "送货中";
            else if ("9".equals(shipAbnormal.get("ship_status").toString())) shipState = "已签收";
            excel.setShipStatus(shipState);

            excel.setPlusFee(shipAbnormal.get("plus_fee").toString());
            excel.setMinusFee(shipAbnormal.get("minus_fee").toString());

            String feeState = "未结算";
            if (shipAbnormal.get("fee_status") != null) if (shipAbnormal.get("fee_status").toString().equals("1")) feeState = "已结算";
            excel.setFeeStatus(feeState);

            excel.setCreateTime(shipAbnormal.get("create_time").toString());
            excel.setToAdd(shipAbnormal.get("toAdd"));
            excel.setFromAdd(shipAbnormal.get("fromAdd"));
            excel.setSenderName(shipAbnormal.get("senderName"));
            excel.setReceiverName(shipAbnormal.get("receiverName"));

            excelList.add(excel);
            i++;
        }

        try {
            String filename = new String("异动管理列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
            HttpServletResponse response = getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            POIUtils.generateXlsxExcelStream(excelList, "异动管理列表", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 进入异动结算页面
     */
    public void settlement() {
        SessionUser user = getSessionSysUser();
        ShipAbnormalSearchModel model = FlowSearchModel.getBindModel(ShipAbnormalSearchModel.class, getRequest());
        List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkList(user);
        setAttr("networks", networks);
        setAttr("type","settlement");
        renderJsp(path + "abnormalsettlement.jsp");
    }


    /**
     * 异动结算
     */
    public void abnormalSettle(){
        SessionUser user=getSessionSysUser();
        String payType=getPara("payType");
        String flowNo=getPara("flowNo");
        String voucherNo=getPara("voucherNo");
        String remark=getPara("remark");
        String shipIds=getPara("shipIds");

        BaseResult baseResult=new BaseResult(ResultType.FAIL);
        try {
            boolean flag= new KdShipAbnormalSvc().AbnormalSettle(shipIds,payType,flowNo,voucherNo,remark,getNow(),user);
            if(flag){
                baseResult.setResultType(ResultType.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderJson(baseResult);
    }


    public void chooseShip(){

        SessionUser user = getSessionSysUser();

        List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(user);

        Paginator paginator = getPaginator();
        paginator.setPageSize(10);
        if (paginator.getPageSize() > 10) {
            paginator.setPageSize(10);
        }
        if (paginator.getPageNo() < 1) {
            paginator.setPageNo(1);
        }
        KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
        Page<KdShip> page=new KdShipAbnormalSvc().getKdShipPageByStock(paginator,model,user);

        setAttr("page", page);
        setAttr("networks", comNetworks);
        setAttr("model",model);
        renderJsp(path + "waybill.jsp");

    }


}
