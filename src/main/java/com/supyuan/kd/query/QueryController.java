package com.supyuan.kd.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Page;
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
import com.supyuan.kd.sign.KdShipSign;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.track.KdShipTrackSvc;
import com.supyuan.kd.transfer.ShipTransfer;
import com.supyuan.kd.transfer.ShipTransferSvc;
import com.supyuan.kd.transfer.TransferMentSearchModel;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
/**
 * 发车查询
 * @author dingfei
 *
 * @date 2017年11月9日 下午12:05:03
 */
@ControllerBind(controllerKey = "/kd/query")
public class QueryController extends BaseProjectController {
	
	private static final String path = "/pages/kd/query/";

	public void index() {

		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		String s = user.toNetWorkIdsStr();
		LoadQuerySearchModel model =LoadQuerySearchModel.getBindModel(LoadQuerySearchModel.class, getRequest());
		Page<KdShip> page = new QueryLoadingSvc().queryLoadingList(paginator, user, model);
		
		/*LoadListSearchModel model = LoadSearchModel.getBindModel(LoadListSearchModel.class, getRequest());
		Page<KdTrunkLoad> page = new LoadingSvc().queryTrunkLoadList(paginator, user, model);*/
		
		//获取配载网点列表
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("netWorkList", netWorkList);
		
		setAttr("page", page);
		setAttr("model",model);
		renderJsp(path + "index.jsp");
	}
	
	
	
	/**
	 * 配载查询列表
	 */
	public void loadList() {
		
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		LoadQuerySearchModel model =LoadQuerySearchModel.getBindModel(LoadQuerySearchModel.class, getRequest());
		Page<KdShip> page = new QueryLoadingSvc().queryLoadingList(paginator, user, model);
		
		
		
		renderJson(page);
	}
	
	
	
	/**
	 * 运单查询
	 * @author liangxp
	 */
	public void ship() {

		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		ShipQuerySearchModel model =ShipQuerySearchModel.getBindModel(ShipQuerySearchModel.class, getRequest());
		Page<KdShip> page = new QueryLoadingSvc().queryShipsList(paginator, user, model);
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("netWorkList", netWorkList);
		setAttr("page", page);
		setAttr("model",model);
		renderJsp(path + "ship.jsp");
	}
	
	public void shipList() {

		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		ShipQuerySearchModel model =ShipQuerySearchModel.getBindModel(ShipQuerySearchModel.class, getRequest());
		Page<KdShip> page = new QueryLoadingSvc().queryShipsList(paginator, user, model);
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("netWorkList", netWorkList);
		setAttr("model",model);
		renderJson(page);
	}
	
	
	/**
	 * 进入按中转查询
	 * @author yuwen
	 */
	public void transfer() {
		
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		//setAttr("model",model);
		setAttr("networks", networks);
		renderJsp(path + "transfer.jsp");
	}
	/**
	 * 按中转查询
	 * @author yuwen
	 */
	public void transfer1() {
		
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		
		TransferMentSearchModel search=TransferMentSearchModel.getBindModel(TransferMentSearchModel.class, getRequest());
		Page<ShipTransfer> page=new ShipTransferSvc().getShipTransferList(paginator, search, user);
		setAttr("search",search);
		setAttr("page", page);
		
		//setAttr("model",model);
		setAttr("networks", networks);
		renderJson(page);
	}
	
	/**
	 * 按运单查询的
	 * 签收图片
	 */
	public void signupload() {
		String shipId = getPara("shipId");
		if(StringUtils.isBlank(shipId)){
			renderError(500);
			return ;
		}
		KdShip ship = new KdShipSvc().findShipList(shipId);
		if(ship==null){
			renderError(500);
			return ;
		}
		KdShip kdShip = new KdShipSvc().findShipList(shipId);
		KdShipSign sign = KdShipSign.dao.findById(shipId);
		if(sign!=null){
			String gid = sign.get("sign_image_gid");
			if(StringUtils.isNotBlank(gid)){
				List<LibImage> libImages = new LibImageSvc().getLibImages(gid);
				setAttr("libImages", libImages);
			}
		}
		setAttr("ship", kdShip);
		setAttr("shipId", shipId);
		renderJsp(path + "sign-upload.jsp");
	}
	
