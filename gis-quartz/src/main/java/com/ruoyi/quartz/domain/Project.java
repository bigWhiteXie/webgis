package com.ruoyi.quartz.domain;

import java.util.Date;

/**
 * 项目对象 project
 */
public class Project {
    /** 自增主键 */
    private Long id;

    /** 项目编号 */
    private String projectCode;

    /** 项目类型 */
    private String projectType;

    /** 入库时间 */
    private Date storageTime;

    /** 负责人 */
    private String manager;

    /** 项目边界 */
    private String geom;

    /** 企业编号 */
    private String companyCode;

    /** 企业名称 */
    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}