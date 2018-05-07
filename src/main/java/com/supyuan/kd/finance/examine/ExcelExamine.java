package com.supyuan.kd.finance.examine;

import com.supyuan.util.excel.poi.FieldComment;

import java.math.BigDecimal;

/**
 * excel导出类
 *
 * @author dingfei
 * @date 2018-03-20 17:57
 **/
public class ExcelExamine {

    @FieldComment(name = "序号")

    private int rowNum;
    @FieldComment(name = "运单号")
    private String shipSn;
    @FieldComment(name = "开单网点")
    private String netWorkName;
    @FieldComment(name = "开单日期")
    private String createTime;
    @FieldComment(name = "托运方")
    private String senderName;
    @FieldComment(name = "收货方")
    private String receiverName;
    @FieldComment(name = "出发地")
    private String formAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "运单状态")
    private String shipState;
    @FieldComment(name = "审核状态")
    private String examineState;
    @FieldComment(name = "现付")
    private double nowpayFee ;
    @FieldComment(name = "提付")
    private double pickuppayFee;
    @FieldComment(name = "回单付")
    private double receiptpayFee;
    @FieldComment(name = "月付")
    private double monthpayFee;
    @FieldComment(name = "短欠")
    private double arrearspayFee;
    @FieldComment(name = "合计")
    private double totalFee;
    @FieldComment(name = "回扣费")
    private double rebateFee;
    @FieldComment(name = "实收")
    private double receiptsFee;
    @FieldComment(name = "货款扣")
    private double goodspayFee;
    @FieldComment(name = "货号")
    private String goodsSn;
    @FieldComment(name = "货物名称")
    private String productName;
    @FieldComment(name = "开单人")
    private String userName;


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

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getExamineState() {
        return examineState;
    }

    public void setExamineState(String examineState) {
        this.examineState = examineState;
    }

    public double getNowpayFee() {
        return nowpayFee;
    }

    public void setNowpayFee(double nowpayFee) {
        this.nowpayFee = nowpayFee;
    }

    public double getPickuppayFee() {
        return pickuppayFee;
    }

    public void setPickuppayFee(double pickuppayFee) {
        this.pickuppayFee = pickuppayFee;
    }

    public double getReceiptpayFee() {
        return receiptpayFee;
    }

    public void setReceiptpayFee(double receiptpayFee) {
        this.receiptpayFee = receiptpayFee;
    }

    public double getMonthpayFee() {
        return monthpayFee;
    }

    public void setMonthpayFee(double monthpayFee) {
        this.monthpayFee = monthpayFee;
    }

    public double getArrearspayFee() {
        return arrearspayFee;
    }

    public void setArrearspayFee(double arrearspayFee) {
        this.arrearspayFee = arrearspayFee;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getRebateFee() {
        return rebateFee;
    }

    public void setRebateFee(double rebateFee) {
        this.rebateFee = rebateFee;
    }

    public double getReceiptsFee() {
        return receiptsFee;
    }

    public void setReceiptsFee(double receiptsFee) {
        this.receiptsFee = receiptsFee;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getGoodspayFee() {
        return goodspayFee;
    }

    public void setGoodspayFee(double goodspayFee) {
        this.goodspayFee = goodspayFee;
    }
}
