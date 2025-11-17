package com.ruoyi.quartz.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 污染物指标对象 pollution_metric
 * 
 * @author ruoyi
 */
@ApiModel("污染物指标")
public class PollutionMetric
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Excel(name = "主键ID")
    @ApiModelProperty("主键ID")
    private Long id;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty("指标编码")
    private String metricCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty("指标名称")
    private String metricName;

    /** 单位 */
    @Excel(name = "单位")
    @ApiModelProperty("单位")
    private String unit;

    /** 类别 */
    @Excel(name = "类别")
    @ApiModelProperty("类别")
    private String category;

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

    public String getMetricName()
    {
        return metricName;
    }

    public void setMetricName(String metricName)
    {
        this.metricName = metricName;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}