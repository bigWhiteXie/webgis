package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.SampleData;
import com.ruoyi.quartz.domain.api.MetricPair;
import com.ruoyi.quartz.domain.api.MetricValItem;
import com.ruoyi.quartz.domain.api.SampleDataResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 地下水样品检测信息Mapper接口
 * 
 * @author ruoyi
 */
@Mapper
public interface SampleDataMapper {
    /**
     * 批量插入地下水样品检测信息
     * 
     * @param sampleDataList 地下水样品检测信息列表
     * @return 结果
     */
    int batchInsertSampleData(List<SampleData> sampleDataList);
    
    /**
     * 查找指定监测井的小于等于指定时间的最近采样时间
     * 
     * @param monitoringWellCode 监测井编号
     * @param specifiedTime 指定时间
     * @return 最近采样时间(最大的那个)
     */
    Date findLatestSamplingTimeByMonitoringWellCodeAndTime(
        @Param("monitoringWellCode") String monitoringWellCode, 
        @Param("specifiedTime") Date specifiedTime);
    
    /**
     * 查询指定监测井关于部分监测指标的监测结果
     * 
     * @param monitoringWellCode 监测井编码
     * @param metricNames 指标名称列表
     * @param samplingTime 采样时间
     * @return SampleDataResp对象
     */
    SampleDataResp selectSampleDataWithMetrics(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("metricNames") List<String> metricNames,
        @Param("samplingTime") Date samplingTime);
    
    /**
     * 查询指定时间范围内监测井的单项指标数据
     * 
     * @param monitoringWellCode 监测井编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param metricName 指标编号
     * @return 指定时间范围内监测井的单项指标数据列表
     */
    List<MetricValItem> selectSampleDataByTimeRange(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("startTime") Date startTime,
        @Param("endTime") Date endTime,
        @Param("metricName") String metricName);
    
    /**
     * 查询所有水质指标
     * 
     * @return 水质指标列表
     */
    List<MetricPair> selectAllWaterQualityMetrics();
    
    /**
     * 分页查询SampleDataResp列表
     * 
     * @param monitoringWellCode 监测井编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param projectId 项目编号（可选）
     * @return SampleDataResp列表
     */
    List<SampleDataResp> selectSampleDataRespList(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("startTime") Date startTime,
        @Param("endTime") Date endTime,
        @Param("projectId") String projectId);
        
    /**
     * 查询SampleDataResp列表总数
     * 
     * @param monitoringWellCode 监测井编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param projectId 项目编号（可选）
     * @return 总数
     */
    int selectSampleDataRespCount(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("startTime") Date startTime,
        @Param("endTime") Date endTime,
        @Param("projectId") String projectId);
        
    /**
     * 按分组查询SampleDataResp基本信息（用于分页）
     * 
     * @param monitoringWellCode 监测井编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param projectId 项目编号（可选）
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @return SampleDataResp基本列表
     */
    List<SampleDataResp> selectSampleDataRespByGroup(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("startTime") Date startTime,
        @Param("endTime") Date endTime,
        @Param("projectId") String projectId,
        @Param("offset") int offset,
        @Param("pageSize") int pageSize);
        
    /**
     * 根据监测井编码和采样时间查询带指标的SampleDataResp
     * 
     * @param monitoringWellCode 监测井编码
     * @param samplingTime 采样时间
     * @return SampleDataResp对象
     */
    SampleDataResp selectSampleDataRespByGroupWithMetrics(
        @Param("monitoringWellCode") String monitoringWellCode,
        @Param("samplingTime") Date samplingTime);
}