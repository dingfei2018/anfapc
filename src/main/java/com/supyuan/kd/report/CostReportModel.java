package com.supyuan.kd.report;

import com.supyuan.util.excel.poi.FieldComment;


import com.supyuan.util.excel.poi.FieldComment;

/**
 * 成本月报实体类
 * @author  yuwen
 * 
 */
public class CostReportModel {
	
	@FieldComment(name="序号")
	private int num;

	@FieldComment(name="月份")
	private String time;
	
	@FieldComment(name="网点")
	private String  netName;

	@FieldComment(name="成本合计")
	private double  cost;



	@FieldComment(name="干线车次")
	private int  gxcount;

	@FieldComment(name="干线费")
	private double  gxfee;

	@FieldComment(name="提货车次")
	private int  thcount;

	@FieldComment(name="提货费")
	private double  thfee;


	@FieldComment(name="短驳车次")
	private int  dbcount;
	@FieldComment(name="短驳费")
	private double  dbfee;

	@FieldComment(name="送货车次")
	private int  shcount;

	@FieldComment(name="送货费")
	private double  shfee;

	@FieldComment(name="中转单次")
	private int  zzcount;
	@FieldComment(name="中转费")
	private int  zzfee;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getGxcount() {
		return gxcount;
	}

	public void setGxcount(int gxcount) {
		this.gxcount = gxcount;
	}

	public double getGxfee() {
		return gxfee;
	}

	public void setGxfee(double gxfee) {
		this.gxfee = gxfee;
	}

	public int getThcount() {
		return thcount;
	}

	public void setThcount(int thcount) {
		this.thcount = thcount;
	}

	public double getThfee() {
		return thfee;
	}

	public void setThfee(double thfee) {
		this.thfee = thfee;
	}

	public int getDbcount() {
		return dbcount;
	}

	public void setDbcount(int dbcount) {
		this.dbcount = dbcount;
	}

	public double getDbfee() {
		return dbfee;
	}

	public void setDbfee(double dbfee) {
		this.dbfee = dbfee;
	}

	public int getShcount() {
		return shcount;
	}

	public void setShcount(int shcount) {
		this.shcount = shcount;
	}

	public double getShfee() {
		return shfee;
	}

	public void setShfee(double shfee) {
		this.shfee = shfee;
	}

	public int getZzcount() {
		return zzcount;
	}

	public void setZzcount(int zzcount) {
		this.zzcount = zzcount;
	}

	public int getZzfee() {
		return zzfee;
	}

	public void setZzfee(int zzfee) {
		this.zzfee = zzfee;
	}
}

