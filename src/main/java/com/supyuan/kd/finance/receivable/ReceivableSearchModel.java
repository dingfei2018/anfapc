package com.supyuan.kd.finance.receivable;

import com.supyuan.jfinal.base.BindModel;

/**
 * 应收查询参数
 *
 * @author dingfei
 * @create 2017-12-20 13:49
 **/
public class ReceivableSearchModel extends BindModel {
    private String  networkId;
    private String startTime;
    private String endTime;
    private String feeStatus;
    private String shipSn;
    private String senderId;
    private String receiverId;
    private String formCode;
    private String toCode;
    private String customerNumber;
    private String  toNetworkId;

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getToNetworkId() {
        return toNetworkId;
    }

    public void setToNetworkId(String toNetworkId) {
        this.toNetworkId = toNetworkId;
    }
}
