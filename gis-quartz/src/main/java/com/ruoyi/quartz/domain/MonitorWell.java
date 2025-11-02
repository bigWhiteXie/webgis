package com.ruoyi.quartz.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("监测井信息")
public class MonitorWell {
    private static final Logger log = LoggerFactory.getLogger(MonitorWell.class);
    
    // 主键ID
    @ApiModelProperty("主键ID")
    @ExcelProperty("序号")
    @Excel(name = "序号")
    private Long id;
    
    // 项目ID
    @ExcelProperty("项目ID")
    @Excel(name = "项目ID")
    @ApiModelProperty("项目ID")
    private String projectId;
    
    // 省代码
    @ExcelProperty("省代码")
    @Excel(name = "省代码")
    @ApiModelProperty("省代码")
    private String provinceCode;
    
    // 市代码
    @ExcelProperty("市代码")
    @Excel(name = "市代码")
    @ApiModelProperty("市代码")
    private String cityCode;
    
    // 县代码
    @ExcelProperty("县代码")
    @Excel(name = "县代码")
    @ApiModelProperty("县代码")
    private String countyCode;
    
    // 监测井编码
    @ExcelProperty("监测井编码")
    @Excel(name = "监测井编码")
    @ApiModelProperty("监测井编码")
    private String wellCode;
    
    // 经度
    @ExcelProperty("经度")
    @NumberFormat("#.######")
    @Excel(name = "经度")
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    
    // 纬度
    @ExcelProperty("纬度")
    @NumberFormat("#.######")
    @Excel(name = "纬度")
    @ApiModelProperty("纬度")
    private BigDecimal latitude;
    
    // 是否为调查评估项目新建井
    @ExcelProperty("是否为调查评估项目新建井")
    @Excel(name = "是否为调查评估项目新建井")
    @ApiModelProperty("是否为调查评估项目新建井")
    private Boolean isNewWell;
    
    // 是否为区域监测点
    @ExcelProperty("是否为区域监测点")
    @Excel(name = "是否为区域监测点")
    @ApiModelProperty("是否为区域监测点")
    private Boolean isAreaMonitoringPoint;
    
    // 区域监测点类型
    @ExcelProperty("区域监测点类型")
    @Excel(name = "区域监测点类型")
    @ApiModelProperty("区域监测点类型")
    private String areaMonitoringPointType;
    
    // 是否为水源监测点
    @ExcelProperty("是否为水源监测点")
    @Excel(name = "是否为水源监测点")
    @ApiModelProperty("是否为水源监测点")
    private Boolean isWaterSourceMonitoringPoint;
    
    // 水源信息
    @ExcelProperty("水源信息")
    @Excel(name = "水源信息")
    @ApiModelProperty("水源信息")
    private String waterSourceInfo;
    
    // 是否为污染源监测点
    @ExcelProperty("是否为污染源监测点")
    @Excel(name = "是否为污染源监测点")
    @ApiModelProperty("是否为污染源监测点")
    private Boolean isPollutionSourceMonitoringPoint;
    
    // 污染源信息
    @ExcelProperty("污染源信息")
    @Excel(name = "污染源信息")
    @ApiModelProperty("污染源信息")
    private String pollutionSourceInfo;
    
