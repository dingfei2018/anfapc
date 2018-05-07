package com.supyuan.kd.finance;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "kd_ship",key = "ship_id")
public class HomeFinanceModel extends KdBaseModel<HomeFinanceModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final HomeFinanceModel dao = new HomeFinanceModel();

	private String fromAdd;
	
	private String toAdd;
	
	private double volume;
	
	private double weight;
	
	private int totalCount;
	
	private double allloadfee;
	
	private double totalfee;
	
	private double alloutfee;
	
	private double rate;

	public String getFromAdd() {
		return fromAdd;
	}

	public void setFromAdd(String fromAdd) {
		this.fromAdd = fromAdd;
	}

	public String getToAdd() {
		return toAdd;
	}

	public void setToAdd(String toAdd) {
		this.toAdd = toAdd;
	}


	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public double getAllloadfee() {
		return allloadfee;
	}

	public void setAllloadfee(double allloadfee) {
		this.allloadfee = allloadfee;
	}

	public double getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(double totalfee) {
		this.totalfee = totalfee;
	}

	public double getAlloutfee() {
		return alloutfee;
	}

	public void setAlloutfee(double alloutfee) {
		this.alloutfee = alloutfee;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
