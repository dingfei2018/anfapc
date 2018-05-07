/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.supyuan.component.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.context.ApplicationContext;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.supyuan.component.plugin.spring.SpringCtxHolder;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.front.scm.ScmVerifySvc;
import com.supyuan.jfinal.base.BaseController;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.util.Attr;
import com.supyuan.kd.home.HomeSvc;
import com.supyuan.kd.role.Menu;
import com.supyuan.kd.user.User;
import com.supyuan.kd.user.UserNetWork;
import com.supyuan.kd.user.UserSvc;
import com.supyuan.system.log.SysLog;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.NumberUtils;
import com.supyuan.util.StrUtils;
import com.supyuan.util.cache.Cache;
import com.supyuan.util.cache.CacheManager;

/**
 * 项目BaseControler
 * 
 * @author flyfox
 * @date 2015-08-02
 * 
 */
public abstract class BaseProjectController extends BaseController {

	private ApplicationContext context = SpringCtxHolder.getApplicationContext();

	/**
	 * 获取spring注解的Bean对象
	 * @param beanName 例如 userService 注意要和spring配置对应
	 * @return Object对象
	 */
	public <T> T  getClassBeanByName(String beanName) {
		if (context != null) {
			return (T)context.getBean(beanName);
		}
		return null;
	}

	public void renderAuto(String view) {
		String path = getAutoPath(view);

		super.render(path);
	}

	public void redirectAuto(String view) {
		String path = getAutoPath(view);

		super.redirect(path);
	}

	protected String getAutoPath(String view) {
		String path = view;

		if (!view.startsWith("/")) {
			path = "/" + path;
		}


		if (view.startsWith("/")) {
			path = "/" + path;
		}

		path = path.replace("//", "/");
		return path;
	}

	public SessionUser getSessionSysUser() {
		SessionUser sessionUser = getSessionAttr(Attr.SESSION_NAME);
		try {
			// 如果session没有，cookie有~那么就设置到Session
			if (sessionUser == null) {
				String cookieContent = getCookie(Attr.SESSION_NAME);
				if (StringUtils.isBlank(cookieContent)) {
					return null;
				}
				String key = JFlyFoxUtils.cookieDecrypt(cookieContent);
				if (StrUtils.isNotEmpty(key) && key.split(",").length == 2) {
					int userid = NumberUtils.parseInt(key.split(",")[0]);
					String password = key.split(",")[1];
					SysUser sysUser = SysUser.dao.findFirstByWhere(" where userid = ? and password = ? ", userid, password);
					if (sysUser != null){
						setSessionInfo(sysUser);
					}
				}
			}else{
				Integer isCert = sessionUser.getIsCert();
				if(isCert==0){//如果审核通过
					ScmVerify scm = new ScmVerifySvc().findTowCheckScm(sessionUser.getUserId(), 1);
					if(scm!=null&&(scm.getInt("status")==2||scm.getInt("status")==4))sessionUser.setIsCert(2);
				}
				boolean flag = new CompanySvc().existCompany(sessionUser.getUserId());
				if(flag==true){
					sessionUser.setIsCompany(1);
				}
			}
		} catch (Exception e) {
			// 异常cookie重新登陆
			removeSessionAttr(Attr.SESSION_NAME);
			removeCookie(Attr.SESSION_NAME);
			log.error("cooke user异常:", e);
			return null;
		}
		return sessionUser;
	}
	
	
	public SessionUser setSessionSysUser(SessionUser sessionUser, boolean setCookie) {
		setSessionAttr(Attr.SESSION_NAME, sessionUser);
		if (setCookie) {// 设置cookie，用id+password作为
			String key = sessionUser.getUserId() + "," + sessionUser.getPassword();
			String cookieContent = JFlyFoxUtils.cookieEncrypt(key);
			setCookie(Attr.SESSION_NAME, cookieContent, 7 * 24 * 60 * 60);
		}else{
			removeCookie(Attr.SESSION_NAME);
		}
		return sessionUser;
	}
	
	public SessionUser setSessionInfo(SysUser sysUser){
		SessionUser sessionUser = new SessionUser();
		ScmVerify scm = new ScmVerifySvc().findTowCheckScm(sysUser.getUserid(), 1);
		if(scm!=null&&(scm.getInt("status")==2||scm.getInt("status")==4))sessionUser.setIsCert(2);
		sessionUser.setPassword(sysUser.getStr("password"));
		sessionUser.setUserId(sysUser.getUserid());
		sessionUser.setCompanyId(sysUser.getInt("create_id")==sysUser.getUserid()?sysUser.getUserid():sysUser.getInt("company_id"));
		sessionUser.setUsertype(sysUser.getInt("usertype"));
		sessionUser.setMobile(sysUser.getStr("mobile"));
		
		List<LogisticsNetwork> networks = new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		List<Integer> networkIds = new ArrayList<Integer>();
		for (LogisticsNetwork userNetWork : networks) {
			networkIds.add(userNetWork.getInt("id"));
		}
		if(networkIds.size()==0)networkIds.add(0);
		sessionUser.setNetworkIds(networkIds);
		boolean flag = new CompanySvc().existCompany(sysUser.getUserid());
		if(flag)sessionUser.setIsCompany(1);
		setSessionUser(sessionUser);
		
		//菜单顶部用户个人信息
		User topUser= new UserSvc().getDetailsUser(sysUser.getUserid());
		setSessionAttr("topUser",topUser);
		
		//初始化开单系统的菜单
		menuSetting(sysUser.getUserid());
		return sessionUser;
	}

