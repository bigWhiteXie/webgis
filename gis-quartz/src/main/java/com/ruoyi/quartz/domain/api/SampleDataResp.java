package com.ruoyi.quartz.domain.api;

import java.util.Date;
import java.util.List;

public class SampleDataResp {
    // 监测井编码
    private String monitoringWellCode;
    
    // 采样时间
    private Date samplingTime;

    // 样品编码
    private String sampleCode;

    private String qualityLevel;
    
    // 水质等级对应的指标编码
    private String metricsCode;
    
    // 水质等级对应的指标名称
    private String metricsName;

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
    
    public String getSampleCode() {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
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
    
    public String getMetricsCode() {
        return metricsCode;
    }
    
    public void setMetricsCode(String metricsCode) {
        this.metricsCode = metricsCode;
    }
    
    public String getMetricsName() {
        return metricsName;
    }
    
    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }
    
    @Override
    public String toString() {
        return "SampleDataResp{" +
                "monitoringWellCode='" + monitoringWellCode + '\'' +
                ", samplingTime=" + samplingTime +
                ", sampleCode='" + sampleCode + '\'' +
                ", metricValues=" + metricValues +
                ", qualityLevel='" + qualityLevel + '\'' +
                ", metricsCode='" + metricsCode + '\'' +
                ", metricsName='" + metricsName + '\'' +
                '}';
    }
}