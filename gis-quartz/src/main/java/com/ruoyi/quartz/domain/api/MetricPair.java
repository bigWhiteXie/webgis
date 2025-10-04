package com.ruoyi.quartz.domain.api;

public class MetricPair {
    private String metricCode;
    private String metricName;

    // 默认构造函数
    public MetricPair() {
    }

    // 带参数的构造函数
    public MetricPair(String metricCode, String metricName) {
        this.metricCode = metricCode;
        this.metricName = metricName;
    }

    // getter和setter方法
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

    @Override
    public String toString() {
        return "MetricPair{" +
                "metricCode='" + metricCode + '\'' +
                ", metricName='" + metricName + '\'' +
                '}';
    }
}