package com.supyuan.kd.network;




import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.user.User;
import com.supyuan.kd.user.UserSvc;
import com.supyuan.modules.addressbook.Address;
/**
 * 网点svc
 * @author chenan
 * Date:2018年1月11日上午9:41:08 
 * 
 */
public class NetWorkSvc extends BaseService {

	private final static Log log = Log.getLog(NetWorkSvc.class);
	
	public Page<NetWork> getNetWorkList(Paginator paginator,String netWorkName,String sub_leader_name,String addr,SessionUser sessionUser) {
		Page<NetWork> netWorkList;
		StringBuilder select = new StringBuilder("SELECT id,sub_network_sn,sub_network_name,sub_leader_name,sub_logistic_mobile,sub_logistic_telphone,company_id,getBookLongRegion(sub_addr_uuid) as addr ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" from logistics_network");
		parm.append(" where 1=1");
		
		if  (StringUtils.isNotBlank(netWorkName)) {
			parm.append(" and sub_network_name like ('%" + netWorkName + "%')");
		}
		if  (StringUtils.isNotBlank(sub_leader_name)) {
			parm.append(" and sub_leader_name like ('%" + sub_leader_name + "%')");
		}
		parm.append(" AND company_id=?");
		sql.append(parm.toString());
		
		netWorkList = NetWork.dao.paginate(paginator,select.toString(),sql.toString(),sessionUser.getCompanyId());
		return netWorkList;
	}
	
	public NetWork getNetWork(String id,SessionUser user){
		StringBuilder sql = new StringBuilder("SELECT l.id,sub_network_sn,sub_network_num,park_id,sub_network_name,sub_leader_name,sub_logistic_mobile,sub_logistic_telphone,company_id,getBookLongRegion (sub_addr_uuid) AS addr,a.region_code,a.tail_address,a.uuid as addId");
		sql.append(" FROM logistics_network l");
		sql.append(" inner JOIN address_book a ON l.sub_addr_uuid=a.uuid ");
		sql.append(" AND company_id=? ");
		sql.append(" AND l.id=? ");
		NetWork netWork=new NetWork().findFirst(sql.toString(),user.getCompanyId(),id);
		return netWork;
	}
	
	public boolean  update(NetWork netWork,Address addressa){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!addressa.update()) return false;
				if(!netWork.update()) return false;
				return true;
			}
		});
		return tx;
	}
	
	
	
	public boolean  saveNetWork(NetWork netWork,Address addressa, boolean mobilecheck,SessionUser user){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!addressa.save()) return false;
				netWork.set("sub_addr_uuid",addressa.get("uuid"));
				if(!netWork.save()) return false;
				
				if(mobilecheck){
					int netWorkId=netWork.get("id");
					String[] netWorkIds={netWorkId+""};
					String[] roleids={"3"};
					User newUser=new User();
					newUser.set("realname", netWork.get("sub_leader_name"));
					newUser.set("username", netWork.get("sub_logistic_mobile"));
					newUser.set("password", "EY3JNDE7nu8=");
					newUser.set("mobile", netWork.get("sub_logistic_mobile"));
					newUser.set("usertype", 1);
					newUser.set("create_time", addressa.get("create_time"));
					newUser.set("update_time", addressa.get("create_time"));
					newUser.set("create_id", user.getUserId());
					newUser.set("status", 1);
					newUser.set("company_id", user.getCompanyId());
					if(!new UserSvc().saveuser(newUser, netWorkIds,roleids )) return false;
				}
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 网点num大于100时 自动补0
	 * @param companyId
	 * @return
	 */
	public boolean  updateNetNum(int companyId){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				List<NetWork> netList=new NetWork().find("SELECT id,sub_network_num,sub_network_name,sub_addr_uuid,sub_leader_name,sub_logistic_mobile,sub_logistic_telphone,company_id,sub_network_sn,sub_network_num,park_id from logistics_network where company_id=?",companyId);
				for (NetWork netWork : netList) {
					String num=netWork.get("sub_network_num");
					if(StringUtils.isNotBlank(num)&&num.length()<3){
						if(!netWork.set("sub_network_num", "0"+num).update()) return false;
					}
				}
				return true;
			}
		});
		return tx;
	}
}
