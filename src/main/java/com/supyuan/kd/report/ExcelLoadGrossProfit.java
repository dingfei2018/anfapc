package com.supyuan.kd.report;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 营业额月报表excel
 *
 * @author huangym
 **/
public class ExcelLoadGrossProfit {
    @FieldComment(name = "序号")
    private int num;
    @FieldComment(name = "配载单号")
    private String loadSn;
    @FieldComment(name = "配载网点")
    private String netName;
    @FieldComment(name = "到货网点")
    private String nextNetName;
    @FieldComment(name = "发车日期")
    private String loadDepartTime;
    @FieldComment(name = "车牌号")
    private String truckIdNumber;
    @FieldComment(name = "司机")
    private String truckDriverName;
    @FieldComment(name = "司机电话")
    private String truckDriverMobile;
    @FieldComment(name = "营业额合计")
    private double totalTurnover;
    @FieldComment(name = "配载费合计")
    private double totalLoadFee;
    @FieldComment(name = "发车毛利")
    private double grossProfit;
    @FieldComment(name = "发车毛利率")
    private String grossProfitRate;
    @FieldComment(name = "出发地")
    private String fromAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "运单单数")
    private int loadCount;
    @FieldComment(name = "装车件数")
    private int loadAmount;
    @FieldComment(name = "装车体积")
    private double loadVolume;
    @FieldComment(name = "装车重量")
    private double loadWeight;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLoadSn() {
        return loadSn;
    }

    public void setLoadSn(String loadSn) {
        this.loadSn = loadSn;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getNextNetName() {
        return nextNetName;
    }

    public void setNextNetName(String nextNetName) {
        this.nextNetName = nextNetName;
    }

    public String getLoadDepartTime() {
        return loadDepartTime;
    }

    public void setLoadDepartTime(String loadDepartTime) {
        this.loadDepartTime = loadDepartTime;
    }

    public String getTruckIdNumber() {
        return truckIdNumber;
    }

    public void setTruckIdNumber(String truckIdNumber) {
        this.truckIdNumber = truckIdNumber;
    }

    public String getTruckDriverName() {
        return truckDriverName;
    }

    public void setTruckDriverName(String truckDriverName) {
        this.truckDriverName = truckDriverName;
    }

    public String getTruckDriverMobile() {
        return truckDriverMobile;
    }

    public void setTruckDriverMobile(String truckDriverMobile) {
        this.truckDriverMobile = truckDriverMobile;
    }

    public double getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(double totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public double getTotalLoadFee() {
        return totalLoadFee;
    }

    public void setTotalLoadFee(double totalLoadFee) {
        this.totalLoadFee = totalLoadFee;
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

    public int getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(int loadCount) {
        this.loadCount = loadCount;
    }

    public int getLoadAmount() {
        return loadAmount;
    }

    public void setLoadAmount(int loadAmount) {
        this.loadAmount = loadAmount;
    }

    public double getLoadVolume() {
        return loadVolume;
    }

    public void setLoadVolume(double loadVolume) {
        this.loadVolume = loadVolume;
    }

    public double getLoadWeight() {
        return loadWeight;
    }

    public void setLoadWeight(double loadWeight) {
        this.loadWeight = loadWeight;
    }
}
