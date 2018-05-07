package com.supyuan.front.modify;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.goods.GoodsSvc;
import com.supyuan.front.goods.Order;
import com.supyuan.front.logisticspark.LogisticsPark;
import com.supyuan.front.logisticspark.LogisticsParkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.InMessageSvc;
import com.supyuan.modules.message.SMSType;
import com.supyuan.modules.message.SMSUtils;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.UUIDUtil;

/**
 * 修改用户密码
 * 
 * @author dingfei <2017-06-30>
 */
@ControllerBind(controllerKey = "/front/userconter")
public class UserController extends BaseProjectController {
	private static final String path = "/pages/front/personal/";
	private Long countOfUnRead;
	/**
	 * 个人中心首页
	 */
	public void index() {
		SessionUser user = getSessionSysUser();
		if(user==null){
			redirect("/index");
			return;
		}
		CompanySvc svc = new CompanySvc();
		Company company = svc.getCompany(user.getUserId());
		if(company!=null){
			setAttr("company", company);
			Integer cert = company.getInt("is_certification");
			if(cert!=null&&cert==2){
				redirect("/front/order/hasGoods");
				return;
			}
		}
		fillMessage();
		setAttr("curr", 1);
		renderJsp(path + "index.jsp");
	}
	/**
	 * 专线管理
	 */
	public void line() {
		setAttr("curr", 6);
		SessionUser user = getSessionSysUser();
		CompanySvc svc = new CompanySvc();
		Company company=null;
		if(svc!=null){
		 company = svc.getCompany(user.getUserId());
		}
		List<LogisticsNetwork> list=new NetWorkSvc().getNetWorkList(user);
		setAttr("list", list);
		List<LogisticsPark> logisticsParks=new LogisticsParkSvc().findAllPark();
		setAttr("logisticsParks", logisticsParks);
		renderJsp(path + "line.jsp");
	}
	
	/**
	 * 修改密码页
	 */
	public void code(){
		setAttr("curr", 7);
		renderJsp(path + "code.jsp");
	}
	

