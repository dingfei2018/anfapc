package com.supyuan.front.branch;

import java.util.ArrayList;
import java.util.List;

import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.waybill.KdShip;
import org.apache.commons.lang.StringUtils;

import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.SessionUser;

/**
 * 网点管理
 * @author dingfei
 *
 * @date 2017年10月11日 下午3:17:51
 */
public class NetWorkSvc extends BaseService  {
	
	/**
	 * 根据公司ID查询网点列表
	 * @return
	 */
	public List<LogisticsNetwork> getNetWorkList(SessionUser user){
		List<LogisticsNetwork> list=null;
		StringBuilder builder=new StringBuilder();
		builder.append(" select id, getBookLongRegion(sub_addr_uuid) addr, company_id,sub_network_name,a.region_code,getRegion(a.region_code) as shortAddr ");    //add sub_network_name by chenan in 2017/12/12 14:36
		builder.append(" FROM logistics_network LEFT JOIN address_book a ON sub_addr_uuid=a.uuid where company_id=? ");
		list= LogisticsNetwork.dao.find(builder.toString(), user.getCompanyId());
		return list;
	}
	
	/**
	 * 根据userID查询网点列表
	 * @return
	 */
	public List<LogisticsNetwork> getNetWorkListBuUserId(SessionUser user){
		if(user.getUsertype()==4 || user.getUsertype()==3)return getNetWorkList(user);
		List<LogisticsNetwork> list=null;
		StringBuilder builder=new StringBuilder();
		builder.append(" select l.id, getBookLongRegion(sub_addr_uuid) addr, l.company_id,sub_network_name,a.region_code,getRegion(a.region_code) as shortAddr ");
		builder.append(" FROM logistics_network l");
		builder.append(" INNER JOIN	sys_user_network n ON l.id=n.network_id");
		builder.append(" LEFT JOIN address_book a ON l.sub_addr_uuid=a.uuid  ");
		builder.append(" where  l.id=n.network_id AND n.user_id=?");
		list= LogisticsNetwork.dao.find(builder.toString(), user.getUserId());
		return list;
	}
	
	
	/**
	 * 根据公司ID和网点所在城市查询网点列表
	 * @return
	 */
	public List<LogisticsNetwork> getNetWorkListByCityCode(SessionUser user , String CityCode){
		List<LogisticsNetwork> list=null;
		StringBuilder builder=new StringBuilder();
		builder.append(" select id, getBookLongRegion(sub_addr_uuid) addr, park_id,company_id,sub_network_name ");    //add sub_network_name by chenan in 2017/12/12 14:36
		builder.append(" FROM logistics_network where company_id=? ");
		if  (StringUtils.isNotBlank(CityCode)) {
			builder.append("  AND getBookLongRegion(sub_addr_uuid) LIKE CONCAT ('%',getRegion(" + CityCode + "),'%')");
		}
		list= LogisticsNetwork.dao.find(builder.toString(), user.getCompanyId());
		return list;
	}
	
	public LogisticsNetwork getNetWorkNetworkId(int networkId){
		StringBuilder builder=new StringBuilder();
		builder.append(" select l.id, getBookLongRegion(l.sub_addr_uuid) addr,l.park_id, l.company_id,l.sub_network_name ");    
		builder.append(" FROM logistics_network l where  l.id=?");
		return LogisticsNetwork.dao.findFirst(builder.toString(), networkId);
	}



	/**
	 * 获取网点地址
	 * @param netWorkId
	 * @return
	 */
	public NetWork findWorkAddByWorkId(int netWorkId){
		return NetWork.dao.findFirst("select n.id,n.sub_addr_uuid,n.sub_network_name,b.region_code, (select city from library_region l where l.region_code=b.region_code) AS addrName, getRegionName(b.region_code) AS addr from logistics_network  n left join address_book b on b.uuid=n.sub_addr_uuid where n.id=?",netWorkId);
	}

	
	
	public List<Integer> getNetWorkIds(SessionUser user){
		List<LogisticsNetwork> networks = getNetWorkList(user);
		List<Integer> networkIds = new ArrayList<Integer>();
		for (LogisticsNetwork userNetWork : networks) {
			networkIds.add(userNetWork.getInt("id"));
		}
		return networkIds;
	}



	public List<LogisticsNetwork> getNetWorkByShipId(String shipId) {
		//开单网点
		KdShip ship = KdShip.dao.findById(shipId);
		Integer networkId = ship.getInt("network_id");
		//到货网点
		Integer loadNetworkId = ship.getInt("load_network_id");

		List<LogisticsNetwork> nets = new ArrayList<>();
		LogisticsNetwork net1 = LogisticsNetwork.dao.findById(networkId);
		nets.add(net1);
		if(networkId!=loadNetworkId){
			LogisticsNetwork net2 = LogisticsNetwork.dao.findById(loadNetworkId);
			nets.add(net2);
		}

		return nets;
	}

}
