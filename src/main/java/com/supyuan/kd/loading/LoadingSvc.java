package com.supyuan.kd.loading;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.common.ShipStatus;
import com.supyuan.kd.kucun.KdInOut;
import com.supyuan.kd.network.NetWork;
import com.supyuan.kd.network.NetWorkSvc;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.truck.Truck;
import com.supyuan.kd.truck.TruckSvc;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.DateUtils;
import com.supyuan.util.StrUtils;


/**
 * 装载服务类
 * @author liangxp
 *
 * Date:2017年11月13日下午5:07:01 
 * 
 * @email liangxp@anfawuliu.com
 */
public class LoadingSvc  extends BaseService {
	
	
	private final static Log log = Log.getLog(LoadingSvc.class);
	
	DecimalFormat   df   = new DecimalFormat("#.00"); 
	
	/**
	 * 装车
	 * @author liangxp
	 * Date:2017年11月14日下午3:40:34 
	 *
	 * @param user
	 * @param shipIds
	 * @return
	 */
	public boolean  loading(SessionUser user,KdLoadGxFee loadGxFee, KdTrunkLoad load, String shipIds, Truck truck){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				Date time = new Date();
				
				//更新司机信息
				truck.set("create_time", time);
				boolean res = new TruckSvc().saveOrUpdate(user, truck);
				if(!res){
					return false;
				}
				
				KdShip ship = queryShipCounts(shipIds);
				load.set("load_count", ship.getInt("count"));
				load.set("load_volume", ship.getDouble("volume"));
				load.set("load_amount", ship.getInt("amount"));
				load.set("load_weight", ship.getDouble("weight"));
				load.set("company_id", user.getCompanyId());
				load.set("load_delivery_status", 1);
				load.set("truck_id", truck.getInt("truck_id"));
				load.set("create_time", time);
				load.set("load_depart_time", time);
				
				if(!load.save(user))return false;
				if(loadGxFee != null){
					loadGxFee.set("load_id", load.getInt("load_id"));
					loadGxFee.set("create_time", time);
					if(!loadGxFee.save())return false;
				}
				
				List<KdTrunkShip> datas = new ArrayList<KdTrunkShip>();
				List<KdShipTrack> tracks = new ArrayList<KdShipTrack>();
				List<KdInOut> inOuts  = new ArrayList<KdInOut>();
				int loadId = load.getInt("load_id");
				int networkId = load.getInt("load_next_network_id");
				int tranType = load.getInt("load_transport_type");
				
				NetWork netWork = new NetWorkSvc().getNetWork(load.getInt("network_id") +"", user);
				NetWork endnetWork = new NetWorkSvc().getNetWork(load.getInt("load_next_network_id")+"", user);
				
				List<KdShip> ships = new KdShipSvc().findShips(shipIds);
				for (int i=0; i<ships.size(); i++) {
					KdShip sp  = ships.get(i);
					int shipId = sp.getInt("ship_id");
					sp.set("ship_before_status", sp.getInt("ship_status"));
					KdTrunkShip tship = new KdTrunkShip();
					tship.set("truck_load_id", loadId);
					tship.set("ship_id", shipId);	
					datas.add(tship);
					int loadworkId = sp.getInt("load_network_id");
					if(tranType==1){
						sp.set("ship_storage", 1);//已提货配载
					}else{
						//sp.set("ship_status", getLoadShipStatus(tranType).getType());
						sp.set("ship_storage", 2);//已配载
					}
					sp.set("before_load_network_id", sp.getInt("load_network_id"));//前一个配载
					if(networkId>0)sp.set("load_network_id", networkId);//档选择到货网点才设置
					sp.set("ship_load_times", sp.getInt("ship_load_times")+1);//被配载
					
					
					if(tranType!=1){//非提货配载
						KdInOut	inout = new KdInOut();
						inout.set("ship_id", shipId);	
						inout.set("network_id", loadworkId);		
						inout.set("out_time", time);		
						inOuts.add(inout);
					}

					//保存物流信息
					KdShipTrack track = new KdShipTrack();
					track.set("track_ship_id", shipId);
					track.set("user_id", user.getUserId());
					track.set("track_resource_id", loadId);
					track.set("track_company_id", user.getCompanyId());
					track.set("track_class", KdShipTrack.MODEL_SHIP_LOAD_NUM);
					track.set("track_networkid",load.getInt("network_id"));
					track.set("track_short_desc", getLoadDesc(tranType));
					String desc = netWork.getStr("sub_network_name")+getLoadDesc(tranType);
					if(endnetWork!=null)desc+=",下一站"+endnetWork.getStr("sub_network_name");
					track.set("track_desc", desc);
					track.set("create_time", time);
					tracks.add(track);
				}
				
				if(inOuts.size()>0){
					//更新出入库记录 add  by liangxiaoping
					int[] inOutsave = Db.batch("update kd_in_out set out_time=? where ship_id=? and network_id=?", "out_time,ship_id,network_id", inOuts, inOuts.size());
					if(inOutsave.length!=inOuts.size())return false;
				}
			
				int[] tshipSave = Db.batchSave(datas, datas.size());
				if(tshipSave.length!=datas.size())return false;

				int[] shipSave = Db.batchUpdate(ships, ships.size());
				if(shipSave.length!=ships.size())return false;

				//操作日志
				int[] trackSave = Db.batchSave(tracks, tracks.size());
				if(trackSave.length!=tracks.size())return false;
				
				return true;
			}
		});
	}
	
	
	public String getLoadDesc(int loadType){
		String desc = "";
		switch (loadType) {
			case 1:  desc = KdShipTrack.MODEL_SHIP_LOAD_TIHUO; break;
			case 2:  desc = KdShipTrack.MODEL_SHIP_LOAD_DUANBO; break;
			case 3:  desc = KdShipTrack.MODEL_SHIP_LOAD_GANXIAN; break;
			case 4:  desc = KdShipTrack.MODEL_SHIP_LOAD_SONGHUO; break;
			default:;break;
		}
		return desc;
	}
	
	/**
	 * 运单列表查询
	 * @author liangxp
	 * Date:2017年11月14日上午11:32:40 
	 *
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdShip>  queryShipList(Paginator paginator, SessionUser user, LoadSearchModel model){
		String select = "SELECT s.ship_id, s.ship_sn, getProductName(s.ship_id) proname, s.ship_volume,getNetworkNameById(s.to_network_id) enetworkName, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity,getNetworkNameById(s.network_id) snetworkName";
		StringBuilder bu = new StringBuilder(" FROM kd_ship s  where ship_deleted=0 ");
		if(model.getType()==1){
			bu.append("and s.ship_storage=0");
		}else{
			bu.append("and s.ship_storage<2");
		}
		List<Object> paras = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(model.getShipSn())){//运单查询
			bu.append(" and s.ship_sn like ?");
			paras.add("%"+ model.getShipSn() +"%");
		}
		
		if(model.getSendName()>0){//开单起始地址
			bu.append(" and s.ship_sender_id=?");
			paras.add(model.getSendName());
		}
		
		if(model.getReceiveName()>0){//开单起始地址
			bu.append(" and s.ship_receiver_id=?");
			paras.add(model.getReceiveName());
		}
		
		if(model.getLoadNetworkId()>0){
			bu.append(" and s.load_network_id=?");
			paras.add(model.getLoadNetworkId());
		}else{
			bu.append(" and s.load_network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(model.getNetworkId()>0){
			bu.append(" and s.to_network_id=?");
			paras.add(model.getNetworkId());
		}/*else{
			List<Integer> netWorkIds = new com.supyuan.front.branch.NetWorkSvc().getNetWorkIds(user);
			String ids = netWorkIds.toString().replace("[", "").replace("]", "");
			bu.append(" and s.to_network_id in (" +ids+")");
		}*/
		
		
		if(StringUtils.isNotBlank(model.getStartCode())){//开单起始地址
			bu.append(" and s.ship_from_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getStartCode()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getEndCode())){//开单结束地址
			bu.append(" and s.ship_to_city_code like ?");
			paras.add(StrUtils.getRealRegionCode(model.getEndCode()) +"%");
		}
		bu.append(" order by s.create_time desc");
		return KdShip.dao.paginate(paginator, select, bu.toString(), paras.toArray());

	}
	
	
	/**
	 * 配载单列表
	 * @author liangxp
	 * @param paginator
	 * @param user
	 * @param model
	 * @return
	 */
	public Page<KdTrunkLoad>  queryTrunkLoadList(Paginator paginator, SessionUser user, LoadListSearchModel model){
		String select = "select l.load_id, l.load_sn, l.truck_id, l.load_fee, l.load_volume, l.load_amount, l.load_weight, l.load_count, l.load_next_network_id, l.load_transport_type,"
				+ " getRegion(l.load_delivery_from) fromCity, getRegion(l.load_delivery_to) toCity, l.load_delivery_status, load_image_gid, l.company_id, "
				+ " getNetworkNameById(l.network_id) snetworkName,getNetworkNameById(l.load_next_network_id) enetworkName, l.create_time,t.truck_driver_name,t.truck_driver_mobile, t.truck_id_number";
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load l left join truck t on l.truck_id=t.truck_id where load_deleted=0 and t.company_id = " + user.getCompanyId());
		List<Object> paras = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(model.getLoadSn())){//配载单查询
			bu.append(" and l.load_sn like ?");
			paras.add("%"+ model.getLoadSn() +"%");
		}
		
		if(Integer.parseInt(model.getStatus())>0){
			bu.append(" and l.load_delivery_status=?");
			paras.add(model.getStatus());
		}
		
		if(model.getTransType()>0){
			bu.append(" and l.load_transport_type=?");
			paras.add(model.getTransType());
		}
		
		
		if(StringUtils.isNotBlank(model.getTime())){
			bu.append(" and l.create_time>=? and l.create_time<=?");
			paras.add(model.getTime());
			paras.add(model.getTime()+" 23:59:59");
		}
		
		if(model.getNetworkId()>0){
			bu.append(" and l.network_id=?");
			paras.add(model.getNetworkId());
		}else{
			bu.append(" and l.network_id in (" + user.toNetWorkIdsStr()+")");
		}
		
		if(model.getLoadToNetworkId()>0){
			bu.append(" and l.load_next_network_id=?");
			paras.add(model.getLoadToNetworkId());
		}
		
		if(StringUtils.isNotBlank(model.getTruckNumber())){
			bu.append(" and t.truck_id_number like ?");
			paras.add(model.getTruckNumber()+"%");
		}
		
		if(StringUtils.isNotBlank(model.getDriverName())){
			bu.append(" and t.truck_driver_name like ?");
			paras.add(model.getDriverName()+"%");
		}
		
		if(StringUtils.isNotBlank(model.getDeliveryFrom())){
			bu.append(" and l.load_delivery_from like ?");
			paras.add(StrUtils.getRealRegionCode(model.getDeliveryFrom()) +"%");
		}
		
		if(StringUtils.isNotBlank(model.getDeliveryTo())){
			bu.append(" and l.load_delivery_to like ?");
			paras.add(StrUtils.getRealRegionCode(model.getDeliveryTo()) +"%");
			
		}
		
		bu.append(" order by l.create_time desc");
		return KdTrunkLoad.dao.paginate(paginator, select, bu.toString(), paras.toArray());
	}
	
	
	public List<LoadListModel> exportTrunkLoadList(SessionUser user, LoadListSearchModel model){
		String select = "select l.load_id, l.load_sn, l.truck_id, l.load_fee, l.load_volume, l.load_amount, l.load_weight, l.load_count, l.load_next_network_id, l.load_transport_type,"
				+ " getRegion(l.load_delivery_from) fromCity, getRegion(l.load_delivery_to) toCity, l.load_delivery_status, load_image_gid, l.company_id, "
				+ " getNetworkNameById(l.network_id) snetworkName,getNetworkNameById(l.load_next_network_id) enetworkName, l.create_time,t.truck_driver_name,t.truck_id_number";
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load l left join truck t on l.truck_id=t.truck_id where load_deleted=0 and t.company_id = " + user.getCompanyId());
		List<Object> paras = new ArrayList<Object>();
		List<Record> records = new ArrayList<Record>();;
		if(StringUtils.isNotBlank(model.getLoadSn())){//配载单查询
			bu.append(" and l.load_sn=?");
			paras.add(model.getLoadSn());
			records = Db.find(select+bu.toString(), paras.toArray());
		}else{
			if(StringUtils.isNotBlank(model.getTime())){
				bu.append(" and l.create_time>=? and l.create_time<=?");
				paras.add(model.getTime());
				paras.add(model.getTime()+" 23:59:59");
			}
			
			if(model.getNetworkId()>0){
				bu.append(" and l.network_id=?");
				paras.add(model.getNetworkId());
			}else{
				bu.append(" and l.network_id in (" + user.toNetWorkIdsStr()+")");
			}
			
			if(StringUtils.isNotBlank(model.getTruckNumber())){
				bu.append(" and t.truck_id_number like ?");
				paras.add(model.getTruckNumber());
			}
			
			if(StringUtils.isNotBlank(model.getDeliveryFrom())){
				bu.append(" and l.load_delivery_from=?");
				paras.add(model.getDeliveryFrom());
			}
			
			if(StringUtils.isNotBlank(model.getDeliveryTo())){
				bu.append(" and l.load_delivery_to=?");
				paras.add(model.getDeliveryTo());
			}
			
			if(StringUtils.isNotBlank(model.getDriverName())){
				bu.append(" and t.truck_driver_name like ?");
				paras.add(model.getDriverName());
			}
			records = Db.find(select+bu.toString(), paras.toArray());
		}
		List<LoadListModel> res = new ArrayList<LoadListModel>();;
		for (Record record : records) {
			LoadListModel mo = new LoadListModel();
			mo.setLoadSn(record.getStr("load_sn"));
			mo.setTruckIdNumber(record.getStr("truck_id_number"));
			mo.setDriverName(record.getStr("truck_driver_name"));
			mo.setLoadnetwork(record.getStr("snetworkName"));
			mo.setTransportType(record.getInt("load_transport_type")+"");
			mo.setFromAddr(record.getStr("fromCity"));
			mo.setToAddr(record.getStr("toCity"));
			mo.setShipCount(record.getInt("load_count"));
			mo.setLoadweight(record.getDouble("load_weight"));
			mo.setLoadVolume(record.getDouble("load_volume"));
			mo.setLoadAmount(record.getInt("load_amount"));
			mo.setLoadFee(record.getDouble("load_fee"));
			mo.setCreatTime(DateUtils.format(record.getDate("create_time"), DateUtils.YMD_HMS));
			mo.setIsArrive(record.getInt("load_delivery_status")+"");
			res.add(mo);
		}
		return res;
	}
	
	
	
	
	
	/**
	 * 查询指定的配载单
	 * @author liangxp
	 * @param loadId
	 * @return
	 */
	public KdTrunkLoad queryTrunkLoad(int loadId, SessionUser user){
		StringBuilder select = new StringBuilder("select l.load_id, l.load_sn, l.truck_id, l.load_fee, l.load_volume, l.load_amount, l.load_weight, l.load_count, l.load_transport_type,l.load_fee_allocation_way,"
				+ " getRegion(l.load_delivery_from) fromCity, getRegion(l.load_delivery_to) toCity, l.load_delivery_status, load_image_gid, l.company_id, l.load_remark,"
				+ " getNetworkNameById(l.network_id) snetworkName,l.network_id,getNetworkNameById(l.load_next_network_id) enetworkName, l.create_time,t.truck_driver_name,t.truck_driver_mobile,t.truck_id_number");
		select.append(" FROM kd_truck_load l left join truck t on l.truck_id=t.truck_id where load_id=? and load_deleted=0 and t.company_id = " + user.getCompanyId());
		return KdTrunkLoad.dao.findFirst(select.toString(), loadId);
	}
	
	
	public KdShip queryReduceShipCounts(String ids){
		StringBuilder bu = new StringBuilder("select count(*) count, sum(ship_volume) volume, sum(ship_amount) amount, sum(ship_weight) weight ");
		bu.append("from kd_ship where ship_deleted=0 and ship_status<9 and ship_storage>0 and ship_id in ("+ids+")");
		return KdShip.dao.findFirst(bu.toString());
	}
	
	/**
	 * 统计指定运单的数量
	 * @author liangxp
	 * @param ids
	 * @return
	 */
	public KdShip queryShipCounts(String ids){
		StringBuilder bu = new StringBuilder("select count(*) count, sum(ship_volume) volume, sum(ship_amount) amount, sum(ship_weight) weight ");
		bu.append("from kd_ship where ship_deleted=0 and ship_id in ("+ids+")");
		return KdShip.dao.findFirst(bu.toString());
	}
	
	/**
	 * 修改配载单司机信息
	 * @author liangxp
	 * @param loadId
	 * @param truck
	 * @param user
	 * @return
	 */
	public boolean changetruck(int loadId,String loadTime, Truck truck, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				truck.set("create_time", new Date());
				boolean res = new TruckSvc().saveOrUpdate(user, truck);
				if(!res) return false;
				KdTrunkLoad load = new KdTrunkLoad();
				load.set("load_id", loadId);
				load.set("load_depart_time", loadTime);
				load.set("truck_id", truck.getInt("truck_id"));
				return load.update();
			}
		});
	}
	
	/**
	 * 修改到货网点
	 * @author liangxp
	 * @param loadId
	 * @param networkId
	 * @param user
	 * @return
	 */
	public boolean changenetwork(int loadId, int networkId, String startCode, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdTrunkLoad load = new KdTrunkLoad();
				load.set("load_id", loadId);
				load.set("load_next_network_id", networkId);
				load.set("load_delivery_to", startCode);
				if(!load.update()){
					return false;
				}
				
				KdTrunkShip ship = KdTrunkShip.dao.findFirst("select GROUP_CONCAT(ship_id) shipIds from kd_truck_ship where truck_load_id=?", loadId);
				if(ship==null) return false;
				
				//更新运单到货网点
				
				int update = 0;
				if(networkId>0){
					update = Db.update("update kd_ship set load_network_id=? where ship_id in("+ship.get("shipIds")+")", networkId);
				}else{//清除到货网点，则返回上一个配载网点
					List<KdShipTrack> tracks = KdShipTrack.dao.find("select track_resource_id from kd_ship_track where track_class=3 and track_ship_id in("+ship.get("shipIds")+")");
					for (KdShipTrack kdShipTrack : tracks) {//如果是提货配载以后已经再次配载了
						if(kdShipTrack.getInt("track_resource_id") != loadId){
							return false;
						}
					}
					update = Db.update("update kd_ship set load_network_id=before_load_network_id where ship_id in("+ship.get("shipIds")+")", networkId);
				}
				if(update==0)return false;
				
				return true;
			}
		});
	}
	
	/**
	 * 
	 * @author liangxp
	 * @param loadIds
	 * @param user
	 * @return
	 */
	public boolean removeLoad(String loadIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//回滚删除配载单
				int update = Db.update("update kd_truck_load set load_deleted=1 where company_id=? and load_delivery_status=1 and load_id in("+loadIds+")", user.getCompanyId());
				if(update==0)return false;
				String[] ids = loadIds.split(",");
				KdShipSvc kdShipSvc = new KdShipSvc();
				for (String id : ids) {
					KdTrunkShip ship = KdTrunkShip.dao.findFirst("select GROUP_CONCAT(ship_id) shipIds from kd_truck_ship where truck_load_id=" + id);
					List<KdShip> ships = kdShipSvc.findShips(ship.getStr("shipIds"));
					KdTrunkLoad load = KdTrunkLoad.dao.findById(id);
					List<KdInOut> inOuts  = new ArrayList<KdInOut>();
					for (KdShip kdShip : ships) {
						kdShip.set("ship_status", kdShip.getInt("ship_before_status"));
						kdShip.set("ship_storage", 0);//未出库
						kdShip.set("load_network_id", kdShip.getInt("before_load_network_id"));
						if(kdShip.getInt("ship_load_times")>0)kdShip.set("ship_load_times", kdShip.getInt("ship_load_times")-1);//被配载
						
						KdInOut	inout = new KdInOut();
						inout.set("ship_id", kdShip.getInt("ship_id"));	
						inout.set("network_id", load.getInt("network_id"));		
						inOuts.add(inout);


						//添加日志
						int transportType = load.getInt("load_transport_type");
						String desc = "";
						if(transportType == 1){
							desc = "提货配载";
						}else if(transportType == 2){
							desc = "短驳配载";
						}else if(transportType == 3){
							desc = "发车配载";
						}else if(transportType == 4){
							desc = "送货配载";
						}

						new KdShipTrack()
								.set("user_id",user.getUserId())
								.set("create_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
								.set("track_ship_id",kdShip.getInt("ship_id"))
								.set("track_resource_id",id)
								.set("track_company_id",user.getCompanyId())
								.set("track_desc","取消"+desc)
								.set("track_short_desc","取消"+desc)
								.set("track_status",kdShip.getInt("ship_before_status"))
								.set("track_networkid",load.getInt("network_id"))
								.save();

					}
					
					int[] batchUpdate = Db.batchUpdate(ships, ships.size());
					if(batchUpdate.length!=ships.size())return false;
					int res = Db.update("delete from kd_truck_load where load_id=" + id);
					if(res==0)return false;
					int res2 = Db.update("delete from kd_truck_ship where truck_load_id=" + id);
					if(res2!=ships.size())return false;
					int res3 = Db.update("delete from kd_ship_track where track_class=3 and track_resource_id=" + id);
					if(res3!=ships.size())return false;
					Db.update("delete from kd_load_gx_fee where load_id=" + id);
					int[] inOutsave = Db.batch("update kd_in_out set out_time=? where ship_id=? and network_id=?", "out_time,ship_id,network_id", inOuts, inOuts.size());
					if(inOutsave.length!=inOuts.size())return false;
				}
				return true;
			}
		});
	}
	
	/**
	 * 发车
	 * @author liangxp
	 * @param loadIds
	 * @param user
	 * @return
	 */
	public boolean sendLoad(String loadIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				
				Date time = new Date();
				int update = Db.update("update kd_truck_load set load_delivery_status=2,load_depart_time=? where company_id=? and  load_id in("+loadIds+")", time, user.getCompanyId());
				if(update<1)return false;
				String[] ids = loadIds.split(",");
				List<KdShipTrack> tracks = new ArrayList<KdShipTrack>();
				for (String id : ids) {
					KdTrunkShip ship = KdTrunkShip.dao.findFirst("select GROUP_CONCAT(ship_id) shipIds from kd_truck_ship where truck_load_id=" + id);
					KdTrunkLoad load = KdTrunkLoad.dao.findById(id);
					NetWork netWork = new NetWorkSvc().getNetWork(load.getInt("network_id") +"", user);
					NetWork endnetWork = new NetWorkSvc().getNetWork(load.getInt("load_next_network_id")+"", user);
					String shipIds = ship.getStr("shipIds");
					int value = 0;
					int transType = load.getInt("load_transport_type");
					if(transType==2){
						value = ShipStatus.DUANBOING.type;
					}else if(transType==3){
						value = ShipStatus.SENDING.type;
					}else if(transType==4){
						value = ShipStatus.DELIVERY.type;
					}
					if(value>0){
						int res = Db.update("update kd_ship set ship_status="+value+" where ship_id in(" + shipIds + ")");
						if(res<1)return false;
					}
					
					String[] sids = shipIds.split(",");
					String loadDesc = getLoadDescShipStatus(transType);
					String desc = netWork.getStr("sub_network_name")+getLoadDesc(transType);
					for (String string : sids) {
						//保存物流信息
						KdShipTrack track = new KdShipTrack();
						track.set("track_ship_id", string);
						track.set("user_id", user.getUserId());
						track.set("track_resource_id", id);
						track.set("track_company_id", user.getCompanyId());
						track.set("track_networkid",load.getInt("network_id"));
						track.set("track_class", KdShipTrack.MODEL_SHIP_LOAD_NUM);
						track.set("track_short_desc", loadDesc);
						if(endnetWork!=null)desc+=",下一站"+endnetWork.getStr("sub_network_name");
						track.set("track_desc", desc);
						track.set("create_time", time);
						tracks.add(track);
					}
				}
				
				//操作日志
				int[] trackSave = Db.batchSave(tracks, tracks.size());
				if(trackSave.length!=tracks.size())return false;
				return true;
			}
		});
	}
	
	/**
	 * 提货完成
	 * @author liangxp
	 * @param loadIds
	 * @param user
	 * @return
	 */
	public boolean loadTihuoOver(String loadIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				Db.update("update kd_truck_load set load_depart_time=create_time where company_id=?  and load_transport_type=1  and  load_delivery_status=1 and load_id in("+loadIds+")", user.getCompanyId());
				int update = Db.update("update kd_truck_load set load_delivery_status=4 where company_id=?  and load_transport_type=1  and load_delivery_status<3 and load_id in("+loadIds+")", user.getCompanyId());
				if(update<1)return false;
				return true;
			}
		});
	}
	
	/**
	 * 送货完成
	 * @author liangxp
	 * @param loadIds
	 * @param user
	 * @return
	 */
	public boolean loadSonghuoOver(String loadIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				Db.update("update kd_truck_load set load_depart_time=create_time where company_id=?  and load_transport_type=4  and  load_delivery_status=1 and load_id in("+loadIds+")", user.getCompanyId());
				int update = Db.update("update kd_truck_load set load_delivery_status=4 where company_id=? and load_transport_type=4  and load_delivery_status<3 and load_id in("+loadIds+")", user.getCompanyId());
				if(update<1)return false;
				
				String[] ids = loadIds.split(",");
				Date time = new Date();
				List<KdShipTrack> tracks = new ArrayList<KdShipTrack>();
				for (String id : ids) {
					KdTrunkShip ship = KdTrunkShip.dao.findFirst("select GROUP_CONCAT(ship_id) shipIds from kd_truck_ship where truck_load_id=" + id);
					KdTrunkLoad load = KdTrunkLoad.dao.findById(id);
					NetWork netWork = new NetWorkSvc().getNetWork(load.getInt("network_id") +"", user);
					String shipIds = ship.getStr("shipIds");
					
					Db.update("update kd_ship set ship_status="+ShipStatus.DELIVERY.getType()+" where ship_status<9 and ship_id in(" + shipIds + ")");
					
					String[] sids = shipIds.split(",");
					String desc = netWork.getStr("sub_network_name")+KdShipTrack.MODEL_SHIP_LOAD_SONGHUO;
					for (String string : sids) {
						//保存物流信息
						KdShipTrack track = new KdShipTrack();
						track.set("track_ship_id", string);
						track.set("user_id", user.getUserId());
						track.set("track_resource_id", id);
						track.set("track_company_id", user.getCompanyId());
						track.set("track_class", KdShipTrack.MODEL_SHIP_LOAD_NUM);
						track.set("track_networkid",load.getInt("network_id"));
						track.set("track_short_desc",  "送货发车");
						track.set("track_desc", desc);
						track.set("create_time", time);
						tracks.add(track);
					}
				}
				
				//操作日志
				int[] trackSave = Db.batchSave(tracks, tracks.size());
				if(trackSave.length!=tracks.size())return false;
				return true;
			}
		});
	}
	
	
	/**
	 * 保存配载司机的信息
	 * @author liangxp
	 * @param load
	 * @param imgs
	 * @return
	 */
	public  boolean attachmentsave(KdTrunkLoad load,  List<LibImage> imgs){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				boolean res = new LibImageSvc().addLibImages(imgs);
				if(!res)return false;
				if(load==null) return true;//追加图片则不更新配载单
				if(load.update()) return true;//新增图片则更新配载单
				return false;
			}
		});
	}
	
	
	public  boolean modifyFee(int loadId, double fee){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				StringBuilder bu = new StringBuilder("update kd_truck_load set load_fee=? where load_id=?");
				int res = Db.update(bu.toString(), fee, loadId);
				if(res>0) return true;
				return false;
			}
		});
	}
	
	
	public  boolean modifyGxFee(KdLoadGxFee gxfee, int loadId, double fee){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				StringBuilder bu = new StringBuilder("update kd_truck_load set load_fee=? where load_id=?");
				int res = Db.update(bu.toString(), fee, loadId);
				if(res==0) return false;
				bu = new StringBuilder("update kd_load_gx_fee set load_id=" + loadId);
				List<Object> paras = new ArrayList<Object>();
				if(gxfee.get("load_nowtrans_fee")!=null){
					bu.append(" ,load_nowtrans_fee=?");
					paras.add(gxfee.get("load_nowtrans_fee"));
				}
				if(gxfee.get("load_nowoil_fee")!=null){
					bu.append(" ,load_nowoil_fee=?");
					paras.add(gxfee.get("load_nowtrans_fee"));
				}
				if(gxfee.get("load_backtrans_fee")!=null){
					bu.append(" ,load_backtrans_fee=?");
					paras.add(gxfee.get("load_backtrans_fee"));
				}
				if(gxfee.get("load_attrans_fee")!=null){
					bu.append(" ,load_attrans_fee=?");
					paras.add(gxfee.get("load_attrans_fee"));
				}
				if(gxfee.get("load_allsafe_fee")!=null){
					bu.append(" ,load_allsafe_fee=?");
					paras.add(gxfee.get("load_allsafe_fee"));
				}
				if(gxfee.get("load_start_fee")!=null){
					bu.append(" ,load_start_fee=?");
					paras.add(gxfee.get("load_start_fee"));
				}
				if(gxfee.get("load_other_fee")!=null){
					bu.append(" ,load_other_fee=?");
					paras.add(gxfee.get("load_other_fee"));
				}
				bu.append(" WHERE load_id=?");
				paras.add(loadId);
				int update = Db.update(bu.toString(), paras.toArray());
				if(update==0) return false;
				return true;
			}
		});
	}
	
	
	
	/**
	 * 配载单详情
	 * @author liangxp
	 * @param loadId
	 * @return
	 */
	public KdTrunkLoad  queryKdTrunkLoad(int loadId, SessionUser user){
		String select = "select l.load_id, l.load_sn, l.truck_id, l.load_fee, l.load_volume,l.load_fee_prepay, l.load_amount, l.load_weight, l.load_count, l.load_next_network_id, l.load_transport_type,l.load_fee_allocation_way,l.load_depart_time,"
				+ " getRegion(l.load_delivery_from) fromCity, getRegion(l.load_delivery_to) toCity, l.load_delivery_status, load_image_gid, l.company_id,l.load_remark, "
				+ " getNetworkNameById(l.network_id) snetworkName,l.network_id,getNetworkNameById(l.load_next_network_id) enetworkName, l.load_next_network_id, l.create_time,t.truck_driver_name,t.truck_id_number,t.truck_driver_mobile," +
				  "  l.load_atunload_fee, l.load_atunload_fee,gx.load_nowtrans_fee,gx.load_nowoil_fee, gx.load_backtrans_fee,gx.load_attrans_fee, gx.load_allsafe_fee,gx.load_start_fee,gx.load_other_fee";
		StringBuilder bu = new StringBuilder(" FROM kd_truck_load l left join truck t on l.truck_id=t.truck_id LEFT JOIN kd_load_gx_fee gx ON l.load_id = gx.load_id where load_deleted=0 and l.load_id = ? and l.company_id =?");
		return KdTrunkLoad.dao.findFirst(select + bu.toString(), loadId, user.getCompanyId());
	} 
	
	
	/**
	 * 配载单运单列表
	 * @author liangxp
	 * @param paginator
	 * @param loadId
	 * @return
	 */
	public Page<KdShip>   queryLoadShips(Paginator paginator, int loadId, SessionUser user){
		String select = "SELECT s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) sendName, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity,"
				+ "getProductName(s.ship_id) proname, getNetworkNameById(s.to_network_id) enetworkName,getNetworkNameById(s.network_id) snetworkName";
		StringBuilder bu = new StringBuilder(" from kd_truck_ship t LEFT JOIN kd_ship s on t.ship_id=s.ship_id where  t.truck_load_id=? and s.company_id=?");
		return KdShip.dao.paginate(paginator, select, bu.toString(), loadId,user.getCompanyId());
	} 
	
	/**
	 * 减少运单
	 * @author liangxp
	 * @param loadId
	 * @param shipIds
	 * @param user
	 * @return
	 */
	public  boolean reduce(int loadId,  String shipIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdTrunkLoad load = KdTrunkLoad.dao.findById(loadId);
				if(load == null) return false;
				KdShip query = queryReduceShipCounts(shipIds);
				if(query == null) return false;
				String[] split = shipIds.split(",");
				if(split.length!=query.getInt("count")){//要删减的运单包含了已经到货确认的或者签收的
					return false;
				}
				KdTrunkLoad newload  = new KdTrunkLoad();
				newload.set("load_volume", load.getDouble("load_volume")-query.getDouble("volume"));
				newload.set("load_amount", load.getInt("load_amount")-query.getInt("amount"));
				newload.set("load_weight", load.getDouble("load_weight")-query.getDouble("weight"));
				newload.set("load_count", load.getInt("load_count")-query.getInt("count"));
				newload.set("load_id", loadId);
				
				int tranType = load.getInt("load_transport_type");
				//删除配载运单的关联表
				int delete = Db.update("delete from kd_truck_ship where truck_load_id=? and ship_id in ("+shipIds+")", loadId);
				if(delete==0) return false;
				
				//更新配载表
				if(!newload.update(user)) return false;
				//更新运单表
				if(tranType==1){//提货配载
					int update = Db.update("update kd_ship  set ship_storage=0, load_network_id=before_load_network_id, ship_load_times=(CASE  when ship_load_times>0 THEN (ship_load_times-1) else 0 end)  where ship_id in (" + shipIds + ")" );
					if(update==0) return false;
				}else{
					int update = Db.update("update kd_ship  set ship_status=ship_before_status, ship_storage=0, load_network_id=before_load_network_id, ship_load_times=(CASE  when ship_load_times>0 THEN (ship_load_times-1) else 0 end)  where ship_id in (" + shipIds + ")" );
					if(update==0) return false;
				}
				
				List<KdInOut> inOuts  = new ArrayList<KdInOut>();
				if(tranType!=1){//非提货配载
					for (String shipId : split) {
						KdInOut	inout = new KdInOut();
						inout.set("ship_id", shipId);	
						inout.set("network_id", load.getInt("network_id"));		
						inOuts.add(inout);

						//添加日志
						int transportType = load.getInt("load_transport_type");
						String desc = "";
						if(transportType == 1){
							desc = "提货配载";
						}else if(transportType == 2){
							desc = "短驳配载";
						}else if(transportType == 3){
							desc = "发车配载";
						}else if(transportType == 4){
							desc = "送货配载";
						}

						new KdShipTrack()
								.set("user_id",user.getUserId())
								.set("create_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
								.set("track_ship_id",shipId)
								.set("track_resource_id",loadId)
								.set("track_company_id",user.getCompanyId())
								.set("track_desc","取消"+desc)
								.set("track_short_desc","取消"+desc)
								.set("track_networkid",load.getInt("network_id"))
								.save();
					}
					if(inOuts.size()>0){
						//更新出入库记录 add  by liangxiaoping
						int[] inOutsave = Db.batch("update kd_in_out set out_time=null where ship_id=? and network_id=?", "ship_id,network_id", inOuts, inOuts.size());
						if(inOutsave.length!=inOuts.size())return false;
					}
				}
				
				//删除操作日志表
				/*int trackdelete = Db.update("delete from kd_ship_track where track_class=3 and track_resource_id=? and track_ship_id in ("+shipIds+")", loadId);
				if(trackdelete==0) return false;*/



				return true;
			}
		});
	}
	
	
	/**
	 * 增加运单
	 * @author liangxp
	 * @param loadId
	 * @param shipIds
	 * @param user
	 * @return
	 */
	public  boolean add(int loadId,  String shipIds, SessionUser user){
		return Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				KdTrunkLoad load = KdTrunkLoad.dao.findById(loadId);
				if(load == null) return false;
				KdShip query = queryShipCounts(shipIds);
				if(query == null) return false;
				KdTrunkLoad newload  = new KdTrunkLoad();
				newload.set("load_volume", load.getDouble("load_volume")+query.getDouble("volume"));
				newload.set("load_amount", load.getInt("load_amount")+query.getInt("amount"));
				newload.set("load_weight", load.getDouble("load_weight")+query.getDouble("weight"));
				newload.set("load_count", load.getInt("load_count")+query.getInt("count"));
				newload.set("load_id", loadId);
				Date time = new Date();
				
				List<KdTrunkShip> datas = new ArrayList<KdTrunkShip>();
				List<KdShip> ships = new ArrayList<KdShip>();
				List<KdShipTrack> tracks = new ArrayList<KdShipTrack>();
				List<KdInOut> inOuts  = new ArrayList<KdInOut>();
				String[] ids = shipIds.split(",");
				int networkId = load.getInt("load_next_network_id");
				int tranType = load.getInt("load_transport_type");
				List<KdShip> oships = new KdShipSvc().findShips(shipIds);
				for (KdShip ship : oships) {
					int shipId = ship.getInt("ship_id");
					KdTrunkShip tship = new KdTrunkShip();
					tship.set("truck_load_id", loadId);
					tship.set("ship_id", shipId);	
					datas.add(tship);
					
					KdShip temp = new KdShip();
					temp.set("ship_id", shipId);
					if(tranType==1){//提货配载不做更改
						temp.set("ship_storage", 1);//已提货配载
					}else{
						temp.set("ship_before_status", ship.getInt("ship_status"));
						temp.set("ship_status", getLoadShipStatus(tranType).getType());
						temp.set("ship_storage", 2);//已配载
					}
					temp.set("before_load_network_id", ship.getInt("load_network_id"));//前一个配载
					if(networkId>0)temp.set("load_network_id", networkId);
					temp.set("ship_load_times", ship.getInt("ship_load_times")+1);//被配载
					ships.add(temp);
					
					//保存物流信息
					KdShipTrack track = new KdShipTrack();
					track.set("track_ship_id", shipId);
					track.set("user_id", user.getUserId());
					track.set("track_resource_id", loadId);
					track.set("track_company_id", user.getCompanyId());
					track.set("track_class", 3);
					track.set("track_networkid",load.getInt("network_id"));
					track.set("track_desc", getLoadDesc(tranType));
					track.set("create_time", time);
					tracks.add(track);
					
					if(tranType!=1){//提货不做出库处理
						KdInOut	inout = new KdInOut();
						inout.set("ship_id", shipId);	
						inout.set("network_id", load.getInt("network_id"));	
						inout.set("out_time", time);	
						inOuts.add(inout);
					}
				}
				
				if(tranType!=1){
					//更新出入库记录 add  by liangxiaoping
					int[] inOutsave = Db.batch("update kd_in_out set out_time=? where ship_id=? and network_id=?", "out_time,ship_id,network_id", inOuts, inOuts.size());
					if(inOutsave.length!=inOuts.size())return false;
				}
				
				int[] tshipSave = Db.batchSave(datas, ids.length);
				if(tshipSave.length!=ids.length)return false;
				
				int[] shipSave = Db.batchUpdate(ships, ids.length);
				if(shipSave.length!=ids.length)return false;
				
				//更新配载表
				if(!newload.update(user)) return false;
				
				//操作日志
				int[] trackSave = Db.batchSave(tracks, tracks.size());
				if(trackSave.length!=tracks.size())return false;
				
				return true;
			}
		});
	}
	
	/**
	 * 查询配载单中的运单列表
	 * @author liangxp
	 * @param loadId
	 * @param user
	 * @return
	 */
	public List<KdShip>  queryLoadShipList(int loadId, SessionUser user){
		String select = "SELECT  s.ship_id, s.ship_sn, getCustomerName(s.ship_sender_id) sendName, getCustomerName(s.ship_receiver_id) receiverName, s.ship_volume, getProductName(s.ship_id) proname, "
				+ "s.ship_amount, s.ship_weight, getRegion(s.ship_from_city_code) fromCity, getRegion(s.ship_to_city_code) toCity,getNetworkNameById(s.network_id) snetworkName, s.create_time ";
		StringBuilder bu = new StringBuilder(" from kd_truck_ship ts   LEFT JOIN kd_ship s on ts.ship_id=s.ship_id  where  ts.truck_load_id=?  and s.company_id=?");
		return KdShip.dao.find(select + bu.toString(), loadId, user.getCompanyId());

	}
	
	
	public ShipStatus getLoadShipStatus(int loadtype){
		switch (loadtype) {
			case 2: return ShipStatus.DUANBOING;
			case 3: return ShipStatus.SENDING;
			case 4: return ShipStatus.DELIVERY;
			default: return null;
		}
	}
	
	public String getLoadDescShipStatus(int loadtype){
		switch (loadtype) {
			case 1: return "提货发车";
			case 2: return "短驳发车";
			case 3: return "干线发车";
			case 4: return "送货发车";
			default: return null;
		}
	}
	
	
}
