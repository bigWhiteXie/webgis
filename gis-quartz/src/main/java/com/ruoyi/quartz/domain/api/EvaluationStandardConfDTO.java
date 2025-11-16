package com.ruoyi.quartz.domain.api;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.quartz.domain.EvaluationStandardConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评价标准配置DTO对象 evaluation_standard_config
 *
 * @author ruoyi
 * @date 2025-11-16
 */
@ApiModel("评价标准配置DTO")
public class EvaluationStandardConfDTO extends EvaluationStandardConfig
{
    private static final long serialVersionUID = 1L;

    /** 参考标准 */
    @Excel(name = "参考标准")
    @ApiModelProperty("参考标准")
    private String referenceStandard;

    public String getReferenceStandard() {
        return referenceStandard;
    }

    public void setReferenceStandard(String referenceStandard) {
        this.referenceStandard = referenceStandard;
    }
}