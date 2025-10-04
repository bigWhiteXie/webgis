package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.api.MetricPair;
import com.ruoyi.quartz.domain.api.MetricValItem;
import com.ruoyi.quartz.domain.api.SampleDataResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 地下水样品检测信息Service接口
 * 
 * @author ruoyi
 */
public interface ISampleDataService {
    
    /**
     * 解析并导入Excel文件
     *
     * @param file Excel文件
     * @return 导入的记录数
     * @throws Exception 导入异常
     */
    int parseAndImportExcelFile(MultipartFile file) throws Exception;
    
    /**
     * 查询指定监测井相关的检测值
     * 
     * @param monitoringWellCode 监测井编号
     * @param date 指定日期
     * @param metricNames 相关指标名称列表
     * @return SampleDataResp对象
     */
    SampleDataResp getSampleDataByMonitoringWellAndMetrics(
        String monitoringWellCode, 
        Date date, 
        List<String> metricNames);
        
    /**
     * 查询指定时间范围内监测井的单项指标数据
     * 
     * @param monitoringWellCode 监测井编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param metricName 指标名称
     * @return 指定时间范围内监测井的单项指标数据列表
     */
    List<MetricValItem> getSampleDataByTimeRange(String monitoringWellCode, Date startTime, Date endTime, String metricName);
        
    /**
     * 获取所有水质指标
     * 
     * @return 水质指标列表
     */
    List<MetricPair> getAllWaterQualityMetrics();
    
    /**
     * 分页查询SampleDataResp列表
     * 
     * @param monitoringWellCode 监测井编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param projectId 项目编号（可选）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    TableDataInfo getSampleDataRespList(
        String monitoringWellCode, 
        Date startTime, 
        Date endTime, 
        String projectId,
        Integer pageNum,
        Integer pageSize);
}