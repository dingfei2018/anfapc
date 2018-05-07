package com.supyuan.kd.finance;

import java.text.DecimalFormat;

import com.supyuan.component.base.KdBaseModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

@ModelBind(table = "kd_ship",key = "ship_id")
public class HomeFinanceAllModel extends KdBaseModel<HomeFinanceAllModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final HomeFinanceAllModel dao = new HomeFinanceAllModel();
	
	static DecimalFormat  df = new DecimalFormat("#.00"); 

	private double shfee;
	
	private double gxfee;
	
	private double dbfee;
	
	private double thfee;
	
	private double zzfee;
	
	private double totalfee;//总收入
	
	public double getShfee() {
		return shfee;
	}

	public void setShfee(double shfee) {
		this.shfee = shfee;
	}

	public double getGxfee() {
		return gxfee;
	}

	public void setGxfee(double gxfee) {
		this.gxfee = gxfee;
	}

	public double getDbfee() {
		return dbfee;
	}

	public void setDbfee(double dbfee) {
		this.dbfee = dbfee;
	}

	public double getThfee() {
		return thfee;
	}

	public void setThfee(double thfee) {
		this.thfee = thfee;
	}

	public double getZzfee() {
		return zzfee;
	}

	public void setZzfee(double zzfee) {
		this.zzfee = zzfee;
	}

	public double getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(double totalfee) {
		this.totalfee = totalfee;
	}

}
