package com.ruoyi.quartz.domain;

import com.alibaba.excel.util.ListUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@ApiModel("地下水样品检测信息")
public class SampleData {
    private static final Logger log = LoggerFactory.getLogger(SampleData.class);

    // 主键ID
    @ApiModelProperty("主键ID")
    @Excel(name = "序号")
    private Long id;

    // 监测井编码
    @ApiModelProperty("监测井编码")
    @Excel(name = "监测井编码")
    private String monitoringWellCode;

    // 样品编码
    @ApiModelProperty("样品编码")
    @Excel(name = "样品编码")
    private String sampleCode;

    // 采样时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("采样时间")
    @Excel(name = "采样时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date samplingTime;

    // 检测实验室
    @ApiModelProperty("检测实验室")
    @Excel(name = "检测实验室")
    private String testingLaboratory;

    // 检测指标代码
    @ApiModelProperty("检测指标代码")
    @Excel(name = "检测指标代码")
    private String metricCode;

    // 检出限
    @ApiModelProperty("检出限")
    @Excel(name = "检出限")
    private String detectionLimit;

    // 实际检测值
    @ApiModelProperty("实际检测值")
    @Excel(name = "实际检测值")
    private String actualTestValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Date samplingTime) {
        this.samplingTime = samplingTime;
    }

    public String getTestingLaboratory() {
        return testingLaboratory;
    }

    public void setTestingLaboratory(String testingLaboratory) {
        this.testingLaboratory = testingLaboratory;
    }

    public String getMetricCode() {
        return metricCode;
    }

    public void setMetricCode(String metricCode) {
        this.metricCode = metricCode;
    }

    public String getDetectionLimit() {
        return detectionLimit;
    }

    public void setDetectionLimit(String detectionLimit) {
        this.detectionLimit = detectionLimit;
    }

    public String getActualTestValue() {
        return actualTestValue;
    }

    public void setActualTestValue(String actualTestValue) {
        this.actualTestValue = actualTestValue;
    }

    /**
     * 解析Excel数据为SampleData对象列表
     *
     * @param data Excel数据列表
     * @return SampleData对象列表
     */
    public static List<SampleData> parseExcelData(List<Object> data) {
        List<SampleData> sampleDataList = ListUtils.newArrayList();

        // 遍历所有行数据
        for (Object rowData : data) {
            try {
                // 检查是否至少有8列
                if (rowData instanceof LinkedHashMap) {
                    LinkedHashMap<Integer, Object> rowMap = (LinkedHashMap<Integer, Object>) rowData;
                    if (rowMap.size() >= 8) {
                        SampleData sampleData = new SampleData();

                        // 根据列索引获取值（跳过第1列序号，由数据库自增）
                        // 第2列为监测井编码
                        sampleData.setMonitoringWellCode(getStringValue(rowMap.get(1)));
                        // 第3列为样品编码
                        sampleData.setSampleCode(getStringValue(rowMap.get(2)));
                        if (StringUtils.isEmpty(sampleData.getSampleCode()) ||
                                StringUtils.isEmpty(sampleData.getMonitoringWellCode())) {
                            continue;
                        }
                        // 第4列为采样时间
                        sampleData.setSamplingTime(DateUtils.parseDate(getStringValue(rowMap.get(3))));
                        // 第5列为检测实验室
                        sampleData.setTestingLaboratory(getStringValue(rowMap.get(4)));
                        // 第6列为检测指标代码
                        sampleData.setMetricCode(getStringValue(rowMap.get(5)));
                        // 第7列为检出限
                        sampleData.setDetectionLimit(getStringValue(rowMap.get(6)));
                        // 第8列为实际检测值
                        sampleData.setActualTestValue(getStringValue(rowMap.get(7)));

                        sampleDataList.add(sampleData);
                    }
                }
            } catch (Exception e) {
                log.error("解析Excel行数据失败: {}，数据：{}", e.getMessage(), rowData, e);
            }
        }

        return sampleDataList;
    }

    /**
     * 获取字符串值
     *
     * @param value 原始值
     * @return 字符串值
     */
    private static String getStringValue(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString().trim();
    }

    @Override
    public String toString() {
        return "SampleData{" +
                "id=" + id +
                ", monitoringWellCode='" + monitoringWellCode + '\'' +
                ", sampleCode='" + sampleCode + '\'' +
                ", samplingTime=" + samplingTime +
                ", testingLaboratory='" + testingLaboratory + '\'' +
                ", metricCode='" + metricCode + '\'' +
                ", detectionLimit='" + detectionLimit + '\'' +
                ", actualTestValue='" + actualTestValue + '\'' +
                '}';
    }


}