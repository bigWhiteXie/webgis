package com.ruoyi.quartz.domain.api;

import java.util.List;
import java.util.Date;

public class SampleDataResp {
    // 监测井编码
    private String monitoringWellCode;
    
    // 采样时间
    private Date samplingTime;

    private String qualityLevel;

    // 指标值列表
    private List<MetricValItem> metricValues;
    
    // 质量等级

    // 默认构造函数
    public SampleDataResp() {
    }
    
    // 带参数的构造函数
    public SampleDataResp(String monitoringWellCode, Date samplingTime, List<MetricValItem> metricValues) {
        this.monitoringWellCode = monitoringWellCode;
        this.samplingTime = samplingTime;
        this.metricValues = metricValues;
    }
    
    // Getter和Setter方法
    public String getMonitoringWellCode() {
        return monitoringWellCode;
    }
    
    public void setMonitoringWellCode(String monitoringWellCode) {
        this.monitoringWellCode = monitoringWellCode;
    }
    
    public Date getSamplingTime() {
        return samplingTime;
    }
    
    public void setSamplingTime(Date samplingTime) {
        this.samplingTime = samplingTime;
    }
    
    public List<MetricValItem> getMetricValues() {
        return metricValues;
    }
    
    public void setMetricValues(List<MetricValItem> metricValues) {
        this.metricValues = metricValues;
    }
    
    public String getQualityLevel() {
        return qualityLevel;
    }
    
    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel;
    }
    
    @Override
    public String toString() {
        return "SampleDataResp{" +
                "monitoringWellCode='" + monitoringWellCode + '\'' +
                ", samplingTime=" + samplingTime +
                ", metricValues=" + metricValues +
                ", qualityLevel='" + qualityLevel + '\'' +
                '}';
    }
}