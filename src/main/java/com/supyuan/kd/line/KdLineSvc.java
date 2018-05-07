package com.supyuan.kd.line;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.supyuan.front.index.IndexLine;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.system.line.SysLine;
import com.supyuan.util.StrUtils;
/**
 * 专线管理业务层
 * @author yuwe
 *
 */
public class KdLineSvc extends BaseService {

	/**
	 * 分页查询所有的专线
	 */
	
	public Page<SysLine> getKdLinePage(Paginator paginator, KdLineSearchModel model, SessionUser user) {
		StringBuilder select=new StringBuilder(" SELECT `id`,getRegion(from_city_code) AS fromAdd , getRegion(to_city_code) AS toAdd ,getParkName(logistics_park_id) AS ParkName ,  `price_heavy`, `price_small`, `starting_price`, `logistics_park_id`, `company_id`, `create_time`,`is_sale`, `is_live`, getNetworkNameById(network_id) AS networkName , getNetworkNameById(arrive_network_id) AS arriveNetworkName ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();

		sql.append(" FROM `logistics_line`  ");
		parm.append(" WHERE 1=1   and is_live = 1 ");
		if (StringUtils.isNotBlank(model.getNetWorkId())) {
			parm.append(" and `network_id`='"+model.getNetWorkId()+"' ");
		}
		if (StringUtils.isNotBlank(model.getArriveNetWorkId())) {
			parm.append(" and `arrive_network_id`='"+model.getArriveNetWorkId()+"' ");
		}
		
		if (StringUtils.isNotBlank(model.getFromCode())) {
			parm.append("and `from_city_code`like '"+ StrUtils.getRealRegionCode(model.getFromCode())+"%'");
		}
		if (StringUtils.isNotBlank(model.getToCode())) {
			parm.append(" and `to_city_code` like '"+StrUtils.getRealRegionCode(model.getToCode())+"%' ");
		}
		
		sql.append(parm.toString());
		sql.append(" and company_id="+user.getCompanyId()+" and network_id in ("+user.toNetWorkIdsStr()+") ");
		sql.append(" order by networkName asc ");
		return SysLine.dao.paginate(paginator, select.toString(), sql.toString());
	}

	
	
	/**
	 * 查询一条专线
	 * @param id 
	 * @param user
	 * @return
	 */
	public SysLine getSysLine(String id, SessionUser user) {
		StringBuilder sql=new StringBuilder(" SELECT `id`,getRegion(from_city_code) AS fromAdd ,from_city_code, getRegion(to_city_code) AS toAdd ,to_city_code,getParkName(logistics_park_id) AS ParkName ,  `price_heavy`, `price_small`, `starting_price`, `logistics_park_id`, `company_id`, `create_time`,`is_sale`, `is_live`, getNetworkNameById(network_id) AS networkName ,network_id, arrive_network_id,getNetworkNameById(arrive_network_id) AS arriveNetworkName ");
		sql.append(" FROM `logistics_line` ");
		sql.append(" WHERE company_id=? ");
		sql.append(" AND id= ? ");
		return new SysLine().findFirst(sql.toString(), user.getCompanyId(),id);
	}
	
	
	/**
	 * 删除专线
	 * @param lineId
	 * @return
	 */
	public boolean  deleteLineById(String lineId){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				int deleteout=Db.update("delete from logistics_line where id in (" + lineId + ")" );
				if(deleteout==0)return false;
				return true;
			}
		});
	}
	
	
	public List<SysLine> getLineListByNetWorkId(String netWorkId){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT `id`,getRegion(from_city_code) AS fromAdd , getRegion(to_city_code) AS toAdd ,to_city_code,getParkName(logistics_park_id) AS ParkName ,  `price_heavy`, `price_small`, `starting_price`, `logistics_park_id`, `company_id`, `create_time`,`is_sale`, `is_live`, getNetworkNameById(network_id) AS networkName , getNetworkNameById(arrive_network_id) AS arriveNetworkName,arrive_network_id ");
		sql.append(" FROM `logistics_line` ");
		sql.append(" WHERE network_id in ("+netWorkId+")");
		 
		return new SysLine().find(sql.toString());
	}
	
	public List<SysLine> getCityByNetWorkId(String netWorkId){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT `id`,getRegion(from_city_code) AS fromAdd , getRegion(to_city_code) AS toAdd ,to_city_code,from_city_code");
		sql.append(" FROM `logistics_line` ");
		sql.append(" WHERE network_id in ("+netWorkId+") group by from_city_code");
		 
		return new SysLine().find(sql.toString());
	}
	
	
	/**
	 * 验证是否发布过该专线
	 * @param fromCityCode
	 * @param toCityCode
	 * @param networkId
	 * @return
	 */
	public IndexLine checkLine(String fromCityCode, String toCityCode,String networkId){
		List<IndexLine> lines=null;
		StringBuilder sql=new StringBuilder("select line.id, getRegion(from_city_code) AS formcity,getRegion(to_city_code) AS tocity, getNetworkNameById(network_id) AS network from logistics_line line ");
		
		if(StringUtils.isNotBlank(fromCityCode)){
			sql.append(" where from_city_code='" + fromCityCode + "'");
		}
		if(StringUtils.isNotBlank(toCityCode)){
			
			  sql.append(" and to_city_code='" + toCityCode + "'");
		}
		sql.append(" and network_id="+networkId);
		lines=IndexLine.dao.find(sql.toString());
		if (lines.size() == 0) {
			return null;
		}
		return lines.get(0);	
	}
	
}
