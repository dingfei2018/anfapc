package com.supyuan.kd.track;

import java.util.List;

import com.supyuan.jfinal.base.SessionUser;
import org.apache.commons.lang3.StringUtils;

import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.transfer.ShipTransfer;
import com.supyuan.kd.transfer.ShipTransferSvc;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
/**
 * 物流跟踪信息
 * @author liangxp
 *
 * Date:2017年12月28日下午4:59:08 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/kd/track")
public class KdShipTrackController extends BaseProjectController {

	private static final String path = "/pages/kd/track/";

	public void index() {
		String shipId = getPara("shipId");
		if(StringUtils.isEmpty(shipId)){
			renderError(500);;
			return;
		}
		KdShip ship = new KdShipSvc().findShortShip(shipId);
		setAttr("ship", ship);
		List<KdShipTrack> tracks = new KdShipTrackSvc().findTracks(shipId);
		setAttr("tracks", tracks);
		renderJsp(path + "index.jsp");
	}
	
	/**
	 * 中转物流跟踪
	 */
	public void transfer() {
		String shipId = getPara("shipId");
		if(StringUtils.isEmpty(shipId)){
			renderError(500);;
			return;
		}
		ShipTransfer shipTransfer = new  ShipTransferSvc().getTransferByshipId(shipId);
		setAttr("shipTransfer", shipTransfer);
		List<KdShipTrack> tracks = new KdShipTrackSvc().findTransferTracks(shipId);
		setAttr("tracks", tracks);
		renderJsp(path + "transfer.jsp");
	}


	/**
	 * 回单日志
	 */
	public void receipt() {
		SessionUser user = getSessionSysUser();
		String shipId = getPara("shipId");
		if(StringUtils.isEmpty(shipId)){
			renderError(500);;
			return;
		}
		KdShip kdShip = new KdShipSvc().findShortShip(shipId);
		setAttr("kdShip", kdShip);
		List<KdShipTrack> tracks = new KdShipTrackSvc().findReceiptTrack(user,shipId);
		setAttr("tracks", tracks);
		renderJsp(path + "journal.jsp");
	}


}
