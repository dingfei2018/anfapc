package com.supyuan.component.base;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.front.scm.ScmVerifySvc;
import com.supyuan.jfinal.base.BaseController;
import com.supyuan.mkd.common.MPage;
import com.supyuan.mkd.common.MSessionUser;
import com.supyuan.mkd.network.LogisticsNetwork;
import com.supyuan.mkd.network.NetWorkSvc;
import com.supyuan.system.log.SysLog;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.encrypt.Md5Utils;

/**
 * 手机开单控制层基类
 * @author liangxp
 *
 * Date:2018年1月26日下午3:41:34 
 * 
 * @email liangxp@anfawuliu.com
 */
public abstract class MBaseProjectController extends BaseController {
	
	protected static final Log log = Log.getLog(MBaseProjectController.class);
	
	private static final String USERKEY = "userkey";
	
	private static final String SALT = "934@#dffkjw@#$";

	public MSessionUser setSessionInfo(SysUser sysUser){
		MSessionUser sessionUser = new MSessionUser();
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
		String createUserKey = createUserKey(sysUser);
		log.debug(createUserKey);
		sessionUser.setUserkey(createUserKey);
		CacheKit.put(USERKEY, sysUser.getUserid()+"", sessionUser);
		return sessionUser;
	}


	public void removeSessionSysUser() {
		String userkey = getHeader(USERKEY);
		removeSessionAttr(userkey);
	}
	
	
	/**
	 * 手机端用户登录日志
	 * @author liangxp
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
			Db.update(sql, SysLog.TYPE_MOBILE, SysLog.getTableRemark(tableName), tableName, updateId, operType, getHeader("imei"), updateTime, updateId);
		} catch (Exception e) {
			log.error("添加日志失败", e);
		}
	}
	
	
	/**
	 * 生成userkey
	 * @author liangxp
	 * @param user
	 * @return
	 */
	public String createUserKey(SysUser user){
		//有效时间30天
		long sday = 30*24*3600*1000l;
		long time = System.currentTimeMillis() + sday;
		String md5Str = new Md5Utils().getMD5(user.getUserid()  + "_" + SALT + "_" + time);
		String key = USERKEY + "_" +  user.getUserid() + "_" + time + "_" + md5Str; 
		return key;
	}                                                                                                 
	
	
	/**
	 * 获取用户登录信息
	 * @author liangxp
	 * @param userkey
	 * @return
	 */
	public MSessionUser getMSessionUser(){
		String userkey = getHeader("userkey");
		log.info("用户请求userkey=" + userkey);
		if(StringUtils.isNotBlank(userkey) && userkey.contains("_")){
			String[] keys = userkey.split("_");
			if(keys.length==4){
				long old = Long.parseLong(keys[2]);
				if(old<System.currentTimeMillis()){//已经失效
					return null;
				}
				String md5Str = new Md5Utils().getMD5(keys[0]  + "_" + SALT + "_" + keys[2]);
				if(md5Str.equals(keys[3])){//用户key
					return null;
				}
				MSessionUser attr = (MSessionUser)CacheKit.get(USERKEY, keys[1]);
				if(attr!=null&&attr.getUserkey().equals(userkey)){//同一个userkey
					return attr;
				}
			}
		}else{
			return null;
		}
		return null;
	}
	
	

	
	
	public MPage transforMpage(Page page){
		MPage data = new MPage();
		if(page == null) return data;
		data.setList(page.getList());
		data.setPageNumber(page.getPageNumber());
		data.setHasMore(!page.isLastPage());
		data.setTotalRow(page.getTotalRow());
		return data;
	}
	
}