	/**
	 * 方法重写
	 * 
	 * 2015年8月2日 下午3:17:29 flyfox 369191470@qq.com
	 * 
	 * @return
	 */
	public void removeSessionSysUser() {
		removeSessionAttr(Attr.SESSION_NAME);
		// 删除cookie
		removeCookie(Attr.SESSION_NAME);
	}

	/**
	 * 用户登录，登出记录
	 * 
	 * 2015年10月16日 下午2:36:39 flyfox 369191470@qq.com
	 * 
	 * @param user
	 * @param operType
	 */
	protected void saveLog(SysUser user, String operType) {
		try {
			String tableName = user.getTable().getName();
			Integer updateId = user.getInt("update_id");
			String updateTime = user.getStr("update_time");
			String sql = "INSERT INTO `sys_log` ( `log_type`, `oper_object`, `oper_table`," //
					+ " `oper_id`, `oper_type`, `oper_remark`, `create_time`, `create_id`) " //
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			Db.update(sql, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, //
					updateId, operType, "", updateTime, updateId);
		} catch (Exception e) {
			log.error("添加日志失败", e);
		}
	}


	Cache cache = CacheManager.get("JFLYFOX_SESSION");

	public Controller setSessionAttrCache(String key, Object value) {
		String id = getSession().getId();
		cache.add(key + "_" + id, value);
		return this;
	}

	public <T> T getSessionAttrCache(String key) {
		String id = getSession().getId();
		return cache.get(key + "_" + id);
	}

	public Controller removeSessionAttrCache(String key) {
		String id = getSession().getId();
		cache.remove(key + "_" + id);
		return this;
	}

	
	public String callbackJson(String callback, Object obj){
		String jsonp = JsonKit.toJson(obj);
		if(StringUtils.isNotBlank(callback)){		
			jsonp = callback+"("+ jsonp +")";
		}
		return jsonp;
	}
	
	/**
	 * 根据session user对应菜单权限显示菜单
	 */
	public void menuSetting(int userId){
		HomeSvc homsvc=new HomeSvc();
		//根据用户id获取该用户下拥有权限的一级菜单
		List<Menu> firstMenuList=homsvc.getFirstLevMenu(userId);
		
		StringBuffer firstMenuName=new StringBuffer();
		
		String baseDataName="",reDeMageName="",LaunchQueryName="",financialMageName="",baseSetName="",receiptName="",abnormalName="",reportName="";
		
		List<Menu> baseData = null,reDeMage=null,LaunchQuery=null,financialMage=null,baseSet=null,receipt=null,abnormal=null,report=null;
		boolean waybillButton=false;
		for (Menu menu : firstMenuList) {
			int menuId=menu.getInt("id");
			firstMenuName.append(menu.getStr("name"));
			switch (menuId) {
			case 1:
				baseData=homsvc.getSecondLevMenu( menuId,userId); //2级基础资料 菜单
				baseDataName=homsvc.getMenuName(baseData);
				break;
			case 2:
				reDeMage=homsvc.getSecondLevMenu(menuId,userId); //2级收发货管理 菜单
				for (Menu menu2 : reDeMage) {
					if(menu2.get("name").equals("运单列表")){
						waybillButton=true;
					}
				}
				reDeMageName=homsvc.getMenuName(reDeMage);
				break;
			case 3:
				LaunchQuery=homsvc.getSecondLevMenu(menuId,userId); //2级发车查询 菜单
				LaunchQueryName=homsvc.getMenuName(LaunchQuery);
				break;
			case 4:
				financialMage=homsvc.getSecondLevMenu(menuId,userId); //2级财务管理 菜单
				financialMageName=homsvc.getMenuName(financialMage);
				break;
			case 5:
				baseSet=homsvc.getSecondLevMenu(menuId,userId); //2级基本设置 菜单
				baseSetName=homsvc.getMenuName(baseSet);
				break;
			case 25:
				abnormal=homsvc.getSecondLevMenu(menuId,userId); //2级异常管理 菜单
				abnormalName=homsvc.getMenuName(abnormal);
				break;
			case 26:
				receipt=homsvc.getSecondLevMenu(menuId,userId); //2级回单管理 菜单
				receiptName=homsvc.getMenuName(receipt);
				break;
			case 41:
				report=homsvc.getSecondLevMenu(menuId,userId); //2级报表中心 菜单
				break;
			default:
				break;
			}
			
		}
		System.out.println("firstMenuName:"+firstMenuName);
		setSessionAttr("firstMenuName", firstMenuName); //1级菜单名字
		setSessionAttr("baseData", baseData); //2级 基础资料 菜单
		setSessionAttr("reDeMage", reDeMage); //2级收发货管理 菜单
		setSessionAttr("LaunchQuery", LaunchQuery); //2级发车查询 菜单
		setSessionAttr("financialMage", financialMage); //2级财务管理 菜单
		setSessionAttr("baseSet", baseSet); //2级基本设置 菜单
		setSessionAttr("receipt", receipt); //2级回单管理 菜单
		setSessionAttr("abnormal", abnormal); //2级异常 菜单
		setSessionAttr("report", report); //2级报表 菜单
		setSessionAttr("waybillButton", waybillButton); //2是否显示创建运单按钮
		
		setSessionAttr("baseDataName", baseDataName); 
		setSessionAttr("reDeMageName", reDeMageName); 
		setSessionAttr("LaunchQueryName", LaunchQueryName); 
		setSessionAttr("financialMageName", financialMageName); 
		setSessionAttr("baseSetName", baseSetName); 
		setSessionAttr("receiptName", receiptName); 
		setSessionAttr("abnormalName", abnormalName);
		setSessionAttr("reportName", reportName);
	}
	
	
}
