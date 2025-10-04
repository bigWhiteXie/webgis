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
     * 根据条件查询指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardListByCondition(MetricStandard metricStandard);
    
    /**
     * 根据条件分页查询指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 指标标准列表
     */
    public List<MetricStandard> selectMetricStandardListByConditionPager(@Param("metricStandard") MetricStandard metricStandard, @Param("offset") int offset, @Param("limit") int limit);
	
	/**
     * 查询指标标准数据总记录数
     *
     * @param metricStandard 查询条件
     * @return 总记录数
     */
    public int selectMetricStandardCount(@Param("metricStandard") MetricStandard metricStandard);
}