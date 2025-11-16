package com.ruoyi.quartz.domain;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.enums.WaterQualityLevel;

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

    /** 评价标准配置ID */
    private Long evaluationId;

    /** 指标编码 */
    private String metricCode;

    /** 指标名称 */
    private String metricName;

    /** 质量等级 */
    private String qualityLevel;

    /** 阈值关系类型 */
    private String relation;

    /** 上界值 */
    private BigDecimal upperBound;

    /** 下界值 */
    private BigDecimal lowerBound;

    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getEvaluationId()
    {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId)
    {
        this.evaluationId = evaluationId;
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

    public String getQualityLevel()
    {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public BigDecimal getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * 计算值是否符合区间范围
     * @param value 待检测的值
     * @return 是否符合区间范围
     */
    public boolean compute(BigDecimal value) {
        if (value == null) {
            return false;
        }

        if (upperBound == null || lowerBound == null) {
            return false;
        }

        switch (relation) {
            case "between":
                // 开区间：lowerBound < value < upperBound
                return value.compareTo(lowerBound) > 0 && value.compareTo(upperBound) < 0;
            case "between_equal":
                // 闭区间：lowerBound <= value <= upperBound
                return value.compareTo(lowerBound) >= 0 && value.compareTo(upperBound) <= 0;
            case "between_up_equal":
                // 左开右闭：lowerBound < value <= upperBound
                return value.compareTo(lowerBound) > 0 && value.compareTo(upperBound) <= 0;
            case "between_low_equal":
                // 左闭右开：lowerBound <= value < upperBound
                return value.compareTo(lowerBound) >= 0 && value.compareTo(upperBound) < 0;
            default:
                return false;
        }
    }
    
    /**
     * 比较水质等级的优劣
     * 
     * @param other 另一个水质等级
     * @return 如果当前等级优于other返回负数，相等返回0，劣于返回正数
     */
    public int compareLevel(String other) {
        return WaterQualityLevel.compareLevel(this.qualityLevel, other);
    }
}