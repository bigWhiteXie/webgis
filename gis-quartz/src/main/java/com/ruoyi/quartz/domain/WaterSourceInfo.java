package com.ruoyi.quartz.domain;

import java.util.Date;

/**
 * 地下水型饮用水水源基础信息对象 water_source_info
 */
public class WaterSourceInfo {
    /** 水源ID */
    private Long sourceId;

    /** 序号 */
    private Integer serialNumber;

    /** 项目编号 */
    private String projectId;

    /** 省份代码 */
    private String provinceCode;

    /** 城市代码 */
    private String cityCode;

    /** 区县代码 */
    private String countyCode;

    /** 水源名称 */
    private String sourceName;

    /** 水源编码 */
    private String sourceCode;

    /** 中心经度 */
    private Double centerLongitude;

    /** 中心中纬度 */
    private Double centerLatitude;

    /** 水源级别 */
    private String sourceLevel;

    /** 供水规模 */
    private Double waterSupplyScale;

    /** 服务人口 */
    private Double servicePopulation;

    /** 水质类别 */
    private Integer waterQualityCategory;

    /** 水质类别时间 */
    private Date waterQualityCategoryTime;

    /** 埋藏条件 */
    private String burialCondition;

    /** 含水层介质类型 */
    private String aquiferMediumType;

    /** 是否划定保护区 */
    private Boolean isProtectionZoneDelineated;

    /** 一级保护区面积 */
    private Double firstLevelProtectionZoneArea;

    /** 一级保护区污染源数量 */
    private Integer firstLevelProtectionZonePollutionSourceCount;

    /** 二级保护区面积 */
    private Double secondLevelProtectionZoneArea;

    /** 二级保护区污染源数量 */
    private Integer secondLevelProtectionZonePollutionSourceCount;

    /** 准保护区面积 */
    private Double quasiProtectionZoneArea;

    /** 准保护区污染源数量 */
    private Integer quasiProtectionZonePollutionSourceCount;

    /** 是否划定补给区 */
    private Boolean isRechargeAreaDelineated;

    /** 补给区面积 */
    private Double rechargeAreaArea;

    /** 补给区污染源数量 */
    private Integer rechargeAreaPollutionSourceCount;

    /** 附近是否有地下水污染源 */
    private Boolean isGroundwaterPollutionSourceNearby;

    /** 附近地下水污染源数量 */
    private Integer groundwaterPollutionSourceCountNearby;

    /** 是否优先控制 */
    private Boolean isPriorityControlled;

    /** 优先控制原因 */
    private String priorityControlReason;

    /** 是否超标 */
    private Boolean isExceedingStandard;

    /** 超标原因 */
    private String exceedingStandardReason;

    /** 人为因素超标指标 */
    private String humanFactorExceedingStandardIndex;

    /** 人为因素超标原因 */
    private String humanFactorExceedingStandardReason;

    /** 已实施的控制措施 */
    private String implementedControlMeasures;

    /** 当前水质状况 */
    private String currentWaterQualityStatus;

    /** 拟采取的控制措施 */
    private String plannedControlMeasures;

    /** 预期达标时间 */
    private Date expectedStandardReachingTime;

    /** 供水是否达标 */
    private Boolean isWaterSupplyUpToStandard;

    /** 空间数据 */
    private String geom;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public String getSourceLevel() {
        return sourceLevel;
    }

    public void setSourceLevel(String sourceLevel) {
        this.sourceLevel = sourceLevel;
    }

    public Double getWaterSupplyScale() {
        return waterSupplyScale;
    }

    public void setWaterSupplyScale(Double waterSupplyScale) {
        this.waterSupplyScale = waterSupplyScale;
    }

    public Double getServicePopulation() {
        return servicePopulation;
    }

    public void setServicePopulation(Double servicePopulation) {
        this.servicePopulation = servicePopulation;
    }

    public Integer getWaterQualityCategory() {
        return waterQualityCategory;
    }

    public void setWaterQualityCategory(Integer waterQualityCategory) {
        this.waterQualityCategory = waterQualityCategory;
    }

    public Date getWaterQualityCategoryTime() {
        return waterQualityCategoryTime;
    }

    public void setWaterQualityCategoryTime(Date waterQualityCategoryTime) {
        this.waterQualityCategoryTime = waterQualityCategoryTime;
    }

    public String getBurialCondition() {
        return burialCondition;
    }

    public void setBurialCondition(String burialCondition) {
        this.burialCondition = burialCondition;
    }

    public String getAquiferMediumType() {
        return aquiferMediumType;
    }

    public void setAquiferMediumType(String aquiferMediumType) {
        this.aquiferMediumType = aquiferMediumType;
    }

    public Boolean getIsProtectionZoneDelineated() {
        return isProtectionZoneDelineated;
    }

    public void setIsProtectionZoneDelineated(Boolean isProtectionZoneDelineated) {
        this.isProtectionZoneDelineated = isProtectionZoneDelineated;
    }

    public Double getFirstLevelProtectionZoneArea() {
        return firstLevelProtectionZoneArea;
    }

    public void setFirstLevelProtectionZoneArea(Double firstLevelProtectionZoneArea) {
        this.firstLevelProtectionZoneArea = firstLevelProtectionZoneArea;
    }

    public Integer getFirstLevelProtectionZonePollutionSourceCount() {
        return firstLevelProtectionZonePollutionSourceCount;
    }

    public void setFirstLevelProtectionZonePollutionSourceCount(Integer firstLevelProtectionZonePollutionSourceCount) {
        this.firstLevelProtectionZonePollutionSourceCount = firstLevelProtectionZonePollutionSourceCount;
    }

