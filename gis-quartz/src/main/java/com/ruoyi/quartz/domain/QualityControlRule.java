package com.ruoyi.quartz.domain;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 质控规则对象 quality_control_rule
 * 
 * @author ruoyi
 */
@ApiModel("质控规则")
public class QualityControlRule
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Long id;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty("指标编码")
    @NotNull(message = "指标编码不能为空")
    private String metricCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty("指标名称")
    @NotNull(message = "指标名称不能为空")
    private String metricName;

    /** 上界值 */
    @Excel(name = "上界值")
    @ApiModelProperty("上界值")
    @NotNull(message = "上界值不能为空")
    private BigDecimal upperBound;

    /** 下界值 */
    @Excel(name = "下界值")
    @ApiModelProperty("下界值")
    @NotNull(message = "下界值不能为空")
    private BigDecimal lowerBound;

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

    public BigDecimal getUpperBound()
    {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound)
    {
        this.upperBound = upperBound;
    }

    public BigDecimal getLowerBound()
    {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound)
    {
        this.lowerBound = lowerBound;
    }
    
    /**
     * 验证值是否符合质控规则
     * 
     * @param value 待验证的值
     * @throws IllegalArgumentException 当值不符合质控规则时抛出异常
     */
    public void validate(BigDecimal value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("验证值不能为空");
        }
        
        if (lowerBound != null && value.compareTo(lowerBound) < 0) {
            throw new IllegalArgumentException(
                String.format("指标 %s 的值 %s 小于下限值 %s", 
                    metricCode != null ? metricCode : "未知指标", 
                    value, 
                    lowerBound));
        }
        
        if (upperBound != null && value.compareTo(upperBound) > 0) {
            throw new IllegalArgumentException(
                String.format("指标 %s 的值 %s 大于上限值 %s", 
                    metricCode != null ? metricCode : "未知指标", 
                    value, 
                    upperBound));
        }
    }
}