	public void signshipimg() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			String filename = getPara("filename");
			if(StringUtils.isNotBlank(filename)){
				SessionUser user = getSessionSysUser();
				String shipId = getPara("shipId");
				List<LibImage> imgs = new ArrayList<LibImage>();
				KdShipSign sign = KdShipSign.dao.findById(shipId);
				String gid = UUIDUtil.GUID();
				if(sign!=null&&StringUtils.isNotBlank(sign.getStr("sign_image_gid"))){//之前有上传图片
					gid = sign.getStr("sign_image_gid");
				}
				String[] names = filename.split(",");
				for (String string : names) {
					transforImage(imgs, string, gid, "签收资料图片", user.getUserId());
				}
				
				if(imgs.size()>0){
					boolean res = new QueryLoadingSvc().signImg(user, gid, shipId, sign, imgs);
					if(res){
						result.setResultType(ResultType.SUCCESS);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
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
	 * 中转或配载
	 */
	public void openTransferAndStowage() {
		String shipId = getPara("shipId");
		if(StringUtils.isEmpty(shipId)){
			renderError(500);;
			return;
		}
		List<KdShip> shipList = new QueryLoadingSvc().queryLoadingByShipId(shipId);
		//KdShip ship = shipList.get(0);
		//setAttr("ship", ship);
		KdShip kdShip=new KdShipSvc().findShipList(shipId);
		
		ShipTransfer shipTransfer = new  ShipTransferSvc().getTransferByshipId(shipId);
		setAttr("shipTransfer", shipTransfer);
		
		setAttr("ship", kdShip);
		setAttr("shipList", shipList);
		/*List<KdShipTrack> tracks = new KdShipTrackSvc().findTracks(shipId);
		setAttr("tracks", tracks);*/
		renderJsp(path + "transfers.jsp");
	}
	
	/**
	 * 运单物流跟踪
	 */
	public void openTrackDiv() {
		SessionUser user = getSessionSysUser();
		String shipId = getPara("shipId");
		if(StringUtils.isEmpty(shipId)){
			renderError(500);;
			return;
		}
		//List<KdShip> shipList = new QueryLoadingSvc().queryLoadingByShipId(shipId);
		//KdShip ship = shipList.get(0);
		
		KdShip kdShip=new KdShipSvc().findShipList(shipId);
		setAttr("ship", kdShip);
		
		List<KdShipTrack> shipTrackList = new KdShipTrackSvc().findShipTrack(user, shipId);
		setAttr("shipTrackList", shipTrackList);
		
		
		renderJsp(path + "logistics.jsp");
	}
	
	/**
	 * 删除物流跟踪
	 */
	public void deleteTrack(){
		Boolean deleteFlag=false;
		String trackId = getPara("trackId");
		if(StringUtils.isEmpty(trackId)){
			renderError(500);;
			return;
		}
		Map<String, Object> map = new HashMap<>();
		
		deleteFlag = new KdShipTrackSvc().deleteTrackByTrackId(trackId);
		map.put("success", deleteFlag);
		
		renderJson(map);
	}
	
	/**
	 * 保存跟踪信息
	 */
	public void saveTrack() throws ParseException{
		SessionUser user = getSessionSysUser();
		Boolean saveFlag = false;
		String createTime = getPara("createTime");
		//SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:m");
		//Date time = sdf.parse(createTime);
		//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//createTime = sdf2.format(time);
		String trackDesc= getPara("trackDesc");
		String shipId = getPara("shipId");
		String trackVisible = getPara("trackVisible");
		
		
		Map<String, Object> map = new HashMap<>();
		
		saveFlag = new KdShipTrackSvc().saveTrack(user,createTime,trackDesc,trackVisible,shipId);
		map.put("success", saveFlag);
		
		renderJson(map);
		
	}
}