	/**
	 * 查询登录用户原密码
	 */
	public void validatePassWord() {
		try {
			// 获取登录session用户
			SessionUser user = getSessionSysUser();
			// 原始密码
			String oldPassword = getPara("password"); // 获取输入密码
			List<SysUser> list = new UserSvc().getUserPassWord(user.getMobile());
			String str = String.valueOf(list.get(0));
			String pwd = str.substring(str.indexOf("password:") + 9, str.length() - 1); // 查找原密码
			String decryptPassword = JFlyFoxUtils.passwordDecrypt(pwd);// 获取加密前原始密码
			setAttr("curr", 7);
			if (decryptPassword.equals(oldPassword)) {
				this.setAttr("state", "SUCCESS");
				this.setAttr("msg", "密码正确！");
				this.renderJson();
				return;

			} else {
				this.setAttr("state", "FAILED");
				this.setAttr("msg", "旧密码不正确！");
				this.renderJson();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 *  修改密码下一步
	 */
	
	public void passwordNext(){
		setAttr("curr", 7);
		renderJsp(path + "password.jsp");
		
	}
	
	/**
	 * 修改密码成功页
	 */
	public void success(){
		
		setAttr("curr", 7);
		renderJsp(path + "success.jsp");
	}

	/**
	 * 修改保存用户密码
	 */
	public void updatePassword() {
		try {
			// 获取登录session
			SessionUser user = getSessionSysUser();
			//获取登录手机号码
			String mobile=user.getMobile();
			// 原始密码
			String password = getPara("password");
			// 加密后的密码
			String decryptPassword = JFlyFoxUtils.passwordEncrypt(password);
			int num = new UserSvc().updateUserPassWord(mobile, decryptPassword);
			setAttr("curr", 7);
			if (num == 0) {
				this.setAttr("state", "FAILED");
				this.setAttr("msg", "修改密码失败！");
				this.renderJson();
				return;
			} else {
				this.setAttr("state", "SUCCESS");
				this.setAttr("msg", "修改密码成功！");
				this.renderJson();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到完善信息页面
	 */

	public void getCompanyInfo() {
		SessionUser user = getSessionSysUser();
		if(user!=null){
		Company company = new CompanySvc().getCompany(user.getUserId());
		if(company!=null){
		Address address=new AddressSvc().findByUUID(company.getStr("corp_addr_uuid"));
		setAttr("address", address);
		}
		setAttr("user", user);
		setAttr("company", company);
		
		setAttr("curr", 1);
		renderJsp(path + "information.jsp");
		}
	}

	/**
	 * 完善基本信息
	 */

	public void saveCompanyInfo() {
		SessionUser user = getSessionSysUser();
		try {
			//省市区代码
			final String regionCode = getPara("region");
			// 公司ID
			final String companyId = getPara("companyid");
			// 公司名称
			final String corpname = getPara("corpname");
			// 营业执照编码
			final String bussinessCode = getPara("bussinessCode");
			//公司地址
			final String corpaddr=getPara("corpaddr");
			//公司联系人
			final String chargePerson=getPara("chargePerson");
			//公司电话
			 final String corpTelphone=getPara("corpTelphone");
			  this.checkCompanyName();
				 Address address=new Address();
				 address.set("uuid", UUIDUtil.UUID());
				 address.set("region_code", regionCode);
				 address.set("tail_address", corpaddr);
				 address.set("create_time", getNow());
				 address.set("user_id", user.getUserId());
			     Company company=new Company();
				company.set("id", companyId);
				company.set("corpname", corpname);
				company.set("bussiness_code", bussinessCode);
				company.set("user_id", user.getUserId());
				company.set("corp_addr_uuid",address.get("uuid"));
				company.set("charge_person", chargePerson);
				company.set("corp_telphone", corpTelphone);
				boolean savaCompany = new CompanySvc().saveCompany(company, address, user);
				if(savaCompany){
				this.setAttr("state", "SUCCESS");
				this.setAttr("msg", "提交成功！");
				this.renderJson();
				return;
		}
		} catch (Exception e) {
			e.printStackTrace();
			this.setAttr("state", "ERROR");
			this.setAttr("msg", "提交失败！");
			this.renderJson();
			return;
		}
	}
	public  void checkCompanyName(){
		SessionUser user = getSessionSysUser();
		final String corpname = getPara("corpname");
		Company company=null;
		boolean flag = new CompanySvc().existCompany(user.getUserId());
		if(StringUtils.isNotBlank(corpname)){
		company = Company.dao.findFirstByWhere(" where corpname='" + corpname + "'");
		if(flag==true && company!=null){
			this.setAttr("state", "SUCCESS");
			this.setAttr("msg", "该公司已经注册！");
			this.renderJson();
			return;
		};
	}
	}
	/**
	 *  跳转到手机号码更改页
	 */
	public  void  getMobile(){
		try{
			SessionUser user = getSessionSysUser();
			setAttr("token", SMSUtils.getToken());
			setAttr("mobile", user.getMobile());
			setSessionAttr("user", user);
			renderJsp(path + "replace.jsp");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更改新手机号页
	 */
	public void replaceMobile(){
		SessionUser user = getSessionSysUser();
		//验证码
		String smsCode = getPara("smsCode").trim();
		ResultType cres = SMSUtils.checkSMS(SMSType.REGISTER_LIMIT_NUM, user.getMobile(), smsCode, true);
		if(cres==null){
			this.setAttr("state", "SUCCESS");
			this.renderJson();
			return ;
		}else{
			this.setAttr("state", "ERROR");
			this.renderJson();
			return ;
		}
	}
	/**
	 * 手机验证成功页
	 */
	public void checksuccess() {
		setAttr("token", SMSUtils.getToken());
		renderJsp(path + "replace2.jsp");
	}
	
	/**
	 * 更新手机号
	 */
	public void updateMobile(){
		try{
			BaseResult result = new BaseResult();
			// 获取登录session用户
			SessionUser user = getSessionSysUser();
			final String newMobile=getPara("mobile"); //获取新手机号码
			//验证码
			String smsCode = getPara("smsCode").trim();
			ResultType cres = SMSUtils.checkSMS(SMSType.REGISTER_LIMIT_NUM, newMobile, smsCode, true);
			if(cres!=null){
				result.setResultType(cres);
				renderJson(result);
				return ;
			}
			boolean res = new UserSvc().updateUserMobile(newMobile,user);
			if (res) {
				this.setAttr("state", "SUCCESS");
				this.setAttr("msg", "修改成功，请重新登录");
				removeSessionSysUser();
				this.renderJson();
				return;
			} else {
				this.setAttr("state", "FAILED");
				this.setAttr("msg", "手机号更改失败！");
				this.renderJson();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
/**
 * 常发货物	
 */
 public void oftenGoods(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<Order> ordergoods = new GoodsSvc().getOrdergoods(paginator, user.getUserId());
		setAttr("page", ordergoods);
		setAttr("curr", 11);
		
		renderJsp(path + "commongoods.jsp");
	}
 
 /**
	 * yangry
	 * 2017年9月2日
	 */
	private void fillMessage() {
		SessionUser user = getSessionSysUser();
		int user_id =0;
     //获得用户id
		if(user!=null){
		 user_id = user.getUserId(); 
		}
		System.out.print("*******________user_id"+user_id);

		Paginator paginator = getPaginator();
		paginator.setPageSize(5);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<InMessage> inMessage = new InMessageSvc().getUnReadMessagePages(paginator, user_id);
		countOfUnRead = new InMessageSvc().countReadMessage(user_id);
		setAttr("unreadcount",countOfUnRead);

		setAttr("anotherpage", inMessage);
		
	}
 }
