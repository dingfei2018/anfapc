package com.supyuan.kd.receipt;

import com.supyuan.jfinal.base.BindModel;

/**
 * 回单查询参数
 *
 * @author dingfei
 * @create 2018-01-17 14:03
 **/
public class ReceiptListSearchModel  extends BindModel{
    /**
     * 开单网点
     */
    private int snetWorkId;
    /**
     * 回单网点
     */
    private int enetWorkId;
    /**
     * 托运方
     */
    private int senderId;

    /**
     * 收货方
     */
    private int receiveId;

    /**
     * 回单状态
     */
    private String status;

    /**
     * 代收网点邮寄单号
     */
    private  String prePostNo;
    /**
     * 发放邮寄单号
     */
    private String sendPostNo;
    /**
     * 运单号
     */
    private String shipSn;

    public int getSnetWorkId() {
        return snetWorkId;
    }

    public void setSnetWorkId(int snetWorkId) {
        this.snetWorkId = snetWorkId;
    }

    public int getEnetWorkId() {
        return enetWorkId;
    }

    public void setEnetWorkId(int enetWorkId) {
        this.enetWorkId = enetWorkId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }



}
