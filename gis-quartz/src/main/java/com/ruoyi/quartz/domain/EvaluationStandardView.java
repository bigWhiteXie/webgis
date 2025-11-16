package com.ruoyi.quartz.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 评价标准查看对象 evaluation_standard_view
 * 
 * @author ruoyi
 */
@ApiModel("评价标准查看")
public class EvaluationStandardView
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @ApiModelProperty("主键ID")
    private Long id;

    /** 标准名称 */
    @Excel(name = "标准名称")
    @ApiModelProperty("标准名称")
    private String standardName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    @ApiModelProperty("文件路径")
    private String filePath;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @ApiModelProperty("创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}