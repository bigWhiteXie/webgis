package com.ruoyi.quartz.domain.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 监测井基础信息返回对象
 * 
 * @author ruoyi
 */
@ApiModel("监测井基础信息返回对象")
public class SimpleWellResp {
    
    /** 监测井编码 */
    @ApiModelProperty("监测井编码")
    private String wellCode;
    
    /** 经度 */
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    
    /** 纬度 */
    @ApiModelProperty("纬度")
    private BigDecimal latitude;
    
    /** 几何对象(位置信息) */
    @ApiModelProperty("几何对象(位置信息)")
    private String geom;
    
    /** 水质等级 */
    @ApiModelProperty("水质等级")
    private String waterQualityLevel;
    
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
    
    public String getGeom() {
        return geom;
    }
    
    public void setGeom(String geom) {
        this.geom = geom;
    }
    
    public String getWaterQualityLevel() {
        return waterQualityLevel;
    }
    
    public void setWaterQualityLevel(String waterQualityLevel) {
        this.waterQualityLevel = waterQualityLevel;
    }
}