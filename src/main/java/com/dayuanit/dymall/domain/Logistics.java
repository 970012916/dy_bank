package com.dayuanit.dymall.domain;

import java.util.Date;

public class Logistics {
    private Integer id;
    private String logisticsName;
    private Integer logisticsCode;
    private Integer logisticsStatus;
    private Date modifyTime;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public Integer getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(Integer logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public Integer getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
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
