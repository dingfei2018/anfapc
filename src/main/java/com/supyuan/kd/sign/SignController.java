package com.supyuan.kd.sign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.file.LibImage;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;
import com.supyuan.util.excel.poi.POIUtils;
import com.supyuan.util.excel.poi.User;
/**
 * 开单--签单
 * @author liangxp
 *
 * Date:2017年11月8日上午10:05:20 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/sign")
public class SignController extends BaseProjectController {

	private static final String path = "/pages/kd/sign/";

	public void index() {
		renderJsp(path + "index.jsp");
	}
	
	
	/**
	 * 签收搜索页面
	 * @author liangxp
	 */
	public void search() {
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		SignSearchModel model = SignSearchModel.getBindModel(SignSearchModel.class, getRequest());
		Page<KdShip> ships = new SignSvc().queryShipList(paginator, user, model);
		renderJson(ships);
	}
	
	
	
	
	/**
	 * 签收回单
	 * @author liangxp
	 * Date:2017年11月14日下午4:41:28 
	 *
	 */
	public void signReturn() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			SessionUser user = getSessionSysUser();
			Integer shipId = getParaToInt("shipId");
			boolean sign = new SignSvc().signReturn(user, shipId);
			if(sign){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
	}
	
	
	public void signupload() {
		String shipId = getPara("shipId");
		if(StringUtils.isBlank(shipId)){
			redirect("/kd/sign");
			return ;
		}
		KdShip ship = new KdShipSvc().findShipList(shipId);
		if(ship==null){
			redirect("/kd/sign");
			return ;
		}
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		setAttr("time", DateUtils.getNow(DateUtils.YMD_HMS));
		setAttr("ship", ship);
		setAttr("receivecustomer", receiverCustomer);
		renderJsp(path + "uploadinfo.jsp");
	}
	
	
	
	/**
	 * 签收运单
	 * @author liangxp
	 * Date:2017年11月14日下午4:40:38 
	 *
	 */
	public void signShip() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			SessionUser user = getSessionSysUser();
			SignModel model = SignModel.getBindModel(SignModel.class, getRequest());
			
			//签收人信息必填
			if(StringUtils.isEmpty(model.getSignName())||StringUtils.isEmpty(model.getSignTime())){
				result.setResultType(ResultType.PARAM_ERROR);
				renderJson(result);
				return;
			}
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			int index = 1;
			String filename = getPara("filename"+index);
			while(StringUtils.isNotBlank(filename)){
				index++;
				transforImage(imgs, filename, gid, "签收资料图片", user.getUserId());
				filename = getPara("filename"+index);
			}
			boolean sign = new SignSvc().signShip(user, gid, model, imgs);
			if(sign){
				result.setResultType(ResultType.SUCCESS);
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
	
	public void downLoad(){
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setName("张三");
		user.setAge(10);
		user.setAddress("广州");
		
		User user1 = new User();
		user1.setName("李四");
		user1.setAge(100);
		user1.setAddress("深圳");
		
		users.add(user);
		users.add(user1);
		try {
			String filename = new String("签收运单列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(users, "签收运单列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
