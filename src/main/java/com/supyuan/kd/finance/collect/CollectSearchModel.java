package com.supyuan.kd.finance.collect;

import com.supyuan.jfinal.base.BindModel;

/**
 * 代收货款查询参数
 *
 * @author dingfei
 * @create 2017-12-25 15:13
 **/
public class CollectSearchModel extends BindModel {
    private  String netWorkId;
    private  String  loadNetWorkId;
    private String startTime;
    private String endTime;
    private String fundStatus;
    private String senderId;
    private String receiverId;
    private String fromCode;
    private String toCode;
    private String shipSn;

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

    public String getLoadNetWorkId() {
        return loadNetWorkId;
    }

    public void setLoadNetWorkId(String loadNetWorkId) {
        this.loadNetWorkId = loadNetWorkId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
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

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }
}