    // 成井时间
    @ExcelProperty("成井时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成井时间", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("成井时间")
    private Date completionTime;
    
    // 水位埋深
    @ExcelProperty("水位埋深")
    @NumberFormat("#.##")
    @Excel(name = "水位埋深")
    @ApiModelProperty("水位埋深")
    private BigDecimal waterLevelDepth;
    
    // 井口高程
    @ExcelProperty("井口高程")
    @NumberFormat("#.##")
    @Excel(name = "井口高程")
    @ApiModelProperty("井口高程")
    private BigDecimal wellheadElevation;
    
    // 井深度
    @ExcelProperty("井深度")
    @NumberFormat("#.##")
    @Excel(name = "井深度")
    @ApiModelProperty("井深度")
    private BigDecimal wellDepth;
    
    // 井口内径
    @ExcelProperty("井口内径")
    @NumberFormat("#.##")
    @Excel(name = "井口内径")
    @ApiModelProperty("井口内径")
    private BigDecimal wellheadInnerDiameter;
    
    // 井管材质
    @ExcelProperty("井管材质")
    @Excel(name = "井管材质")
    @ApiModelProperty("井管材质")
    private String wellPipeMaterial;
    
    // 是否为多段筛管
    @ExcelProperty("是否为多段筛管")
    @Excel(name = "是否为多段筛管")
    @ApiModelProperty("是否为多段筛管")
    private Boolean isMultipleScreenPipe;
    
    // 筛管深度
    @ExcelProperty("筛管深度")
    @Excel(name = "筛管深度")
    @ApiModelProperty("筛管深度")
    private String screenPipeDepth;
    
    // 埋藏条件
    @ExcelProperty("埋藏条件")
    @Excel(name = "埋藏条件")
    @ApiModelProperty("埋藏条件")
    private String burialCondition;
    
    // 含水介质
    @ExcelProperty("含水介质")
    @Excel(name = "含水介质")
    @ApiModelProperty("含水介质")
    private String aquiferMedium;
    
    // 井权属单位
    @ExcelProperty("井权属单位")
    @Excel(name = "井权属单位")
    @ApiModelProperty("井权属单位")
    private String wellOwnershipUnit;
    
    // 是否符合长期监测井要求
    @ExcelProperty("是否符合长期监测井要求")
    @Excel(name = "是否符合长期监测井要求")
    @ApiModelProperty("是否符合长期监测井要求")
    private Boolean isSuitableForLongTermMonitoring;
    
    // 是否已转为长期监测井
    @ExcelProperty("是否已转为长期监测井")
    @Excel(name = "是否已转为长期监测井")
    @ApiModelProperty("是否已转为长期监测井")
    private Boolean isConvertedToLongTermMonitoring;
    
    // 是否开展了监测井维护管理工作
    @ExcelProperty("是否开展了监测井维护管理工作")
    @Excel(name = "是否开展了监测井维护管理工作")
    @ApiModelProperty("是否开展了监测井维护管理工作")
    private Boolean isMaintenanceManagementCarriedOut;
    
    // 实际维护管理单位
    @ExcelProperty("实际维护管理单位")
    @Excel(name = "实际维护管理单位")
    @ApiModelProperty("实际维护管理单位")
    private String actualMaintenanceManagementUnit;
    
    // 对于非长期监测井是否已进行封井回填
    @ExcelProperty("对于非长期监测井是否已进行封井回填")
    @Excel(name = "对于非长期监测井是否已进行封井回填")
    @ApiModelProperty("对于非长期监测井是否已进行封井回填")
    private Boolean isSealedBackfilledForNonLongTerm;
    
    // 封井回填状态
    @ExcelProperty("封井回填状态")
    @Excel(name = "封井回填状态")
    @ApiModelProperty("封井回填状态")
    private String sealedBackfilledStatus;
    
    // 图片链接

    private String imageUrl;
    
    // 几何对象(位置信息)
    @ApiModelProperty("几何对象(位置信息)")
    private String geom;
    
    // 省份名称
    @ExcelProperty("省份名称")
    @ApiModelProperty("省份名称")
    private String provinceName;
    
    // 地市名称
    @ExcelProperty("地市名称")
    @ApiModelProperty("地市名称")
    private String cityName;
    
    // 区县名称
    @ExcelProperty("区县名称")
    @ApiModelProperty("区县名称")
    private String countyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getWellCode() {
        return wellCode;
    }

    public void setWellCode(String wellCode) {
        this.wellCode = wellCode;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Boolean getIsNewWell() {
        return isNewWell;
    }

    public void setIsNewWell(Boolean isNewWell) {
        this.isNewWell = isNewWell;
    }

    public Boolean getIsAreaMonitoringPoint() {
        return isAreaMonitoringPoint;
    }

    public void setIsAreaMonitoringPoint(Boolean isAreaMonitoringPoint) {
        this.isAreaMonitoringPoint = isAreaMonitoringPoint;
    }

    public String getAreaMonitoringPointType() {
        return areaMonitoringPointType;
    }

    public void setAreaMonitoringPointType(String areaMonitoringPointType) {
        this.areaMonitoringPointType = areaMonitoringPointType;
    }

    public Boolean getIsWaterSourceMonitoringPoint() {
        return isWaterSourceMonitoringPoint;
    }

    public void setIsWaterSourceMonitoringPoint(Boolean isWaterSourceMonitoringPoint) {
        this.isWaterSourceMonitoringPoint = isWaterSourceMonitoringPoint;
    }

    public String getWaterSourceInfo() {
        return waterSourceInfo;
    }

    public void setWaterSourceInfo(String waterSourceInfo) {
        this.waterSourceInfo = waterSourceInfo;
    }

    public Boolean getIsPollutionSourceMonitoringPoint() {
        return isPollutionSourceMonitoringPoint;
    }

    public void setIsPollutionSourceMonitoringPoint(Boolean isPollutionSourceMonitoringPoint) {
        this.isPollutionSourceMonitoringPoint = isPollutionSourceMonitoringPoint;
    }

    public String getPollutionSourceInfo() {
        return pollutionSourceInfo;
    }

    public void setPollutionSourceInfo(String pollutionSourceInfo) {
        this.pollutionSourceInfo = pollutionSourceInfo;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public BigDecimal getWaterLevelDepth() {
        return waterLevelDepth;
    }

    public void setWaterLevelDepth(BigDecimal waterLevelDepth) {
        this.waterLevelDepth = waterLevelDepth;
    }

    public BigDecimal getWellheadElevation() {
        return wellheadElevation;
    }

    public void setWellheadElevation(BigDecimal wellheadElevation) {
        this.wellheadElevation = wellheadElevation;
    }

    public BigDecimal getWellDepth() {
        return wellDepth;
    }

    public void setWellDepth(BigDecimal wellDepth) {
        this.wellDepth = wellDepth;
    }

    public BigDecimal getWellheadInnerDiameter() {
        return wellheadInnerDiameter;
    }

    public void setWellheadInnerDiameter(BigDecimal wellheadInnerDiameter) {
        this.wellheadInnerDiameter = wellheadInnerDiameter;
    }

    public String getWellPipeMaterial() {
        return wellPipeMaterial;
    }

    public void setWellPipeMaterial(String wellPipeMaterial) {
        this.wellPipeMaterial = wellPipeMaterial;
    }

    public Boolean getIsMultipleScreenPipe() {
        return isMultipleScreenPipe;
    }

    public void setIsMultipleScreenPipe(Boolean isMultipleScreenPipe) {
        this.isMultipleScreenPipe = isMultipleScreenPipe;
    }

    public String getScreenPipeDepth() {
        return screenPipeDepth;
    }

    public void setScreenPipeDepth(String screenPipeDepth) {
        this.screenPipeDepth = screenPipeDepth;
    }

    public String getBurialCondition() {
        return burialCondition;
    }

    public void setBurialCondition(String burialCondition) {
        this.burialCondition = burialCondition;
    }

    public String getAquiferMedium() {
        return aquiferMedium;
    }

    public void setAquiferMedium(String aquiferMedium) {
        this.aquiferMedium = aquiferMedium;
    }

    public String getWellOwnershipUnit() {
        return wellOwnershipUnit;
    }

    public void setWellOwnershipUnit(String wellOwnershipUnit) {
        this.wellOwnershipUnit = wellOwnershipUnit;
    }

    public Boolean getIsSuitableForLongTermMonitoring() {
        return isSuitableForLongTermMonitoring;
    }

    public void setIsSuitableForLongTermMonitoring(Boolean isSuitableForLongTermMonitoring) {
        this.isSuitableForLongTermMonitoring = isSuitableForLongTermMonitoring;
    }

    public Boolean getIsConvertedToLongTermMonitoring() {
        return isConvertedToLongTermMonitoring;
    }

    public void setIsConvertedToLongTermMonitoring(Boolean isConvertedToLongTermMonitoring) {
        this.isConvertedToLongTermMonitoring = isConvertedToLongTermMonitoring;
    }

    public Boolean getIsMaintenanceManagementCarriedOut() {
        return isMaintenanceManagementCarriedOut;
    }

    public void setIsMaintenanceManagementCarriedOut(Boolean isMaintenanceManagementCarriedOut) {
        this.isMaintenanceManagementCarriedOut = isMaintenanceManagementCarriedOut;
    }

    public String getActualMaintenanceManagementUnit() {
        return actualMaintenanceManagementUnit;
    }

    public void setActualMaintenanceManagementUnit(String actualMaintenanceManagementUnit) {
        this.actualMaintenanceManagementUnit = actualMaintenanceManagementUnit;
    }

    public Boolean getIsSealedBackfilledForNonLongTerm() {
        return isSealedBackfilledForNonLongTerm;
    }

    public void setIsSealedBackfilledForNonLongTerm(Boolean isSealedBackfilledForNonLongTerm) {
        this.isSealedBackfilledForNonLongTerm = isSealedBackfilledForNonLongTerm;
    }

    public String getSealedBackfilledStatus() {
        return sealedBackfilledStatus;
    }

    public void setSealedBackfilledStatus(String sealedBackfilledStatus) {
        this.sealedBackfilledStatus = sealedBackfilledStatus;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
    
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

    @Override
    public String toString() {
        return "MonitorWell{" +
                "id=" + id +
                ", projectId='" + projectId + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", countyCode='" + countyCode + '\'' +
                ", wellCode='" + wellCode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isNewWell=" + isNewWell +
                ", isAreaMonitoringPoint=" + isAreaMonitoringPoint +
                ", areaMonitoringPointType='" + areaMonitoringPointType + '\'' +
                ", isWaterSourceMonitoringPoint=" + isWaterSourceMonitoringPoint +
                ", waterSourceInfo='" + waterSourceInfo + '\'' +
                ", isPollutionSourceMonitoringPoint=" + isPollutionSourceMonitoringPoint +
                ", pollutionSourceInfo='" + pollutionSourceInfo + '\'' +
                ", completionTime=" + completionTime +
                ", waterLevelDepth=" + waterLevelDepth +
                ", wellheadElevation=" + wellheadElevation +
                ", wellDepth=" + wellDepth +
                ", wellheadInnerDiameter=" + wellheadInnerDiameter +
                ", wellPipeMaterial='" + wellPipeMaterial + '\'' +
                ", isMultipleScreenPipe=" + isMultipleScreenPipe +
                ", screenPipeDepth='" + screenPipeDepth + '\'' +
                ", burialCondition='" + burialCondition + '\'' +
                ", aquiferMedium='" + aquiferMedium + '\'' +
                ", wellOwnershipUnit='" + wellOwnershipUnit + '\'' +
                ", isSuitableForLongTermMonitoring=" + isSuitableForLongTermMonitoring +
                ", isConvertedToLongTermMonitoring=" + isConvertedToLongTermMonitoring +
                ", isMaintenanceManagementCarriedOut=" + isMaintenanceManagementCarriedOut +
                ", actualMaintenanceManagementUnit='" + actualMaintenanceManagementUnit + '\'' +
                ", isSealedBackfilledForNonLongTerm=" + isSealedBackfilledForNonLongTerm +
                ", sealedBackfilledStatus='" + sealedBackfilledStatus + '\'' +
                ", geom=" + geom +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyName='" + countyName + '\'' +
                '}';
    }
    
    /**
     * 解析Excel数据为MonitorWell对象列表
     * 
     * @param data Excel数据列表
     * @return MonitorWell对象列表
     */
    public static List<MonitorWell> parseExcelData(List<Object> data) {
        List<MonitorWell> monitorWells = ListUtils.newArrayList();
        
        // 遍历所有行数据
        for (Object rowData : data) {
            try {
                // 检查是否至少有32列
                if (rowData instanceof LinkedHashMap) {
                    LinkedHashMap<Integer, Object> rowMap = (LinkedHashMap<Integer, Object>) rowData;
                    if (rowMap.size() >= 32) {
                        MonitorWell monitorWell = new MonitorWell();
                        
                        // 按照MonitorWell的属性顺序设置值（跳过id字段，由数据库自增）
                        // 根据列索引获取值，而不是根据属性名
                        monitorWell.setProjectId(getStringValue(rowMap.get(1)));
                        monitorWell.setProvinceCode(getStringValue(rowMap.get(2)));
                        monitorWell.setCityCode(getStringValue(rowMap.get(3)));
                        monitorWell.setCountyCode(getStringValue(rowMap.get(4)));
                        monitorWell.setWellCode(getStringValue(rowMap.get(5)));

                        // 经纬度
                        monitorWell.setLongitude(getBigDecimalValue(rowMap.get(6)));
                        monitorWell.setLatitude(getBigDecimalValue(rowMap.get(7)));

                        // 设置geom字段为WKT格式
                        BigDecimal longitude = monitorWell.getLongitude();
                        BigDecimal latitude = monitorWell.getLatitude();
                        if (longitude == null || latitude == null) {
                            throw new Exception("经纬度不能为空");
                        }
                        monitorWell.setGeom("POINT(" + longitude + " " + latitude + ")");


                        // 是否为调查评估项目新建井
                        monitorWell.setIsNewWell(getBooleanValue(rowMap.get(8)));

                        // 监测井性质相关字段
                        monitorWell.setIsAreaMonitoringPoint(getBooleanValue(rowMap.get(9)));
                        monitorWell.setAreaMonitoringPointType(getStringValue(rowMap.get(10)));
                        monitorWell.setIsWaterSourceMonitoringPoint(getBooleanValue(rowMap.get(11)));
                        monitorWell.setWaterSourceInfo(getStringValue(rowMap.get(12)));
                        monitorWell.setIsPollutionSourceMonitoringPoint(getBooleanValue(rowMap.get(13)));
                        monitorWell.setPollutionSourceInfo(getStringValue(rowMap.get(14)));

                        // 成井信息
                        monitorWell.setCompletionTime(DateUtils.parseDate(getStringValue(rowMap.get(15))));
                        monitorWell.setWaterLevelDepth(getBigDecimalValue(rowMap.get(16)));
                        monitorWell.setWellheadElevation(getBigDecimalValue(rowMap.get(17)));
                        monitorWell.setWellDepth(getBigDecimalValue(rowMap.get(18)));
                        monitorWell.setWellheadInnerDiameter(getBigDecimalValue(rowMap.get(19)));
                        monitorWell.setWellPipeMaterial(getStringValue(rowMap.get(20)));
                        monitorWell.setIsMultipleScreenPipe(getBooleanValue(rowMap.get(21)));
                        monitorWell.setScreenPipeDepth(getStringValue(rowMap.get(22)));

                        // 地下水类型
                        monitorWell.setBurialCondition(getStringValue(rowMap.get(23)));
                        monitorWell.setAquiferMedium(getStringValue(rowMap.get(24)));

                        // 环境管理信息
                        monitorWell.setWellOwnershipUnit(getStringValue(rowMap.get(25)));
                        monitorWell.setIsSuitableForLongTermMonitoring(getBooleanValue(rowMap.get(26)));
                        monitorWell.setIsConvertedToLongTermMonitoring(getBooleanValue(rowMap.get(27)));
                        monitorWell.setIsMaintenanceManagementCarriedOut(getBooleanValue(rowMap.get(28)));
                        monitorWell.setActualMaintenanceManagementUnit(getStringValue(rowMap.get(29)));
                        monitorWell.setIsSealedBackfilledForNonLongTerm(getBooleanValue(rowMap.get(30)));
                        monitorWell.setSealedBackfilledStatus(getStringValue(rowMap.get(31)));
                        
                        monitorWells.add(monitorWell);
                    }
                }
            } catch (Exception e) {
                log.error("解析Excel行数据失败: {}，数据：{}", e.getMessage(),rowData, e);
            }
        }
        
        return monitorWells;
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
        return value.toString();
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
                return new BigDecimal((String) value);
        }
        throw new NumberFormatException("无法将值转换为BigDecimal: " + value);
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
    
    /**
     * 解析成井时间字符串为Date对象
     * 支持三种格式: yyyy, yyyy.MM, yyyy.MM.dd
     * 
     * @param value 成井时间字符串
     * @return Date对象
     */
    private static Date parseCompletionTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        String trimmedValue = value.trim();
        try {
            if (trimmedValue.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
                // yyyy.MM.dd格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                return sdf.parse(trimmedValue);
            } else if (trimmedValue.matches("\\d{4}\\.\\d{2}")) {
                // yyyy.MM格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
                return sdf.parse(trimmedValue);
            } else if (trimmedValue.matches("\\d{4}")) {
                // yyyy格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                return sdf.parse(trimmedValue);
            }
        } catch (ParseException e) {
            log.warn("无法解析成井时间: {}", value);
        }
        
        return null;
    }
}