    public Double getSecondLevelProtectionZoneArea() {
        return secondLevelProtectionZoneArea;
    }

    public void setSecondLevelProtectionZoneArea(Double secondLevelProtectionZoneArea) {
        this.secondLevelProtectionZoneArea = secondLevelProtectionZoneArea;
    }

    public Integer getSecondLevelProtectionZonePollutionSourceCount() {
        return secondLevelProtectionZonePollutionSourceCount;
    }

    public void setSecondLevelProtectionZonePollutionSourceCount(Integer secondLevelProtectionZonePollutionSourceCount) {
        this.secondLevelProtectionZonePollutionSourceCount = secondLevelProtectionZonePollutionSourceCount;
    }

    public Double getQuasiProtectionZoneArea() {
        return quasiProtectionZoneArea;
    }

    public void setQuasiProtectionZoneArea(Double quasiProtectionZoneArea) {
        this.quasiProtectionZoneArea = quasiProtectionZoneArea;
    }

    public Integer getQuasiProtectionZonePollutionSourceCount() {
        return quasiProtectionZonePollutionSourceCount;
    }

    public void setQuasiProtectionZonePollutionSourceCount(Integer quasiProtectionZonePollutionSourceCount) {
        this.quasiProtectionZonePollutionSourceCount = quasiProtectionZonePollutionSourceCount;
    }

    public Boolean getIsRechargeAreaDelineated() {
        return isRechargeAreaDelineated;
    }

    public void setIsRechargeAreaDelineated(Boolean isRechargeAreaDelineated) {
        this.isRechargeAreaDelineated = isRechargeAreaDelineated;
    }

    public Double getRechargeAreaArea() {
        return rechargeAreaArea;
    }

    public void setRechargeAreaArea(Double rechargeAreaArea) {
        this.rechargeAreaArea = rechargeAreaArea;
    }

    public Integer getRechargeAreaPollutionSourceCount() {
        return rechargeAreaPollutionSourceCount;
    }

    public void setRechargeAreaPollutionSourceCount(Integer rechargeAreaPollutionSourceCount) {
        this.rechargeAreaPollutionSourceCount = rechargeAreaPollutionSourceCount;
    }

    public Boolean getIsGroundwaterPollutionSourceNearby() {
        return isGroundwaterPollutionSourceNearby;
    }

    public void setIsGroundwaterPollutionSourceNearby(Boolean isGroundwaterPollutionSourceNearby) {
        this.isGroundwaterPollutionSourceNearby = isGroundwaterPollutionSourceNearby;
    }

    public Integer getGroundwaterPollutionSourceCountNearby() {
        return groundwaterPollutionSourceCountNearby;
    }

    public void setGroundwaterPollutionSourceCountNearby(Integer groundwaterPollutionSourceCountNearby) {
        this.groundwaterPollutionSourceCountNearby = groundwaterPollutionSourceCountNearby;
    }

    public Boolean getIsPriorityControlled() {
        return isPriorityControlled;
    }

    public void setIsPriorityControlled(Boolean isPriorityControlled) {
        this.isPriorityControlled = isPriorityControlled;
    }

    public String getPriorityControlReason() {
        return priorityControlReason;
    }

    public void setPriorityControlReason(String priorityControlReason) {
        this.priorityControlReason = priorityControlReason;
    }

    public Boolean getIsExceedingStandard() {
        return isExceedingStandard;
    }

    public void setIsExceedingStandard(Boolean isExceedingStandard) {
        this.isExceedingStandard = isExceedingStandard;
    }

    public String getExceedingStandardReason() {
        return exceedingStandardReason;
    }

    public void setExceedingStandardReason(String exceedingStandardReason) {
        this.exceedingStandardReason = exceedingStandardReason;
    }

    public String getHumanFactorExceedingStandardIndex() {
        return humanFactorExceedingStandardIndex;
    }

    public void setHumanFactorExceedingStandardIndex(String humanFactorExceedingStandardIndex) {
        this.humanFactorExceedingStandardIndex = humanFactorExceedingStandardIndex;
    }

    public String getHumanFactorExceedingStandardReason() {
        return humanFactorExceedingStandardReason;
    }

    public void setHumanFactorExceedingStandardReason(String humanFactorExceedingStandardReason) {
        this.humanFactorExceedingStandardReason = humanFactorExceedingStandardReason;
    }

    public String getImplementedControlMeasures() {
        return implementedControlMeasures;
    }

    public void setImplementedControlMeasures(String implementedControlMeasures) {
        this.implementedControlMeasures = implementedControlMeasures;
    }

    public String getCurrentWaterQualityStatus() {
        return currentWaterQualityStatus;
    }

    public void setCurrentWaterQualityStatus(String currentWaterQualityStatus) {
        this.currentWaterQualityStatus = currentWaterQualityStatus;
    }

    public String getPlannedControlMeasures() {
        return plannedControlMeasures;
    }

    public void setPlannedControlMeasures(String plannedControlMeasures) {
        this.plannedControlMeasures = plannedControlMeasures;
    }

    public Date getExpectedStandardReachingTime() {
        return expectedStandardReachingTime;
    }

    public void setExpectedStandardReachingTime(Date expectedStandardReachingTime) {
        this.expectedStandardReachingTime = expectedStandardReachingTime;
    }

    public Boolean getIsWaterSupplyUpToStandard() {
        return isWaterSupplyUpToStandard;
    }

    public void setIsWaterSupplyUpToStandard(Boolean isWaterSupplyUpToStandard) {
        this.isWaterSupplyUpToStandard = isWaterSupplyUpToStandard;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
}
