package com.supyuan.kd.transport;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.loading.LoadListSearchModel;
import com.supyuan.kd.loading.LoadingSvc;

/**
 * 到货确认
 * 
 * @author dingfei
 *
 * @date 2017年12月14日 上午10:30:10
 */
@ControllerBind(controllerKey = "/kd/transport")
public class TransportController extends BaseProjectController {
	private static final String path = "/pages/kd/transport/";

	/**
	 * 即将到货
	 */
	public void index() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> networks=new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", networks);
		renderJsp(path + "soon.jsp");
	}

	/**
	 * 列表
	 */
	public void soonList(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if (paginator.getPageSize() > 10) {
			paginator.setPageSize(10);
		}
		if (paginator.getPageNo() < 1) {
			paginator.setPageNo(1);
		}
		LoadListSearchModel model=LoadListSearchModel.getBindModel(LoadListSearchModel.class,getRequest());
		Page<KdTrunkLoad> page=new TransportSvc().querySoonTrunkLoadList(paginator,user,model);
		renderJson(page);

	}

	/**
	 * 已到达
	 */
	public void  arrive(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> networks=new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", networks);
		renderJsp(path + "arrive.jsp");

	}
	/**
	 * 已到达列表
	 */

	public void arriveList(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if (paginator.getPageSize() > 10) {
			paginator.setPageSize(10);
		}
		if (paginator.getPageNo() < 1) {
			paginator.setPageNo(1);
		}
		LoadListSearchModel model=LoadListSearchModel.getBindModel(LoadListSearchModel.class,getRequest());
		Page<KdTrunkLoad> page=new TransportSvc().queryConfirmTrunkLoadList(paginator,user,model);
		renderJson(page);
	}

	/**
	 * 确认到达
	 */
	public void confirmArrive(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		int loadId = getParaToInt("loadId");
		int loadType=getParaToInt("loadType");
		if(loadId>0){
			boolean flag=new TransportSvc().saveTruckLoad(loadId,loadType,user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}else{
				baseResult.setResultType(ResultType.FAIL);
			}
		}
		renderJson(baseResult);

	}

    /**
     * 完成卸车页面
     */
	public void unLoading (){
        SessionUser user = getSessionSysUser();
        int loadId = getParaToInt("loadId");
        int loadType=getParaToInt("loadType");
        KdTrunkLoad load=new LoadingSvc().queryKdTrunkLoad(loadId,user);
        setAttr("load",load);
        if (loadType==2){
            renderJsp(path+"duanbounload.jsp");
        }else{
            renderJsp(path+"ganxianunload.jsp");
        }
    }

	/**
	 * 完成卸车确定
	 */
    public void confirmUnloading(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		int loadId = getParaToInt("loadId");
		int loadType=getParaToInt("loadType");
		String atunloadFee=getPara("atunloadFee")!=null?getPara("atunloadFee"):"0";
		String atotherFee=getPara("atotherFee")!=null?getPara("atotherFee"):"0";
			boolean flag = new TransportSvc().updateUnloading(loadId,loadType,new Double(atunloadFee), new Double(atotherFee), user);
			if(flag){
				baseResult.setResultType(ResultType.SUCCESS);
			}else{
				baseResult.setResultType(ResultType.FAIL);
			}
		renderJson(baseResult);
	}

}
