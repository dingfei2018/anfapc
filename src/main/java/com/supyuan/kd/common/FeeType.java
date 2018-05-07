package com.supyuan.kd.common;


/**
 * 费用类型
 * @author yuwen
 *
 */
public enum FeeType {

	NOWPAY(1, "现付"),
	PICKUPPAY(2, "提付"),
	RECEIPTPAY(3, "回单付"),
	MONTHLYPAY(4, "月结"),
	OWE(5, "短欠"),
	LOANSBUCKLE(6, "贷款扣"),
	PICKUPFEE(7, "提货费"),
	DELIVERYFEE(8, "送货费"),
	DRAYAGE(9, "短驳费"),
	TRANSFERFEE(10, "中转费"),
	BROKERAGE(11, "回扣"),
	NOWTRANSFEE(12, "现付运输费"),
	NOWOILFEE(13, "现付油卡费"),
	BACKTRANSFEE(14, "回付运输费"),
	ALLSAFEFEE(15, "整车保险费"),
	STARTFEE(16, "发站装车费"),
	OTHERFEE(17, "发站其他费"),
	ATTRANSFEE(18, "到付运输费"),
	ATUNLOADFEE(19, "到站卸车费"),
	ATOTHERFEE(20, "到站其他费"),
	DUANBOED(21, "货款回收"),
	LOANRECOVERY(22, "货款回扣"),
	LOANSACCOUNT(23, "货款到账"),
	LOANORIGINATION(24, "货款发放"),
	CHECKFORINCOME(25, "对账收入"),
	CHECKFORSPENDING(26, "对账支出"),
	ABNORMALPLUS(27, "异动增加"),
	ABNORMALMINUS(28, "异动减款");

	public int type;

	private String name;

	private FeeType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
