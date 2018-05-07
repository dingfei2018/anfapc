package com.supyuan.kd.report;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 营业额月报表excel
 *
 * @author huangym
 **/
public class ExcelTurnoverReport {
    @FieldComment(name = "序号")
    private int num;
    @FieldComment(name = "月份")
    private String month;
    @FieldComment(name = "网点")
    private String netWorkName;
    @FieldComment(name = "总单数")
    private long totalAmount;
    @FieldComment(name = "营业额合计")
    private double totalTurnover;
    @FieldComment(name = "现付")
    private double totalShipNowpayFee;
    @FieldComment(name = "提付")
    private double totalShipPickuppayFee;
    @FieldComment(name = "回单付")
    private double totalShipReceiptpayFee;
    @FieldComment(name = "月结")
    private double totalShipMonthpayFee;
    @FieldComment(name = "短欠")
    private double totalShipArrearspayFee;
    @FieldComment(name = "货款扣")
    private double totalShipGoodspayFee;
    @FieldComment(name = "异动增加")
    private double totalPlusFee;
    @FieldComment(name = "异动减款")
    private double totalMinusFee;
    @FieldComment(name = "回扣")
    private double totalShipRebateFee;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNetWorkName() {
        return netWorkName;
    }

    public void setNetWorkName(String netWorkName) {
        this.netWorkName = netWorkName;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalTurnover() {
        return this.totalShipNowpayFee+totalShipPickuppayFee+totalShipReceiptpayFee+totalShipMonthpayFee+totalShipArrearspayFee+totalShipGoodspayFee+totalPlusFee-totalMinusFee-totalShipRebateFee;
    }


    public double getTotalShipNowpayFee() {
        return totalShipNowpayFee;
    }

    public void setTotalShipNowpayFee(double totalShipNowpayFee) {
        this.totalShipNowpayFee = totalShipNowpayFee;
    }

    public double getTotalShipPickuppayFee() {
        return totalShipPickuppayFee;
    }

    public void setTotalShipPickuppayFee(double totalShipPickuppayFee) {
        this.totalShipPickuppayFee = totalShipPickuppayFee;
    }

    public double getTotalShipReceiptpayFee() {
        return totalShipReceiptpayFee;
    }

    public void setTotalShipReceiptpayFee(double totalShipReceiptpayFee) {
        this.totalShipReceiptpayFee = totalShipReceiptpayFee;
    }

    public double getTotalShipMonthpayFee() {
        return totalShipMonthpayFee;
    }

    public void setTotalShipMonthpayFee(double totalShipMonthpayFee) {
        this.totalShipMonthpayFee = totalShipMonthpayFee;
    }

    public double getTotalShipArrearspayFee() {
        return totalShipArrearspayFee;
    }

    public void setTotalShipArrearspayFee(double totalShipArrearspayFee) {
        this.totalShipArrearspayFee = totalShipArrearspayFee;
    }

    public double getTotalShipGoodspayFee() {
        return totalShipGoodspayFee;
    }

    public void setTotalShipGoodspayFee(double totalShipGoodspayFee) {
        this.totalShipGoodspayFee = totalShipGoodspayFee;
    }

    public double getTotalPlusFee() {
        return totalPlusFee;
    }

    public void setTotalPlusFee(double totalPlusFee) {
        this.totalPlusFee = totalPlusFee;
    }

    public double getTotalMinusFee() {
        return totalMinusFee;
    }

    public void setTotalMinusFee(double totalMinusFee) {
        this.totalMinusFee = totalMinusFee;
    }

    public double getTotalShipRebateFee() {
        return totalShipRebateFee;
    }

    public void setTotalShipRebateFee(double totalShipRebateFee) {
        this.totalShipRebateFee = totalShipRebateFee;
    }
}
