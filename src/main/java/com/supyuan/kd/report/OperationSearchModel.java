package com.supyuan.kd.report;

import com.supyuan.jfinal.base.BindModel;

/**
 * 运作明细查询参数
 *
 * @author dingfei
 * @create 2017-12-26 17:15
 **/
public class OperationSearchModel extends BindModel {
    public String netWorkId;
    public String startTime;
    private String endTime;
    private String shipSn;
    private String  customerNumber;
    private String senderId;
    private String receiverId;
    private String fromCode;
    private String toCode;
    private String loadSn;
    private String truckIdNumber;

    public String getNetWorkId() {
        return netWorkId;
    }

    public void setNetWorkId(String netWorkId) {
        this.netWorkId = netWorkId;
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

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
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

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getLoadSn() { return loadSn; }

    public void setLoadSn(String loadSn) { this.loadSn = loadSn; }

    public String getTruckIdNumber() { return truckIdNumber; }

    public void setTruckIdNumber(String truckIdNumber) { this.truckIdNumber = truckIdNumber; }
}
