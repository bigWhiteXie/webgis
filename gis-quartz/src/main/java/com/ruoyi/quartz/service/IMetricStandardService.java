package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MetricStandard;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IMetricStandardService {

    /**
     * 插入指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    public int insertMetricStandard(MetricStandard metricStandard);

    /**
     * 批量插入指标标准记录
     *
     * @param metricStandards 指标标准对象列表
     * @return 结果
     */
    public int insertMetricStandardBatch(List<MetricStandard> metricStandards);

    /**
     * 修改指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    public int updateMetricStandard(MetricStandard metricStandard);

    /**
     * 根据ID删除指标标准记录
     *
     * @param id 指标标准ID
     * @return 结果
     */
    public int deleteMetricStandardById(Long id);
    
    /**
     * 批量删除指标标准记录
     *
     * @param ids 需要删除的指标标准ID数组
     * @return 结果
     */
    public int deleteMetricStandardByIds(Long[] ids);

    /**
     * 根据指标编码列表批量删除指标标准记录
     *
     * @param metricCodes 指标编码列表
     * @return 结果
     */
    public int deleteMetricStandardByMetricCodes(List<String> metricCodes);
    
    /**
     * 查询所有指标标准记录
     *
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardList();

    /**
     * 根据指标编码查询指标标准记录
     *
     * @param metricCode 指标编码
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardListByMetricCode(String metricCode);
    
    /**
     * 根据ID查询指标标准记录
     *
     * @param id 指标标准ID
     * @return 指标标准对象
     */
    public MetricStandard selectMetricStandardById(Long id);


    /**
     * 分页查询指标标准
     * 
     * @param metricCode 指标编码
     * @param qualityLevel 质量等级
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    public TableDataInfo selectMetricStandardList(String metricCode, String qualityLevel, int pageNum, int pageSize);

    /**
     * 等级计算方法
     *
     * @param metricCodes 指标编码列表
     * @param values      检测值列表
     * @return 最差的质量等级
     */
    public String calculateQualityLevel(List<String> metricCodes, List<String> values, Map<String, List<MetricStandard>> metricStandardMap);

    public Map<String, List<MetricStandard>> getMetricStandardMap(List<String> metricCodes);
    
    /**
     * 计算单个指标的质量等级
     *
     * @param metricCode 指标编码
     * @param value      检测值
     * @return 质量等级
     */
    public String calculateSingleQualityLevel(String metricCode, String value);
}