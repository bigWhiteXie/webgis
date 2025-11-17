package com.ruoyi.quartz.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.enums.WaterQualityLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 评价标准配置对象 evaluation_standard_config
 *
 * @author ruoyi
 * @date 2025-11-16
 */
@ApiModel("评价标准配置")
public class EvaluationStandardConfig
{
    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LoggerFactory.getLogger(EvaluationStandardConfig.class);

    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Long id;

    /** 参考标准ID */
    @ApiModelProperty("参考标准ID")
    private Long referenceStandardId;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty("指标编码")
    private String metricCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty("指标名称")
    private String metricName;

    /** I类范围 */
    @Excel(name = "I类范围")
    @ApiModelProperty("I类范围")
    private String classIRange;

    /** II类范围 */
    @Excel(name = "II类范围")
    @ApiModelProperty("II类范围")
    private String classIiRange;

    /** III类范围 */
    @Excel(name = "III类范围")
    @ApiModelProperty("III类范围")
    private String classIiiRange;

    /** IV类范围 */
    @Excel(name = "IV类范围")
    @ApiModelProperty("IV类范围")
    private String classIvRange;

    /** V类范围 */
    @Excel(name = "V类范围")
    @ApiModelProperty("V类范围")
    private String classVRange;

    /** 劣V类范围 */
    @Excel(name = "劣V类范围")
    @ApiModelProperty("劣V类范围")
    private String classInferiorVRange;

    /** 状态 */
    @ApiModelProperty("状态（0-停用，1-启用）")
    private Integer status;
    ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceStandardId() {
        return referenceStandardId;
    }

    public void setReferenceStandardId(Long referenceStandardId) {
        this.referenceStandardId = referenceStandardId;
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

    public String getClassIRange() {
        return classIRange;
    }

    public void setClassIRange(String classIRange) {
        this.classIRange = classIRange;
    }

    public String getClassIiRange() {
        return classIiRange;
    }

    public void setClassIiRange(String classIiRange) {
        this.classIiRange = classIiRange;
    }

    public String getClassIiiRange() {
        return classIiiRange;
    }

    public void setClassIiiRange(String classIiiRange) {
        this.classIiiRange = classIiiRange;
    }

    public String getClassIvRange() {
        return classIvRange;
    }

    public void setClassIvRange(String classIvRange) {
        this.classIvRange = classIvRange;
    }

    public String getClassVRange() {
        return classVRange;
    }

    public void setClassVRange(String classVRange) {
        this.classVRange = classVRange;
    }

    public String getClassInferiorVRange() {
        return classInferiorVRange;
    }

    public void setClassInferiorVRange(String classInferiorVRange) {
        this.classInferiorVRange = classInferiorVRange;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 将EvaluationStandardConfig转换为多个MetricStandard对象
     *
     * @return List<MetricStandard> 转换后的MetricStandard列表
     */
    public List<MetricStandard> toMetricStandards() {
        List<MetricStandard> metricStandards = new ArrayList<>();
        
        // 使用for循环遍历WaterQualityLevel枚举类型
        for (WaterQualityLevel level : WaterQualityLevel.values()) {
            String rangeValue = null;
            String qualityLevelLabel = level.getLabel();
            
            switch (level) {
                case CLASS_I:
                    rangeValue = classIRange;
                    break;
                case CLASS_II:
                    rangeValue = classIiRange;
                    break;
                case CLASS_III:
                    rangeValue = classIiiRange;
                    break;
                case CLASS_IV:
                    rangeValue = classIvRange;
                    break;
                case CLASS_V:
                    rangeValue = classVRange;
                    break;
                case CLASS_INFERIOR_V:
                    rangeValue = classInferiorVRange;
                    break;
                default:
                    break;
            }
            
            // 只处理有值的类别
            if (rangeValue != null && !rangeValue.isEmpty()) {
                log.info("解析范围值: qualityLevel={}, rangeValue={}", qualityLevelLabel, rangeValue);
                String[] values = rangeValue.split("or");
                log.info("分割后的值: qualityLevel={}, values={}", qualityLevelLabel, (Object[]) values);
                for (String value : values) {
                    MetricStandard ms = parseRange(value);
                    if (ms != null) {
                        log.info("成功解析范围值: qualityLevel={}, value={}, result={}", qualityLevelLabel, value, ms);
                        ms.setEvaluationId(id);
                        ms.setMetricCode(metricCode);
                        ms.setMetricName(metricName);
                        ms.setQualityLevel(qualityLevelLabel);
                        metricStandards.add(ms);
                    } else {
                        log.warn("未能解析范围值: qualityLevel={}, value={}", qualityLevelLabel, value);
                    }
                }

            }
        }
        
        return metricStandards;
    }

    /**
     * 解析范围字符串，转换为MetricStandard对象
     *
     * @param range 范围字符串，如 "[7.5,8.5]"、"(7.5,8.5]" 等
     * @return 解析后的MetricStandard对象
     */
    private MetricStandard parseRange(String range) {
        // 正则表达式匹配区间格式，包括处理无穷大的情况
        // 修改正则表达式以正确处理边界情况
        Pattern pattern = Pattern.compile("([\\[\\(])([^,]*),([^\\]\\)\\[]*)([\\]\\)])");
        Matcher matcher = pattern.matcher(range.trim());

        if (!matcher.matches()) {
            // 尝试匹配半开放区间，如 "[7.5, )" 或 "[, 8.5)"
            pattern = Pattern.compile("([\\[\\(])([^,]*),\\s*([\\]\\)])");
            matcher = pattern.matcher(range.trim());
            
            if (!matcher.matches()) {
                return null;
            }
        }

        String leftBracket = matcher.group(1);
        String lowerStr = matcher.group(2).trim();
        String upperStr = matcher.group(3).trim();
        String rightBracket = matcher.group(4);

        MetricStandard ms = new MetricStandard();

        // 判断区间类型
        if ("[".equals(leftBracket) && "]".equals(rightBracket)) {
            ms.setRelation("between_equal");
        } else if ("(".equals(leftBracket) && "]".equals(rightBracket)) {
            ms.setRelation("between_up_equal");
        } else if ("[".equals(leftBracket) && ")".equals(rightBracket)) {
            ms.setRelation("between_low_equal");
        } else if ("(".equals(leftBracket) && ")".equals(rightBracket)) {
            ms.setRelation("between");
        }

        // 处理下界
        if (!lowerStr.isEmpty()) {
            try {
                ms.setLowerBound(new BigDecimal(lowerStr));
            } catch (NumberFormatException e) {
                // 如果不是有效数字，则保持为null
                ms.setLowerBound(null);
            }
        } else {
            // 处理[,x)这种形式，表示从0开始
            ms.setLowerBound(BigDecimal.ZERO);
        }

        // 处理上界
        if (!upperStr.isEmpty() && !upperStr.equals(")") && !upperStr.equals("]")) {
            try {
                ms.setUpperBound(new BigDecimal(upperStr));
            } catch (NumberFormatException e) {
                // 如果不是有效数字，则保持为null
                ms.setUpperBound(null);
            }
        } else {
            // 处理[x,)这种形式，表示无上限
            ms.setUpperBound(new BigDecimal(Double.MAX_VALUE));
        }

        return ms;
    }
}