package com.supyuan.kd.loading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.truck.Truck;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
import com.supyuan.util.excel.poi.POIUtils;
/**
 * 开单--装载
 * @author liangxp
 *
 * Date:2017年11月8日上午10:04:14 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/loading")
public class LoadingController extends BaseProjectController {

	private static final String path = "/pages/kd/loading/";
	
	private static final String LOADSN = "loadSn";
	
	private static final String SPLIT = "_";
	
	private static final String LOADSN_PRIFIX = "PZ";
	

	public void index() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("allnetWorks", allnetWorks);
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		renderJsp(path + "loading-index.jsp");
	}
	
	
	public void tihuo() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("allnetWorks", allnetWorks);
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		renderJsp(path + "loading-delivery.jsp");
	}
	
	
	public void duanbo() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("allnetWorks", allnetWorks);
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		renderJsp(path + "loading-refutation.jsp");
	}
	
	
	public void songhuo() {
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("allnetWorks", allnetWorks);
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		renderJsp(path + "loading-deliverys.jsp");
	}
	
	
	
	/**
	 * 装载搜索页面
	 * @author liangxp
	 */
	public void search() {
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10000);
		LoadSearchModel model = LoadSearchModel.getBindModel(LoadSearchModel.class, getRequest());
		Page<KdShip> ships = new LoadingSvc().queryShipList(paginator, user, model);
		renderJson(ships);
	}
	
	
	
	/**
	 * 配载详情页面
	 * @author liangxp
	 */
	public void loadingView() {
		SessionUser user = getSessionSysUser();
    	String loadId = getPara("loadId");
    	if(StringUtils.isNotBlank(loadId)){
    		KdTrunkLoad load = new LoadingSvc().queryKdTrunkLoad(Integer.parseInt(loadId), user);
    		List<KdShip> shipList = new LoadingSvc().queryLoadShipList(Integer.parseInt(loadId), user);
    		setAttr("load", load);
    		setAttr("ships", shipList);
    	}else{
    		renderError(404);
    		return;
    	}
		renderJsp(path + "loadingview.jsp");
	}
	

	/**
	 * 配载单列表
	 * @author liangxp
	 */
	public void loadlist() {
		SessionUser user = getSessionSysUser();
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		List<LogisticsNetwork> tonetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("tonetWorks", tonetWorks);
		renderJsp(path + "loadlist.jsp");
	}
	
	/**
	 * 更换司机信息
	 * @author liangxp
	 */
	public void loadchange() {
		String loadId = getPara("loadId");
		SessionUser user = getSessionSysUser();
		if(StringUtils.isNotBlank(loadId)){
			KdTrunkLoad load = new LoadingSvc().queryKdTrunkLoad(Integer.parseInt(loadId), user);
			if(load!=null){
				Truck truck = Truck.dao.findById(load.getInt("truck_id"));
				setAttr("truck", truck);
				setAttr("load", load);
			}
		}
		renderJsp(path + "loadlist-change.jsp");
	}
	/**
	 * 保存更新的司机信息
	 * @author liangxp
	 */
	public void changetruck() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			int loadId = getParaToInt("loadId");
			String loadTime = getPara("load_depart_time");
			Truck truck = getModel(Truck.class, null, true);
			truck.set("create_time", new Date());
			boolean changetruck = new LoadingSvc().changetruck(loadId,loadTime, truck, user);
			if(changetruck){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	
	/**
	 * 更换到货网点
	 * @author liangxp
	 */
	public void loadnetwork() {
		String loadId = getPara("loadId");
		SessionUser user = getSessionSysUser();
		if(StringUtils.isNotBlank(loadId)){
			 KdTrunkLoad load = new LoadingSvc().queryTrunkLoad(Integer.parseInt(loadId), user);
			 setAttr("load", load);
			 List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkList(user);
			 setAttr("networks", netWorkList);
			 setAttr("loadId", loadId);
		}
		renderJsp(path + "loadlist-network.jsp");
	}
	/**
	 * 保存更新的网点信息
	 * @author liangxp
	 */
	public void changenetwork() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			int loadId = getParaToInt("loadId");
			int networkId = getParaToInt("load_next_network_id");
			String endCode = getPara("load_delivery_to");
			if(loadId>0&&StringUtils.isNotBlank(endCode)){
				boolean change = new LoadingSvc().changenetwork(loadId, networkId,  endCode, user);
				if(change){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 删除配载单
	 * @author liangxp
	 */
	public void removeload() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String loadIds = getPara("loadIds");
			Long anum = Db.queryLong("select count(*) from kd_truck_load t  where t.load_id in (?) and t.load_delivery_status=2", loadIds);
			if(anum>0){//已到达的配载单不能删除
				baseResult.setResultType(ResultType.SHIP_IS_ARRIVAL_ERROR);
				renderJson(baseResult);
				return;
			}
			Long num = Db.queryLong("select count(*) from kd_ship s left join kd_truck_ship ts on s.ship_id=ts.ship_id left join kd_truck_load t on t.load_id=ts.truck_load_id where t.load_id in (?) and s.ship_status=9", loadIds);
			if(num>0){//包含签收的运单，配载单不能删除
				baseResult.setResultType(ResultType.SHIP_IS_SIGN_ERROR);
				renderJson(baseResult);
				return;
			}
			boolean res = new LoadingSvc().removeLoad(loadIds, user);
			if(res){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	
	public void searchLoadlist() {
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		LoadListSearchModel model = LoadSearchModel.getBindModel(LoadListSearchModel.class, getRequest());
		Page<KdTrunkLoad> loads = new LoadingSvc().queryTrunkLoadList(paginator, user, model);
		renderJson(loads);
	}
	
	
	/**
	 * 导出配载单列表
	 * @author liangxp
	 */
	public void exportLoadlist() {
		SessionUser user = getSessionSysUser();
		LoadListSearchModel model = LoadSearchModel.getBindModel(LoadListSearchModel.class, getRequest());
		try {
			 List<LoadListModel> exportTrunkLoadList = new LoadingSvc().exportTrunkLoadList(user, model);
			String filename = new String("发车列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(exportTrunkLoadList, "发车列表.xlsx", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	/**
	 * 配载上传司机图
	 * @author liangxp
	 */
	public void loadattachment() {
		String loadId = getPara("loadId");
		KdTrunkLoad load = KdTrunkLoad.dao.findById(Integer.parseInt(loadId));
		if(load!=null){
			String gid = load.get("load_image_gid");
			List<LibImage> libImages = new LibImageSvc().getLibImages(gid);
			setAttr("libImages", libImages);
		}
		setAttr("loadId", loadId);
		renderJsp(path + "loadlist-upload.jsp");
	}
	
	public void attachmentsave() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String loadId = getPara("loadId");
			String filename = getPara("filename");
			if(StringUtils.isNotBlank(filename)){
				List<LibImage> imgs = new ArrayList<LibImage>();
				KdTrunkLoad load = KdTrunkLoad.dao.findById(Integer.parseInt(loadId));
				if(load==null){
					renderJson(baseResult);
					return;
				}
				String gid = load.get("load_image_gid");
				KdTrunkLoad temp = null;
				if(!StringUtils.isNotBlank(gid)){
					gid = UUIDUtil.GUID();
					temp = new KdTrunkLoad();
					temp.set("load_id", Integer.parseInt(loadId));
					temp.set("load_image_gid", gid);
				}
				String[] names = filename.split(",");
				for (String string : names) {
					transforImage(imgs, string, gid, "车辆资料图片", user.getUserId());
				}
				
				boolean res = new LoadingSvc().attachmentsave(temp, imgs);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 装载
	 * @author liangxp
	 */
	public void up() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			KdTrunkLoad load = getModel(KdTrunkLoad.class, null, true);
			
			KdLoadGxFee loadGxFee = null;
			Integer transType = getParaToInt("load_transport_type");
			if(transType==3){//干线其他费
				loadGxFee = getModel(KdLoadGxFee.class, null, true);
			}
			Truck truck = getModel(Truck.class, null, true);
			String ids = getPara("ids");
			if(StringUtils.isNoneBlank(ids)){
				load.set("load_sn", createLoadSn(user.getCompanyId()));
				boolean res = new LoadingSvc().loading(user, loadGxFee, load, ids, truck);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 发车
	 * @author liangxp
	 */
	public void send() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String ids = getPara("ids");
			if(StringUtils.isNoneBlank(ids)){
				boolean res = new LoadingSvc().sendLoad(ids, user);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 提货完成
	 * @author liangxp
	 */
	public void tihuoOver() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String ids = getPara("ids");
			if(StringUtils.isNoneBlank(ids)){
				boolean res = new LoadingSvc().loadTihuoOver(ids, user);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 送货完成
	 * @author liangxp
	 */
	public void songhuoOver() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String ids = getPara("ids");
			if(StringUtils.isNoneBlank(ids)){
				boolean res = new LoadingSvc().loadSonghuoOver(ids, user);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	
	public void modifyFee() {
		String loadId = getPara("loadId");
		if(StringUtils.isBlank(loadId)){
			redirect("404");
			return ;
		}
		SessionUser user = getSessionSysUser();
		KdTrunkLoad load = new LoadingSvc().queryTrunkLoad(Integer.parseInt(loadId), user);
		if(load!=null){
			int transtype = load.getInt("load_transport_type");
			setAttr("load", load);
			if(transtype==1){
				renderJsp(path + "modify-tihuo-fee.jsp");
				return ;
			}else if(transtype==2){
				renderJsp(path + "modify-duanbo-fee.jsp");
				return ;
			}else if(transtype==3){
				renderJsp(path + "modify-ganxian-fee.jsp");
				return ;
			}else if(transtype==4){
				renderJsp(path + "modify-songhuo-fee.jsp");
				return ;
			}
		}else{
			redirect("404");
			return ;
		}
	}
	
	
	public void comfirmModifyFee() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			int type = getParaToInt("type");
			double fee = Double.parseDouble(getPara("loadfee"));
			int loadId = getParaToInt("loadId");
			if(type==3){//干线
				KdLoadGxFee gxfee = KdLoadGxFee.dao.findFirst("select * from kd_load_gx_fee where load_id=?", loadId);
				if(gxfee==null){
					renderJson(baseResult);
					return;
				}
				KdLoadGxFee loadfee = getModel(KdLoadGxFee.class, null, true);
				if(loadfee.get("load_nowtrans_fee")!=null&&gxfee.get("load_nowtrans_fee")!=null&&gxfee.getDouble("load_nowtrans_fee")!=loadfee.getDouble("load_nowtrans_fee")&&gxfee.getBoolean("load_nowtrans_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				if(loadfee.get("load_nowoil_fee")!=null&&gxfee.get("load_nowoil_fee")!=null&&gxfee.getDouble("load_nowoil_fee")!=loadfee.getDouble("load_nowoil_fee")&&gxfee.getBoolean("load_nowoil_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				if(loadfee.get("load_backtrans_fee")!=null&&gxfee.get("load_backtrans_fee")!=null&&gxfee.getDouble("load_backtrans_fee")!=loadfee.getDouble("load_backtrans_fee")&&gxfee.getBoolean("load_backtrans_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				if(loadfee.get("load_attrans_fee")!=null&&gxfee.get("load_attrans_fee")!=null&&gxfee.getDouble("load_attrans_fee")!=loadfee.getDouble("load_attrans_fee")&&gxfee.getBoolean("load_attrans_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				if(loadfee.get("load_allsafe_fee")!=null&&gxfee.get("load_allsafe_fee")!=null&&gxfee.getDouble("load_allsafe_fee")!=loadfee.getDouble("load_allsafe_fee")&&gxfee.getBoolean("load_allsafe_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				if(loadfee.get("load_start_fee")!=null&&gxfee.get("load_start_fee")!=null&&gxfee.getDouble("load_start_fee")!=loadfee.getDouble("load_start_fee")&&gxfee.getBoolean("load_start_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				if(loadfee.get("load_other_fee")!=null&&gxfee.get("load_other_fee")!=null&&gxfee.getDouble("load_other_fee")!=loadfee.getDouble("load_other_fee")&&gxfee.getBoolean("load_other_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				fee += gxfee.get("load_nowtrans_fee")!=null?0:loadfee.getDouble("load_nowtrans_fee");
				fee += gxfee.get("load_nowoil_fee")!=null?0:loadfee.getDouble("load_nowoil_fee");
				fee += gxfee.get("load_backtrans_fee")!=null?0:loadfee.getDouble("load_backtrans_fee");
				fee += gxfee.get("load_attrans_fee")!=null?0:loadfee.getDouble("load_attrans_fee");
				fee += gxfee.get("load_allsafe_fee")!=null?0:loadfee.getDouble("load_allsafe_fee");
				fee += gxfee.get("load_start_fee")!=null?0:loadfee.getDouble("load_start_fee");
				fee += gxfee.get("load_other_fee")!=null?0:loadfee.getDouble("load_other_fee");
				boolean res = new LoadingSvc().modifyGxFee(loadfee, loadId, fee);
				if(res)baseResult.setResultType(ResultType.SUCCESS);
			}else{
				KdTrunkLoad load = KdTrunkLoad.dao.findById(loadId);
				if(load==null){
					renderJson(baseResult);
					return;
				}
				if(load.getDouble("load_fee")!=fee&&load.getBoolean("load_fee_fill")){
					baseResult.setResultType(ResultType.FEE_IS_SETTLE_ERROR);
					renderJson(baseResult);
					return;
				}
				boolean res = new LoadingSvc().modifyFee(loadId, fee);
				if(res)baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	/**
	 * 增加运单
	 * @author liangxp
	 */
	public void add() {
		String loadId = getPara("loadId");
		if(StringUtils.isBlank(loadId)){
			redirect("/kd/loading");
			return ;
		}
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", netWorkList);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("allnetWorks", allnetWorks);
		
		LoadingSvc loadingSvc = new LoadingSvc();
		KdTrunkLoad load = loadingSvc.queryTrunkLoad(Integer.parseInt(loadId), user);
		if(load!=null){
			setAttr("load", load);
		}else{
			redirect("/kd/loading");
			return ;
		}
		renderJsp(path + "vehicles.jsp");
	}
	
	
	
	/**
	 * 减少运单
	 * @author liangxp
	 */
	public void reduce() {
		String loadId = getPara("loadId");
		if(StringUtils.isBlank(loadId)){
			redirect("/kd/loading");
			return ;
		}
		SessionUser user = getSessionSysUser();
		KdTrunkLoad load = new LoadingSvc().queryTrunkLoad(Integer.parseInt(loadId), user);
		if(load!=null){
			setAttr("load", load);
		}else{
			redirect("/kd/loading");
			return ;
		}
		renderJsp(path + "simple.jsp");
	}
	
	/**
	 * 运单列表
	 * @author liangxp
	 */
	public void loadshiplist() {
		String loadId = getPara("loadId");
		if(StringUtils.isBlank(loadId)){
			redirect("/kd/loading");
			return ;
		}
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		Page<KdShip> loads = new LoadingSvc().queryLoadShips(paginator, Integer.parseInt(loadId), user);
		renderJson(loads);
	}
	
	/**
	 * 确定减少运单
	 * @author liangxp
	 */
	public void comfirmreduce() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String loadId = getPara("loadId");
			String shipIds = getPara("shipIds");
			if(StringUtils.isNotBlank(loadId)&&StringUtils.isNotBlank(shipIds)){
				Integer count = Db.queryInt("select load_count from kd_truck_load t where t.load_id=?", loadId);
				String[] ids = shipIds.split(",");
				if(count==ids.length){//配载的运单不能全部删减
					baseResult.setResultType(ResultType.SHIP_IS_DELETE_ERROR);
					renderJson(baseResult);
					return;
				}
				
				boolean res = new LoadingSvc().reduce(Integer.parseInt(loadId), shipIds, user);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	
	/**
	 * 确定增加运单
	 * @author liangxp
	 */
	public void comfirmadd() {
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		try {
			String loadId = getPara("loadId");
			String shipIds = getPara("shipIds");
			if(StringUtils.isNotBlank(loadId)&&StringUtils.isNotBlank(shipIds)){
				boolean res = new LoadingSvc().add(Integer.parseInt(loadId), shipIds, user);
				if(res){
					baseResult.setResultType(ResultType.SUCCESS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}
	
	
	
	private void transforImage(List<LibImage> imgs, String image, String gid, String tag, Integer uid){
		if(StringUtils.isBlank(image)){
			return;
		}
		LibImage img2 = new LibImage();
		img2.set("uuid", UUIDUtil.UUID());
		img2.set("image", image.replace("http://" + Config.getStr("IMAGE.DOMAIN_PREFIX"), ""));
		img2.set("gid", gid);
		img2.set("tag", tag);
		img2.set("host", Config.getStr("IMAGE.DOMAIN_PREFIX"));
		img2.set("status", 1);
		img2.set("create_time", DateUtils.get11CurrrTime());
		img2.set("user_id", uid);
		imgs.add(img2);
	}
	
	
	
	/**
	 * 生成配载单
	 * @author liangxp
	 * @param companyId
	 * @return
	 */
	private String createLoadSn(int companyId){
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD);
		String beforeTime = DateUtils.getBeforeTime(DateUtils.DEFAULT_REGEX_YYYYMMDD);
		String value = CacheKit.get(LOADSN, companyId+SPLIT+now);
		if(StringUtils.isBlank(value)){
			int beforeValue = getBeforeLoadSn(now, companyId);
			if(beforeValue==0){
				value = CacheKit.get(LOADSN, companyId+SPLIT+beforeTime);
				if(StringUtils.isBlank(value)){
					CacheKit.remove(LOADSN, companyId+SPLIT+beforeTime);
					CacheKit.put(LOADSN, companyId+SPLIT+now, "1");
					return  LOADSN_PRIFIX + now +"0001";
				}
			}else{
				value = beforeValue + "";
			}
		}
		int intValue = Integer.parseInt(value);
		++intValue;
		if(intValue<10){
			value =  LOADSN_PRIFIX + now + "000" + intValue;
		}else if(intValue>=10&&intValue<100){
			value =  LOADSN_PRIFIX + now + "00" + intValue;
		}else if(intValue>=100&&intValue<1000){
			value =  LOADSN_PRIFIX + now + "0" + intValue;
		}else if(intValue>=1000&&intValue<10000){
			value =  LOADSN_PRIFIX + now + intValue;
		}else{
			value =  LOADSN_PRIFIX + now + intValue;
		}
		CacheKit.put(LOADSN, companyId+SPLIT+now, intValue+"");
		return value;
	}
	
	
	private int getBeforeLoadSn(String curr, int companyId){
		KdTrunkLoad find = KdTrunkLoad.dao.findFirst("select load_sn from kd_truck_load where company_id=? ORDER BY load_id desc limit 1 ", companyId);
		if(find!=null){
			String loadsn = find.getStr("load_sn");
			String time = loadsn.substring(2, 10);
			if(time.equals(curr)){
				return Integer.parseInt(loadsn.substring(10));
			}
		}
		return 0;
	}
	
}
