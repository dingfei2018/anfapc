package com.supyuan.kd.receipt;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 回单excel
 *
 * @author dingfei
 * @create 2017-12-19 17:43
 **/
public class ExcelReceipt {
    @FieldComment(name = "序号")
    private int rowNum;
    @FieldComment(name = "运单号")
    private String shipSn;
    @FieldComment(name = "开单网点")
    private String netWorkName;
    @FieldComment(name = "开单日期")
    private String createTime;
    @FieldComment(name = "托运人")
    private String senderName;
    @FieldComment(name = "收货人")
    private String receiverName;
    @FieldComment(name = "出发地")
    private String formAdd;
    @FieldComment(name = "到达地")
    private String toAdd;
    @FieldComment(name = "回单状态")
    private String status;
    @FieldComment(name = "回单网点")
    private String endWorkName;
    @FieldComment(name = "代收邮寄单号")
    private String prePostNo;
    @FieldComment(name = "发放邮寄单号")
    private String sendPostNo;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndWorkName() {
        return endWorkName;
    }

    public void setEndWorkName(String endWorkName) {
        this.endWorkName = endWorkName;
    }

    public String getPrePostNo() {
        return prePostNo;
    }

    public void setPrePostNo(String prePostNo) {
        this.prePostNo = prePostNo;
    }

    public String getSendPostNo() {
        return sendPostNo;
    }

    public void setSendPostNo(String sendPostNo) {
        this.sendPostNo = sendPostNo;
    }
}
