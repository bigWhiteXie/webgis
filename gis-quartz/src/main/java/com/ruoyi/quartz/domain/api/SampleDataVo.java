package com.ruoyi.quartz.domain.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel("监测井指标检测数据")
public class SampleDataVo {
    // 监测井编码
    @ApiModelProperty(value = "监测井编码", required = true)
    @NotBlank(message = "监测井编码不能为空")
    private String monitoringWellCode;

    // 采样时间
    @ApiModelProperty(value = "采样时间", required = true)
    @NotNull(message = "采样时间不能为空")
    private Date samplingTime;

    // 指标值列表
    @ApiModelProperty(value = "指标值列表")
    private List<MetricValItem> metricValues;

    // 质量等级

    // 默认构造函数
    public SampleDataVo() {
    }

    // 带参数的构造函数
    public SampleDataVo(String monitoringWellCode, Date samplingTime, List<MetricValItem> metricValues) {
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

    @Override
    public String toString() {
        return "SampleDataResp{" +
                "monitoringWellCode='" + monitoringWellCode + '\'' +
                ", samplingTime=" + samplingTime +
                ", metricValues=" + metricValues +
                '}';
    }
}