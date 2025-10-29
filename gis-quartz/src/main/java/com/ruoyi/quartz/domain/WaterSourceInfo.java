package com.ruoyi.quartz.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.excel.util.ListUtils;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 地下水型饮用水水源基础信息对象 water_source_info
 */
public class WaterSourceInfo {
    private static final Logger log = LoggerFactory.getLogger(WaterSourceInfo.class);

    /** 水源ID */
    private Integer sourceId;

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

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("县名称")
    private String countyName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    /**
     * 解析Excel数据为WaterSourceInfo对象列表
     *
     * @param data Excel数据列表
     * @return WaterSourceInfo对象列表
     */
    public static List<WaterSourceInfo> parseExcelData(List<Object> data) throws RuntimeException {
        List<WaterSourceInfo> waterSourceInfos = ListUtils.newArrayList();

        // 遍历所有行数据
        for (Object rowData : data) {
            try {
                // 检查是否至少有40列（根据Mapper中的字段数量估算）
                if (rowData instanceof LinkedHashMap) {
                    LinkedHashMap<Integer, Object> rowMap = (LinkedHashMap<Integer, Object>) rowData;
                    WaterSourceInfo waterSourceInfo = new WaterSourceInfo();

                    // 按照WaterSourceInfo的属性顺序设置值（跳过id字段，由数据库自增）
                    // 根据列索引获取值，而不是根据属性名
                    waterSourceInfo.setSourceId(getIntegerValue(rowMap.get(0)));
                    waterSourceInfo.setProjectId(getStringValue(rowMap.get(1)));
                    waterSourceInfo.setProvinceCode(getStringValue(rowMap.get(2)));
                    waterSourceInfo.setCityCode(getStringValue(rowMap.get(3)));
                    waterSourceInfo.setCountyCode(getStringValue(rowMap.get(4)));
                    waterSourceInfo.setSourceName(getStringValue(rowMap.get(5)));
                    waterSourceInfo.setSourceCode(getStringValue(rowMap.get(6)));

                    // 处理经纬度，将BigDecimal转换为Double
                    BigDecimal longitudeValue = getBigDecimalValue(rowMap.get(7));
                    BigDecimal latitudeValue = getBigDecimalValue(rowMap.get(8));
                    if (longitudeValue != null) {
                        waterSourceInfo.setCenterLongitude(longitudeValue.doubleValue());
                    }
                    if (latitudeValue != null) {
                        waterSourceInfo.setCenterLatitude(latitudeValue.doubleValue());
                    }

                    waterSourceInfo.setSourceLevel(getStringValue(rowMap.get(9)));

                    // 处理BigDecimal类型字段
                    BigDecimal waterSupplyScaleValue = getBigDecimalValue(rowMap.get(10));
                    if (waterSupplyScaleValue != null) {
                        waterSourceInfo.setWaterSupplyScale(waterSupplyScaleValue.doubleValue());
                    }

                    BigDecimal servicePopulationValue = getBigDecimalValue(rowMap.get(11));
                    if (servicePopulationValue != null) {
                        waterSourceInfo.setServicePopulation(servicePopulationValue.doubleValue());
                    }

                    waterSourceInfo.setWaterQualityCategory(getIntegerValue(rowMap.get(12)));
                    waterSourceInfo.setWaterQualityCategoryTime(parseDate(getStringValue(rowMap.get(13))));
                    waterSourceInfo.setBurialCondition(getStringValue(rowMap.get(14)));
                    waterSourceInfo.setAquiferMediumType(getStringValue(rowMap.get(16)));
                    waterSourceInfo.setIsProtectionZoneDelineated(getBooleanValue(rowMap.get(17)));

                    // 处理BigDecimal类型字段
                    BigDecimal firstLevelProtectionZoneAreaValue = getBigDecimalValue(rowMap.get(18));
                    if (firstLevelProtectionZoneAreaValue != null) {
                        waterSourceInfo.setFirstLevelProtectionZoneArea(firstLevelProtectionZoneAreaValue.doubleValue());
                    }

                    waterSourceInfo.setFirstLevelProtectionZonePollutionSourceCount(getIntegerValue(rowMap.get(19)));

                    BigDecimal secondLevelProtectionZoneAreaValue = getBigDecimalValue(rowMap.get(20));
                    if (secondLevelProtectionZoneAreaValue != null) {
                        waterSourceInfo.setSecondLevelProtectionZoneArea(secondLevelProtectionZoneAreaValue.doubleValue());
                    }

                    waterSourceInfo.setSecondLevelProtectionZonePollutionSourceCount(getIntegerValue(rowMap.get(21)));

                    BigDecimal quasiProtectionZoneAreaValue = getBigDecimalValue(rowMap.get(22));
                    if (quasiProtectionZoneAreaValue != null) {
                        waterSourceInfo.setQuasiProtectionZoneArea(quasiProtectionZoneAreaValue.doubleValue());
                    }

                    waterSourceInfo.setQuasiProtectionZonePollutionSourceCount(getIntegerValue(rowMap.get(23)));
                    waterSourceInfo.setIsRechargeAreaDelineated(getBooleanValue(rowMap.get(24)));

                    BigDecimal rechargeAreaAreaValue = getBigDecimalValue(rowMap.get(25));
                    if (rechargeAreaAreaValue != null) {
                        waterSourceInfo.setRechargeAreaArea(rechargeAreaAreaValue.doubleValue());
                    }

                    waterSourceInfo.setRechargeAreaPollutionSourceCount(getIntegerValue(rowMap.get(26)));
                    waterSourceInfo.setIsGroundwaterPollutionSourceNearby(getBooleanValue(rowMap.get(27)));
                    waterSourceInfo.setGroundwaterPollutionSourceCountNearby(getIntegerValue(rowMap.get(28)));
                    waterSourceInfo.setIsPriorityControlled(getBooleanValue(rowMap.get(29)));
                    waterSourceInfo.setPriorityControlReason(getStringValue(rowMap.get(30)));
                    waterSourceInfo.setIsExceedingStandard(getBooleanValue(rowMap.get(31)));
                    waterSourceInfo.setExceedingStandardReason(getStringValue(rowMap.get(32)));
                    waterSourceInfo.setHumanFactorExceedingStandardIndex(getStringValue(rowMap.get(33)));
                    waterSourceInfo.setHumanFactorExceedingStandardReason(getStringValue(rowMap.get(34)));
                    waterSourceInfo.setImplementedControlMeasures(getStringValue(rowMap.get(35)));
                    waterSourceInfo.setCurrentWaterQualityStatus(getStringValue(rowMap.get(36)));
                    waterSourceInfo.setPlannedControlMeasures(getStringValue(rowMap.get(37)));
                    waterSourceInfo.setExpectedStandardReachingTime(parseDate(getStringValue(rowMap.get(38))));
                    waterSourceInfo.setIsWaterSupplyUpToStandard(getBooleanValue(rowMap.get(39)));

                    // 设置geom字段为WKT格式
                    if (waterSourceInfo.getCenterLongitude() != null && waterSourceInfo.getCenterLatitude() != null) {
                        waterSourceInfo.setGeom("POINT(" + waterSourceInfo.getCenterLongitude() + " " + waterSourceInfo.getCenterLatitude() + ")");
                    } else {
                        log.warn("无法获取水源的经纬度信息，数据：" + rowData);
                        continue;
                    }

                    waterSourceInfos.add(waterSourceInfo);
                }
            } catch (Exception e) {
                log.warn("解析Excel行数据失败: " + e.getMessage() + "，数据：" + rowData);
            }
        }

        return waterSourceInfos;
    }

