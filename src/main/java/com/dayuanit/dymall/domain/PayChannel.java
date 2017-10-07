package com.dayuanit.dymall.domain;

import java.util.Date;

public class PayChannel {
    private Integer id;
    private String payName;
    private Integer payCode;
    private Integer payStatus;
    private Date modifyTime;
    private Date createTime;
    private String payImgId;


    public String getPayImgId() {
        return payImgId;
    }

    public void setPayImgId(String payImgId) {
        this.payImgId = payImgId;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public Integer getPayCode() {
        return payCode;
    }

    public void setPayCode(Integer payCode) {
        this.payCode = payCode;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
