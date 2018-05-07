package com.supyuan.kd.abnormal;




import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
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
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSearchModel;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
import com.supyuan.util.excel.poi.POIUtils;

/**
 * 运单异常控制器
 * @author chenan
 * Date:2018年1月17日下午9:41:08 
 */
@ControllerBind(controllerKey = "/kd/abnormal")
public class AbnormalController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/abnormal/";
	
	public void index() {
		SessionUser sessionUser=getSessionSysUser();
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(sessionUser);
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		
		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "index.jsp");
	}
	
	public void myabnormalIndex() {
		SessionUser sessionUser=getSessionSysUser();
		List<LogisticsNetwork> comNetworks= new NetWorkSvc().getNetWorkList(sessionUser);
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		
		setAttr("userNetworks", userNetworks);
		setAttr("comNetworks", comNetworks);
		renderJsp(path + "myabnormal.jsp");
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
		Page<KdShip> page=new KdShipSvc().getKdShipPageByStock(paginator,model,user);
		
		setAttr("page", page);
		setAttr("networks", comNetworks);
		setAttr("model",model);
		renderJsp(path + "waybill.jsp");
		
	}
	
	public void goFollowUp(){
		String abnormalId=getPara("abnormalId");
		Abnormal abnormal=new AbnormalSvc().getAbnormal(abnormalId);
		List<HashMap> handleList=new AbnormalSvc().getAbnormalHandle(abnormalId);
		List<LibImage> abnormalImg=null;
		if(StringUtils.isNotBlank(abnormal.get("abnormal_image_gid"))){
		abnormalImg = new LibImageSvc().getLibImages(abnormal.get("abnormal_image_gid"));
		}
		
		setAttr("abnormal", abnormal);
		setAttr("abnormalImg", abnormalImg);
		setAttr("handleList", handleList);
		renderJsp(path + "followup.jsp");
	}
	
	public void goAbnormalDetail(){
		String abnormalId=getPara("abnormalId");
		Abnormal abnormal=new AbnormalSvc().getAbnormal(abnormalId);
		List<HashMap> handleList=new AbnormalSvc().getAbnormalHandle(abnormalId);
		List<LibImage> abnormalImg=null;
		if(StringUtils.isNotBlank(abnormal.get("abnormal_image_gid"))){
			abnormalImg = new LibImageSvc().getLibImages(abnormal.get("abnormal_image_gid"));
		}
		
		setAttr("abnormal", abnormal);
		setAttr("abnormalImg", abnormalImg);
		setAttr("handleList", handleList);
		renderJsp(path + "abnormaldetail.jsp");
	}
	
	public void add() {
		SessionUser sessionUser=getSessionSysUser();
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		setAttr("userNetworks",userNetworks);
		renderJsp(path + "add.jsp");
	}
	
	public void addmy() {
		SessionUser sessionUser=getSessionSysUser();
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		setAttr("userNetworks",userNetworks);
		renderJsp(path + "addmy.jsp");
	}
	
	public void update(){
		
		String id=getPara("abnormalId");
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			
			if(new AbnormalSvc().update(id)){
				baseResult.setResultType(ResultType.SUCCESS);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
		
	}
	
	
	public void getAbnormalPageList(){
		SessionUser user=getSessionSysUser();
		String flag=getPara("flag")==null?"":getPara("flag");
		
		AbnormalSearchModel search=AbnormalSearchModel.getBindModel(AbnormalSearchModel.class, getRequest());
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<Abnormal> page=new AbnormalSvc().getAbnormalPageList(paginator, user,search,flag);
		
		renderJson(page);
	}
	
	
	
	
public void save(){
		
		
		String filename = getPara("filename");
		
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		SessionUser user=getSessionUser();
		try {
			Abnormal abnormal=getModel(Abnormal.class,true);
			abnormal.set("abnormal_sn", 0);
			abnormal.set("abnormal_status", 0);
			abnormal.set("user_id", user.getUserId());
			abnormal.set("create_time", getNow());
			
			SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String str = simpleDateFormat.format(date);
			
			Record record =Db.findFirst("SELECT count(1) as countId from kd_abnormal where company_id="+user.getCompanyId());
			long id=1;
			if(record.getLong("countId")==0){
				
			}else{
				id=record.getLong("countId")+1;
			}
			String maxId=id+"";
			switch (maxId.length()) {
			case 1:maxId="00"+maxId;
				break;
			case 2:maxId="0"+maxId;
				break;
			default:
				break;
			}
			String abnormalSn="YC"+str+maxId;
			abnormal.set("abnormal_sn", abnormalSn);
			abnormal.set("company_id", user.getCompanyId());
			
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			if(StringUtils.isNotBlank(filename)){
				
				String[] names = filename.split(",");
				for (String string : names) {
					transforImage(imgs, string, gid, "异常图片", user.getUserId());
				}
				
			}
			if(new AbnormalSvc().save(abnormal,imgs,gid)){
				baseResult.setResultType(ResultType.SUCCESS);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);
	}

public void saveHandle(){
	String filename = getPara("filename");
	AbnormalHandle abnormalHandle=getModel(AbnormalHandle.class,true);
	BaseResult baseResult=new BaseResult(ResultType.FAIL);
	SessionUser user=getSessionUser();
	try {
		abnormalHandle.set("create_time", getNow());
		abnormalHandle.set("user_id", user.getUserId());
		
		List<LibImage> imgs = new ArrayList<LibImage>();
		String gid = UUIDUtil.GUID();
		if(StringUtils.isNotBlank(filename)){
			
			String[] names = filename.split(",");
			for (String string : names) {
				transforImage(imgs, string, gid, "异常图片", user.getUserId());
			}
			
		}
		
		if(new AbnormalSvc().saveHandle(abnormalHandle,imgs,gid)){
				baseResult.setResultType(ResultType.SUCCESS);
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
	
	
	
	public void exportAbnormallist() {
		SessionUser user = getSessionSysUser();
		AbnormalSearchModel search=AbnormalSearchModel.getBindModel(AbnormalSearchModel.class, getRequest());
		String flag=getPara("flag")==null?"":getPara("flag");
		try {
			
			List<ExcelAbnormal> excel=new AbnormalSvc().getAbnormalExcelList(user,search,flag);
			String filename = new String("全部异常列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excel, "全部异常列表.xlsx", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	public void exportMyAbnormallist() {
		SessionUser user = getSessionSysUser();
		AbnormalSearchModel search=AbnormalSearchModel.getBindModel(AbnormalSearchModel.class, getRequest());
		String flag=getPara("flag")==null?"":getPara("flag");
		try {
			
			List<ExcelMyAbnormal> excel=new AbnormalSvc().getMyAbnormalExcelList(user,search,flag);
			String filename = new String("我的异常列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(excel, "我的异常列表.xlsx", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	

}
