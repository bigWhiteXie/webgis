package com.ruoyi.quartz.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("污染指标信息")
public  class PollutionMetric {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("指标编码（唯一标识，如G0013、G0026等标准化编码）")
    private String metricCode;

    @ApiModelProperty("指标简称（如铅、镉、COD等简短名称）")
    private String metricName;

    @ApiModelProperty("指标单位（如mg/L、μg/m³等度量单位）")
    private String unit;

    // 默认构造函数
    public PollutionMetric() {
    }

    // 带参数的构造函数
    public PollutionMetric(Long id, String metricCode, String indicatorName, String unit) {
        this.id = id;
        this.metricCode = metricCode;
        this.metricName = indicatorName;
        this.unit = unit;
    }

    // Getter和Setter方法
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "PollutionMetric{" +
                "id=" + id +
                ", indicatorCode='" + metricCode + '\'' +
                ", indicatorName='" + metricName + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
