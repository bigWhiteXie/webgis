package com.ruoyi.quartz.domain;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 指标标准对象 metric_standard
 * TODO: 后续补充每个指标的计算逻辑
 * @author ruoyi
 * @date 2025-10-02
 */
@ApiModel("指标标准")
public class MetricStandard
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 指标编码 */
    private String metricCode;

    /** 阈值 */
    private BigDecimal thresholdValue;

    /** 质量等级 */
    private String qualityLevel;

    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getMetricCode()
    {
        return metricCode;
    }

    public void setMetricCode(String metricCode)
    {
        this.metricCode = metricCode;
    }

    public BigDecimal getThresholdValue()
    {
        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue)
    {
        this.thresholdValue = thresholdValue;
    }

    public String getQualityLevel()
    {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }
}