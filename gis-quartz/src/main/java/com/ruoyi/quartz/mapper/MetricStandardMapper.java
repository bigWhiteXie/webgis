package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.MetricStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 指标标准Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-02
 */
public interface MetricStandardMapper 
{
    /**
     * 插入指标标准记录，如果唯一索引冲突则更新阈值
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    public int insertMetricStandard(MetricStandard metricStandard);

    /**
     * 批量插入指标标准记录，如果唯一索引冲突则更新阈值
     *
     * @param metricStandards 指标标准对象列表
     * @return 结果
     */
    public int insertMetricStandardBatch(List<MetricStandard> metricStandards);

    /**
     * 修改指标标准记录，只能修改阈值和质量等级，如果唯一索引冲突则报错
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    public int updateMetricStandard(MetricStandard metricStandard);

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
     * 根据指标编码列表查询指标标准记录
     *
     * @param metricCodes 指标编码列表
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardListByMetricCodes(List<String> metricCodes);

    /**
     * 根据ID删除指标标准记录
     *
     * @param id 指标标准ID
     * @return 结果
     */
    public int deleteMetricStandardById(Long id);
    
    /**
     * 根据ID查询指标标准记录
     *
     * @param id 指标标准ID
     * @return 指标标准对象
     */
    public MetricStandard selectMetricStandardById(Long id);
    
    /**
     * 根据条件查询指标标准列表
     *
     * @param metricStandard 查询条件
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardListByCondition(MetricStandard metricStandard);

    /**
     * 根据ID列表删除指标标准
     *
     * @param ids ID列表
     * @return 删除结果
     */
    public int deleteMetricStandardByEvaluationIds(List<Long> ids);
    
    /**
     * 根据指标编码列表删除指标标准
     *
     * @param metricCodes 指标编码列表
     * @return 删除结果
     */
    public int deleteMetricStandardByMetricCodes(List<String> metricCodes);

    /**
     * 分页查询指标标准列表
     *
     * @param metricStandard 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 指标标准列表和总数
     */
    public List<MetricStandard> selectMetricStandardListByConditionPager(MetricStandard metricStandard, int offset, int limit);

    /**
     * 查询指标标准总数
     *
     * @param metricStandard 查询条件
     * @return 总数
     */
    public int selectMetricStandardCount(MetricStandard metricStandard);
}