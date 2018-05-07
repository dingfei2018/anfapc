package com.supyuan.kd.report;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 运作明细excel
 *
 * @author dingfei
 * @create 2018-01-12 14:58
 **/
public class ExcelOperation {
    @FieldComment(name = "序号")
    private int orderNum;
    @FieldComment(name = "运单号")
    private String shipSn;
    @FieldComment(name = "开单网点")
    private String netWorkName;
    @FieldComment(name = "客户单号")
    private String customerNumber;
    @FieldComment(name = "开单日期")
    private String createTime;
    @FieldComment(name = "托运方")
    private String senderName;
    @FieldComment(name = "收货方")
    private String receiverName;
    @FieldComment(name = "出发地")
    private String fromAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "体积")
    private double shipVolume;
    @FieldComment(name = "重量")
    private double shipWeight;
    @FieldComment(name = "数量")
    private int shipAmount;
    @FieldComment(name = "代收货款")
    private double agencyFund;
    @FieldComment(name = "付款方式")
    private String payWay;
    @FieldComment(name = "合计")
    private double totalFee;
    @FieldComment(name = "运费")
    private double fee;
    @FieldComment(name = "提货费")
    private double pickupFee;
    @FieldComment(name = "送货费")
    private double deliveryFee;
    @FieldComment(name = "保费")
    private double insuranceFee;
    @FieldComment(name = "包装费")
    private double packageFee;
    @FieldComment(name = "其他费")
    private double addonFee;
    @FieldComment(name = "提货(车牌号|发车日期|司机|运费)")
    private String truckTH ;
    @FieldComment(name = "短驳(车牌号|发车日期|司机|运费)")
    private String getTruckDB;
    @FieldComment(name = "干线(车牌号|发车日期|司机|运费)")
    private String getTruckGX;
    @FieldComment(name = "送货(车牌号|发车日期|司机|运费)")
    private String getTruckSH;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getNetWorkName() {
        return netWorkName;
    }

    public void setNetWorkName(String netWorkName) {
        this.netWorkName = netWorkName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public double getShipVolume() {
        return shipVolume;
    }

    public void setShipVolume(double shipVolume) {
        this.shipVolume = shipVolume;
    }

    public double getShipWeight() {
        return shipWeight;
    }

    public void setShipWeight(double shipWeight) {
        this.shipWeight = shipWeight;
    }

    public int getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(int shipAmount) {
        this.shipAmount = shipAmount;
    }

    public double getAgencyFund() {
        return agencyFund;
    }

    public void setAgencyFund(double agencyFund) {
        this.agencyFund = agencyFund;
    }


    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public double getTotalFee() {

        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getPickupFee() {
        return pickupFee;
    }

    public void setPickupFee(double pickupFee) {
        this.pickupFee = pickupFee;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public double getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(double packageFee) {
        this.packageFee = packageFee;
    }

    public double getAddonFee() {
        return addonFee;
    }

    public void setAddonFee(double addonFee) {
        this.addonFee = addonFee;
    }

    public String getTruckTH() {
        return truckTH;
    }

    public void setTruckTH(String truckTH) {
        this.truckTH = truckTH;
    }

    public String getGetTruckDB() {
        return getTruckDB;
    }

    public void setGetTruckDB(String getTruckDB) {
        this.getTruckDB = getTruckDB;
    }

    public String getGetTruckGX() {
        return getTruckGX;
    }

    public void setGetTruckGX(String getTruckGX) {
        this.getTruckGX = getTruckGX;
    }

    public String getGetTruckSH() {
        return getTruckSH;
    }

    public void setGetTruckSH(String getTruckSH) {
        this.getTruckSH = getTruckSH;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }

    public String getSignPerson() {
        return signPerson;
    }

    public void setSignPerson(String signPerson) {
        this.signPerson = signPerson;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @FieldComment(name = "中转方")
    private String transferName;
    @FieldComment(name = "中转日期")
    private String transferTime;
    @FieldComment(name = "中转费")
    private double transferFee;
    @FieldComment(name = "签收人")
    private String signPerson;
    @FieldComment(name = "签收日期")
    private String signTime;
    @FieldComment(name = "毛利")
    private double profit;
    @FieldComment(name = "毛利率")
    private double rate;

}
