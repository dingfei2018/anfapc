package com.supyuan.kd.report;


import com.supyuan.util.excel.poi.FieldComment;

/**
 * 利润月报实体类
 * @author  yuwen
 * 
 */
public class ProfitReportModel {

	@FieldComment(name="序号")
	private int num;

	@FieldComment(name="月份")
	private String time;

	@FieldComment(name="网点")
	private String  netName;

	@FieldComment(name="营业额")
	private double  turnover;
	@FieldComment(name="成本")
	private double  cost;
	
	@FieldComment(name="毛利")
	private double  grossProfit;
	
	@FieldComment(name="毛利率")
	private String   grossProfitRate;
	
	@FieldComment(name="单量")
	private long  amount;
	
	@FieldComment(name="现付")
	private double  ship_nowpay_fee;

	@FieldComment(name="提付")
	private double  ship_pickuppay_fee;

	@FieldComment(name="回单付")
	private double  ship_receiptpay_fee;

	@FieldComment(name="月结")
	private double  ship_monthpay_fee;

	@FieldComment(name="短欠")
	private double  ship_arrearspay_fee;

	@FieldComment(name="货款扣")
	private double  ship_goodspay_fee;

	@FieldComment(name="异动增加")
	private double  plus_fee;

	@FieldComment(name="异动减款")
	private double  minus_fee;

	@FieldComment(name="回扣")
	private double  ship_rebate_fee;

	@FieldComment(name="提货费")
	private double  thfee;

	@FieldComment(name="短驳费")
	private double  dbfee;

	@FieldComment(name="送货费")
	private double  shfee;

	@FieldComment(name="中转费")
	private double  zzfee;

	@FieldComment(name="现付运输费")
	private double  load_nowtrans_fee;

	@FieldComment(name="现付油卡费")
	private double  load_nowoil_fee;

	@FieldComment(name="回付运输费")
	private double  load_backtrans_fee;

	@FieldComment(name="到付运输费")
	private double  load_attrans_fee;

	@FieldComment(name="整车保险费")
	private double  load_allsafe_fee;

	@FieldComment(name="发站装车费")
	private double  load_start_fee;

	@FieldComment(name="发站其他费")
	private double  load_other_fee;

	@FieldComment(name="到站卸车费")
	private double  load_atunload_fee;

	@FieldComment(name="到站其他费")
	private double  load_atother_fee;

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

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public String getGrossProfitRate() {
		return grossProfitRate;
	}

	public void setGrossProfitRate(String grossProfitRate) {
		this.grossProfitRate = grossProfitRate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public double getShip_nowpay_fee() {
		return ship_nowpay_fee;
	}

	public void setShip_nowpay_fee(double ship_nowpay_fee) {
		this.ship_nowpay_fee = ship_nowpay_fee;
	}

	public double getShip_pickuppay_fee() {
		return ship_pickuppay_fee;
	}

	public void setShip_pickuppay_fee(double ship_pickuppay_fee) {
		this.ship_pickuppay_fee = ship_pickuppay_fee;
	}

	public double getShip_receiptpay_fee() {
		return ship_receiptpay_fee;
	}

	public void setShip_receiptpay_fee(double ship_receiptpay_fee) {
		this.ship_receiptpay_fee = ship_receiptpay_fee;
	}

	public double getShip_monthpay_fee() {
		return ship_monthpay_fee;
	}

	public void setShip_monthpay_fee(double ship_monthpay_fee) {
		this.ship_monthpay_fee = ship_monthpay_fee;
	}

	public double getShip_arrearspay_fee() {
		return ship_arrearspay_fee;
	}

	public void setShip_arrearspay_fee(double ship_arrearspay_fee) {
		this.ship_arrearspay_fee = ship_arrearspay_fee;
	}

	public double getShip_goodspay_fee() {
		return ship_goodspay_fee;
	}

	public void setShip_goodspay_fee(double ship_goodspay_fee) {
		this.ship_goodspay_fee = ship_goodspay_fee;
	}

	public double getPlus_fee() {
		return plus_fee;
	}

	public void setPlus_fee(double plus_fee) {
		this.plus_fee = plus_fee;
	}

	public double getMinus_fee() {
		return minus_fee;
	}

	public void setMinus_fee(double minus_fee) {
		this.minus_fee = minus_fee;
	}

	public double getShip_rebate_fee() {
		return ship_rebate_fee;
	}

	public void setShip_rebate_fee(double ship_rebate_fee) {
		this.ship_rebate_fee = ship_rebate_fee;
	}

	public double getThfee() {
		return thfee;
	}

	public void setThfee(double thfee) {
		this.thfee = thfee;
	}

	public double getDbfee() {
		return dbfee;
	}

	public void setDbfee(double dbfee) {
		this.dbfee = dbfee;
	}

	public double getShfee() {
		return shfee;
	}

	public void setShfee(double shfee) {
		this.shfee = shfee;
	}

	public double getZzfee() {
		return zzfee;
	}

	public void setZzfee(double zzfee) {
		this.zzfee = zzfee;
	}

	public double getLoad_nowtrans_fee() {
		return load_nowtrans_fee;
	}

	public void setLoad_nowtrans_fee(double load_nowtrans_fee) {
		this.load_nowtrans_fee = load_nowtrans_fee;
	}

	public double getLoad_nowoil_fee() {
		return load_nowoil_fee;
	}

	public void setLoad_nowoil_fee(double load_nowoil_fee) {
		this.load_nowoil_fee = load_nowoil_fee;
	}

	public double getLoad_backtrans_fee() {
		return load_backtrans_fee;
	}

	public void setLoad_backtrans_fee(double load_backtrans_fee) {
		this.load_backtrans_fee = load_backtrans_fee;
	}

	public double getLoad_attrans_fee() {
		return load_attrans_fee;
	}

	public void setLoad_attrans_fee(double load_attrans_fee) {
		this.load_attrans_fee = load_attrans_fee;
	}

	public double getLoad_allsafe_fee() {
		return load_allsafe_fee;
	}

	public void setLoad_allsafe_fee(double load_allsafe_fee) {
		this.load_allsafe_fee = load_allsafe_fee;
	}

	public double getLoad_start_fee() {
		return load_start_fee;
	}

	public void setLoad_start_fee(double load_start_fee) {
		this.load_start_fee = load_start_fee;
	}

	public double getLoad_other_fee() {
		return load_other_fee;
	}

	public void setLoad_other_fee(double load_other_fee) {
		this.load_other_fee = load_other_fee;
	}

	public double getLoad_atunload_fee() {
		return load_atunload_fee;
	}

	public void setLoad_atunload_fee(double load_atunload_fee) {
		this.load_atunload_fee = load_atunload_fee;
	}

	public double getLoad_atother_fee() {
		return load_atother_fee;
	}

	public void setLoad_atother_fee(double load_atother_fee) {
		this.load_atother_fee = load_atother_fee;
	}
}
