package com.supyuan.kd.print;

import java.util.List;

import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.waybill.KdShip;

public class LoadPrintModel {
	
	private List<KdShip> shipList;
	
	private KdTrunkLoad  load;

	public List<KdShip> getShipList() {
		return shipList;
	}

	public void setShipList(List<KdShip> shipList) {
		this.shipList = shipList;
	}

	public KdTrunkLoad getLoad() {
		return load;
	}

	public void setLoad(KdTrunkLoad load) {
		this.load = load;
	}

}
