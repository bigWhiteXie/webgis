package com.ruoyi.quartz.domain.api;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class MetricValItem {
    private Long id;
    private String metricCode;
    private String metricName;
    private String value;
    private String unit;
    private Date SamplingTime;
    private String level;
    
    // 默认构造函数
    public MetricValItem() {
    }
    
    // 带参数的构造函数
    public MetricValItem(String metricCode, String metricName, String value, String unit) {
        this.metricCode = metricCode;
        this.metricName = metricName;
        this.value = value;
        this.unit = unit;
    }
    
    // getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMetricCode() {
        return metricCode;
    }
    
    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode;
    }
    
    public String getMetricName() {
        return metricName;
    }
    
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getSamplingTime() {
        return SamplingTime;
    }

    public void setSamplingTime(Date samplingTime) {
        this.SamplingTime = samplingTime;
    }
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    @Override
    public String toString() {
        return "MetricValItem{" +
                "id=" + id +
                ", metricCode='" + metricCode + '\'' +
                ", metricName='" + metricName + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}