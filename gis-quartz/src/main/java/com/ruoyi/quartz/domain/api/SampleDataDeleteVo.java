package com.ruoyi.quartz.domain.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel("监测数据删除对象")
public class SampleDataDeleteVo {
    // 监测井编码
    @ApiModelProperty(value = "监测井编码", required = true)
    @NotBlank(message = "监测井编码不能为空")
    private String monitoringWellCode;

    // 样品编码
    @ApiModelProperty(value = "样品编码", required = true)
    @NotBlank(message = "样品编码不能为空")
    private String sampleCode;

    // 默认构造函数
    public SampleDataDeleteVo() {
    }

    // 带参数的构造函数
    public SampleDataDeleteVo(String monitoringWellCode, String sampleCode) {
        this.monitoringWellCode = monitoringWellCode;
        this.sampleCode = sampleCode;
    }

    // Getter和Setter方法
    public String getMonitoringWellCode() {
        return monitoringWellCode;
    }

    public void setMonitoringWellCode(String monitoringWellCode) {
        this.monitoringWellCode = monitoringWellCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Override
    public String toString() {
        return "SampleDataDeleteVo{" +
                "monitoringWellCode='" + monitoringWellCode + '\'' +
                ", sampleCode='" + sampleCode + '\'' +
                '}';
    }
}