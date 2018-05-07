package com.supyuan.kd.track;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.user.User;


/**
 * 物流跟踪
 * @author liangxp
 *
 * Date:2017年12月28日下午4:58:50 
 * 
 * @email liangxp@anfawuliu.com
 */
public class KdShipTrackSvc  extends BaseService {
	
	
	public List<KdShipTrack> findTracks(String shipId){
		StringBuilder bu = new StringBuilder("select user_id,getNetworkNameById(track_networkid) as networkName, track_short_desc, create_time ");
		bu.append("from kd_ship_track where track_ship_id=? order by track_id desc");
		List<KdShipTrack> tracks = KdShipTrack.dao.find(bu.toString(),shipId);
		List<Integer> ids = new ArrayList<Integer>();
		for (KdShipTrack kdShipTrack : tracks) {
			int tempId = kdShipTrack.getInt("user_id");
			if(!ids.contains(tempId))ids.add(tempId);
		}
		System.out.println(ids);
		List<User> users = User.dao.find("select userid, realname from sys_user where userid in ("+ids.toString().replace("[", "").replace("]", "")+")");
		for (KdShipTrack kdShipTrack : tracks) {
			int tempId = kdShipTrack.getInt("user_id");
			for (User user : users) {
				if(tempId==user.getInt("userid"))kdShipTrack.put("username", user.get("realname"));
			}
		}
		return tracks;
	}
	/**
	 * 中转物流信息跟踪
	 * @param shipId
	 * @return
	 */
	public List<KdShipTrack> findTransferTracks(String shipId){
		StringBuilder bu = new StringBuilder("select  track_desc, create_time ");
		bu.append("from kd_ship_track where track_ship_id=? AND track_class NOT in('7') order by track_id desc");
		List<KdShipTrack> tracks = KdShipTrack.dao.find(bu.toString(),shipId);
		return tracks;
	}
	
	/**
	 * 运单物流信息跟踪
	 * @param shipId
	 * @return
	 */
	public List<KdShipTrack> findShipTrack(SessionUser user, String shipId){
		StringBuilder bu = new StringBuilder("select track_id, track_desc, create_time, track_visible, track_class,");
		bu.append(" getPCUserName("+user.getUserId()+") AS userName ");
		bu.append("from kd_ship_track where track_ship_id=? AND track_class NOT in('7') order by track_id desc");
		List<KdShipTrack> tracks = KdShipTrack.dao.find(bu.toString(),shipId);
		return tracks;
	}
	
	/**
	 * 根据TrackId删除跟踪信息
	 * @param trackId
	 */
	public Boolean deleteTrackByTrackId(String trackId) {
		int deleteout=Db.update("delete from kd_ship_track where track_id=" + trackId );
		if(deleteout==0)return false;
		
		return true;
	}
	
	
	public Boolean saveTrack(SessionUser user, String createTime, String trackDesc, String trackVisible, String shipId) {
		int save=Db.update("INSERT INTO `kd_ship_track` (`user_id`, `track_ship_id`, `track_company_id`, `track_desc`, `track_class`, `track_visible`,  `create_time` ) "
				+ " VALUES ('"+user.getUserId()+"', '"+shipId+"', '"+user.getCompanyId()+"', '"+trackDesc+"','"+10+"',  "+trackVisible+",  '"+createTime+"')");
		if(save==0)return false;
		
		return true;
	}

	/**
	 * 回单操作日志
	 * @param user
	 * @param shipId
	 * @return
	 */
	public List<KdShipTrack> findReceiptTrack(SessionUser user, String shipId){
		StringBuilder bu = new StringBuilder("select track_id, track_desc, getNetworkNameById(track_networkid) as networkName, create_time, track_visible, track_class,");
		bu.append(" getPCUserName("+user.getUserId()+") AS userName ");
		bu.append("from kd_ship_track where track_ship_id=? and track_class=7 order by track_id desc");
		List<KdShipTrack> tracks = KdShipTrack.dao.find(bu.toString(),shipId);
		return tracks;
	}
	
	
}
