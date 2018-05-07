package com.supyuan.mkd.network;

import java.util.List;

import com.supyuan.jfinal.base.BaseService;
import com.supyuan.mkd.common.MSessionUser;


public class NetWorkSvc extends BaseService  {
	
	/**
	 * 根据公司ID查询网点列表
	 * @return
	 */
	public List<LogisticsNetwork> getNetWorkList(MSessionUser user){
		List<LogisticsNetwork> list=null;
		StringBuilder builder=new StringBuilder();
		builder.append(" select id, getBookLongRegion(sub_addr_uuid) addr, company_id,sub_network_name ");    //add sub_network_name by chenan in 2017/12/12 14:36
		builder.append(" FROM logistics_network where company_id=? ");
		list= LogisticsNetwork.dao.find(builder.toString(), user.getCompanyId());
		return list;
	}
	
	/**
	 * 根据userID查询网点列表
	 * @return
	 */
	public List<LogisticsNetwork> getNetWorkListBuUserId(MSessionUser user){
		if(user.getUsertype()==4 || user.getUsertype()==3)return getNetWorkList(user);
		List<LogisticsNetwork> list=null;
		StringBuilder builder=new StringBuilder();
		builder.append(" select l.id, getBookLongRegion(sub_addr_uuid) addr, l.company_id,sub_network_name ");    
		builder.append(" FROM logistics_network l,sys_user_network n ");
		builder.append(" where  l.id=n.network_id AND n.user_id=?");
		list= LogisticsNetwork.dao.find(builder.toString(), user.getUserId());
		return list;
	}
	
	public LogisticsNetwork getNetWorkNetworkId(int networkId){
		StringBuilder builder=new StringBuilder();
		builder.append(" select l.id, getBookLongRegion(l.sub_addr_uuid) addr, l.company_id,l.sub_network_name ");    
		builder.append(" FROM logistics_network l where  l.id=?");
		return LogisticsNetwork.dao.findFirst(builder.toString(), networkId);
	}

}
