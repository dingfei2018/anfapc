package com.supyuan.kd.waybill;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * ${DESCRIPTION}
 *
 * @author dingfei
 * @create 2017-12-19 17:43
 **/
public class ExcelkdShip {
    @FieldComment(name = "序号")
    private int rowNum;
    @FieldComment(name = "运单号")
    private String shipSn;
    @FieldComment(name = "开单网点")
    private String netWorkName;
    @FieldComment(name = "开单日期")
    private String createTime;
    @FieldComment(name = "客户号")
    private String customerNumber;
    @FieldComment(name = "托运人")
    private String senderName;
    @FieldComment(name = "收货人")
    private String receiverName;
    @FieldComment(name = "出发地")
    private String formAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "状态")
    private String state;
    @FieldComment(name = "体积")
    private String volume;
    @FieldComment(name = "重量")
    private String weight;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
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

    public String getFormAdd() {
        return formAdd;
    }

    public void setFormAdd(String formAdd) {
        this.formAdd = formAdd;
    }

    public String getToAdd() {
        return toAdd;
    }

    public void setToAdd(String toAdd) {
        this.toAdd = toAdd;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @FieldComment(name = "数量")
    private String amount;




}
