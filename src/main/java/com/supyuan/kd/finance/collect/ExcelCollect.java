package com.supyuan.kd.finance.collect;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * Excel导出
 *
 * @author dingfei
 * @create 2017-12-19 17:43
 **/
public class ExcelCollect {
    @FieldComment(name = "序号")
    private int rowNum;
    @FieldComment(name = "运单号")
    private String shipSn;
    @FieldComment(name = "开单网点")
    private String netWorkName;
    @FieldComment(name = "货款状态")
    private String agencyFundStatus;
    @FieldComment(name = "代收货款(元)")
    private double agencyFund;
    @FieldComment(name = "代收网点")
    private String agencyWorkName;
    @FieldComment(name = "运单状态")
    private String shipStatus;
    @FieldComment(name = "开单日期")
    private String createTime ;
    @FieldComment(name = "托运方")
    private String senderName;
    @FieldComment(name = "收货方")
    private String receiverName;
    @FieldComment(name = "出发地")
    private String fromAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "体积")
    private double volume;
    @FieldComment(name = "重量")
    private double weight;
    @FieldComment(name = "数量")
    private int amount;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
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

    public String getAgencyFundStatus() {
        return agencyFundStatus;
    }

    public void setAgencyFundStatus(String agencyFundStatus) {
        this.agencyFundStatus = agencyFundStatus;
    }

    public double getAgencyFund() {
        return agencyFund;
    }

    public void setAgencyFund(double agencyFund) {
        this.agencyFund = agencyFund;
    }

    public String getAgencyWorkName() {
        return agencyWorkName;
    }

    public void setAgencyWorkName(String agencyWorkName) {
        this.agencyWorkName = agencyWorkName;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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




}