    /**
     * 获取字符串值
     *
     * @param value 原始值
     * @return 字符串值
     */
    private static String getStringValue(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString().trim();
    }

    /**
     * 获取Integer值
     *
     * @param value 原始值
     * @return Integer值
     */
    private static Integer getIntegerValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            String strValue = value.toString().trim();
            if ("是".equals(strValue) || "Y".equalsIgnoreCase(strValue) || "true".equalsIgnoreCase(strValue)) {
                return 1;
            } else if ("否".equals(strValue) || "N".equalsIgnoreCase(strValue) || "false".equalsIgnoreCase(strValue)) {
                return 0;
            } else if (strValue.isEmpty()) {
                return null;
            } else {
                try {
                    return Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
    }

    /**
     * 获取BigDecimal值
     *
     * @param value 原始值
     * @return BigDecimal值
     * @throws NumberFormatException 当值无法转换为BigDecimal时
     */
    private static BigDecimal getBigDecimalValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof String) {
            String strValue = ((String) value).trim();
            if (strValue.isEmpty()) {
                return null;
            }
            return new BigDecimal(strValue);
        }
        throw new NumberFormatException("无法将值转换为BigDecimal: " + value);
    }

    /**
     * 解析日期字符串为Date对象
     * 支持多种格式: yyyy-MM-dd, yyyy/MM/dd, yyyy.MM.dd
     *
     * @param value 日期字符串
     * @return Date对象
     */
    private static Date parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String trimmedValue = value.trim();
        // 尝试不同的日期格式
        String[] datePatterns = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"};
        
        for (String pattern : datePatterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.parse(trimmedValue);
            } catch (ParseException e) {
                // 继续尝试下一个格式
            }
        }
        
        System.err.println("无法解析日期: " + value);
        return null;
    }

    /**
     * 获取Boolean值
     *
     * @param value 原始值
     * @return Boolean值
     */
    private static Boolean getBooleanValue(Object value) {
        if (value == null) {
            return null;
        }
        
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0;
        } else {
            String strValue = value.toString().toLowerCase();
            return "是".equals(strValue) || "y".equals(strValue) || "true".equals(strValue);
        }
    }
}