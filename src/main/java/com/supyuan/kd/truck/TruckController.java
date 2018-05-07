package com.supyuan.kd.truck;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.file.LibImage;
import com.supyuan.jfinal.base.EasyUIPage;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
/**
 * 运输车/货车controller
 * @author chenan
 * Date:2017年11月16日下午17:30:08 
 */
@ControllerBind(controllerKey = "/kd/truck")
public class TruckController extends BaseProjectController {
	private static final String path = "/pages/kd/truck/";

	public void index() {
		int companyId=getSessionSysUser().getCompanyId();
		
		TruckSvc truckSvc=new TruckSvc();
		
		String truck_id_number=getPara("truck_id_number")==null?"":getPara("truck_id_number");
		String truck_driver_name=getPara("truck_driver_name")==null?"":getPara("truck_driver_name");
		String truck_type=getPara("truck_type")==null?"":getPara("truck_type");
		String truck_hired_mode=getPara("truck_hired_mode")==null?"":getPara("truck_hired_mode");
		String truck_driver_mobile=getPara("truck_driver_mobile")==null?"":getPara("truck_driver_mobile");
		
		JSONObject json=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<Truck> page=truckSvc.getTruckList(paginator,truck_id_number,truck_driver_name,truck_type,truck_hired_mode,truck_driver_mobile,companyId);
		
		setAttr("page",page);
		setAttr("truck_id_number",truck_id_number);
		setAttr("truck_driver_name",truck_driver_name);
		setAttr("truck_type",truck_type);
		setAttr("truck_hired_mode",truck_hired_mode);
		setAttr("truck_driver_mobile",truck_driver_mobile);
		setAttr("msg",json);
		renderJsp(path + "index.jsp");
	}
	
	public void add(){
		String type=getPara("type")==null?"":getPara("type");
		if(type.equals("update")){
			int truckId=getParaToInt("truckId");
			Truck truck=Truck.dao.findById(truckId);
			setAttr("truck",truck);
			setAttr("type","update");
		}
		renderJsp(path + "add.jsp");
	}
	
	public void updateTruck() {
		Truck truck=getModel(Truck.class,true);
		
		try{
			
		JSONObject json = new JSONObject();
		boolean flag=truck.update();
		
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "更新成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "更新失败");
			
		}
		setSessionAttr("msg", json);
		}catch (Exception e) {
			e.printStackTrace();
	}
		redirect("/kd/truck");
		
	}
	

	 public void delTruck(){
			
			try{
				JSONObject json = new JSONObject();
				String truckId= getPara("truckId");
				if(truckId!=null&&!truckId.equals("")){
				String[] ids=truckId.split(",");
					boolean flag=false;
				for (String id : ids) {
					flag=new TruckSvc().delete(id);
				}
					
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
						renderJson(json.toString());
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
						renderJson(json.toString());
					}
				}
				setSessionAttr("msg", json);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void saveTruck() {
		String filename = getPara("filename");
		Truck truck=getModel(Truck.class,true);
		SessionUser user= getSessionUser();
		try{
			truck.set("create_time", getNow());
			truck.set("company_id", getSessionSysUser().getCompanyId());
			
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			if(StringUtils.isNotBlank(filename)){
				
				String[] names = filename.split(",");
				for (String string : names) {
					transforImage(imgs, string, gid, "车辆图片", user.getUserId());
				}
				
			}
			
			boolean flag=new TruckSvc().save(truck, imgs, gid);
			JSONObject json = new JSONObject();
		
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "新增成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "新增失败");
		}
		setSessionAttr("msg", json);
		}catch (Exception e) {
			e.printStackTrace();
	}
		redirect("/kd/truck");
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
	
	
	
	public void searchTruck() {
		SessionUser user = getSessionSysUser();
		String pageNo = getPara("page");
		String pageSize = getPara("rows");
		Paginator paginator = getPaginator();
		if(StringUtils.isNotBlank(pageNo)){
			int size = Integer.parseInt(pageSize);
			paginator.setPageSize(size>50?50:size);
		}
		if(StringUtils.isNotBlank(pageNo)){
			int no = Integer.parseInt(pageNo);
			paginator.setPageNo(no>50?50:no);
		}
		Page<Truck> truckList = new TruckSvc().getTruckGrid(paginator,getPara("queryName"),user.getCompanyId());
		renderJson(new EasyUIPage<Truck>(truckList));
	}
	
	public void checkTruckNumber(){
		String truckNumber=getPara("truckNumber");
		Truck truck=new Truck().findFirst("SELECT truck_id_number from truck where truck_id_number=? and company_id=?",truckNumber,getSessionUser().getCompanyId());
		boolean flag=false;
		if(truck==null){
			flag=true;
		}
		renderJson(flag);
	}

}
