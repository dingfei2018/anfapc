package com.supyuan.kd.waybill;

import com.supyuan.jfinal.base.BindModel;

/**
 * 运单查询参数
 *
 * @author dingfei
 * @create 2017-12-19 17:06
 **/
public class KdShipSearchModel extends BindModel {
    /**
     * 开单网点
     */
    private String netWorkId;
    /**
     * 开单开始时间
     */
    private String startTime;

    /**
     * 开单结束时间
      */
    private String endTime;
    /**
     * 运单号
     */
    private String shipSn;

    /**
     * 运单状态
     */
    private  String shipSate;

    /**
     * 托运方
     */
    private String senderId;

    /**
     * 收货方
     */
    private String receiverId;
    /**
     * 出发地
     */
    private String fromCode;

    /**
     * 到达地
     */
    private String toCode;


    /**
     * 客户单号

     */
    private String customerNumber;


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

    public String getShipSate() {
        return shipSate;
    }

    public void setShipSate(String shipSate) {
        this.shipSate = shipSate;
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

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }




}
