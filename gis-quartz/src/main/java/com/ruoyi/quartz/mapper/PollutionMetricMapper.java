package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.PollutionMetric;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 污染物指标Mapper接口
 * 
 * @author ruoyi
 */
public interface PollutionMetricMapper 
{
    /**
     * 查询污染物指标
     * 
     * @param id 污染物指标主键
     * @return 污染物指标
     */
    public PollutionMetric selectPollutionMetricById(Long id);
    
    /**
     * 查询污染物指标列表（无分页）
     * 
     * @param pollutionMetric 污染物指标
     * @return 污染物指标集合
     */
    public List<PollutionMetric> selectPollutionMetricList(PollutionMetric pollutionMetric);
    
    /**
     * 查询污染物指标列表（分页）
     *
     * @param pollutionMetric 污染物指标
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 污染物指标集合
     */
    public List<PollutionMetric> selectPollutionMetricListByPage(@Param("pollutionMetric") PollutionMetric pollutionMetric, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 查询污染物指标总数
     *
     * @param pollutionMetric 污染物指标
     * @return 污染物指标总数
     */
    public int selectPollutionMetricCount(@Param("pollutionMetric") PollutionMetric pollutionMetric);

    /**
     * 新增污染物指标
     * 
     * @param pollutionMetric 污染物指标
     * @return 结果
     */
    public int insertPollutionMetric(PollutionMetric pollutionMetric);

    /**
     * 修改污染物指标
     * 
     * @param pollutionMetric 污染物指标
     * @return 结果
     */
    public int updatePollutionMetric(PollutionMetric pollutionMetric);

    /**
     * 删除污染物指标
     * 
     * @param id 污染物指标主键
     * @return 结果
     */
    public int deletePollutionMetricById(Long id);

    /**
     * 批量删除污染物指标
     * 
     * @param ids 需要删除的数据主键数组
     * @return 结果
     */
    public int deletePollutionMetricByIds(Long[] ids);

    /**
     * 根据指标名称查询指标编码
     *
     * @param metricName 指标名称
     * @return 污染物指标对象
     */
    public PollutionMetric selectByMetricName(String metricName);
    
    /**
     * 根据指标编码查询指标信息
     *
     * @param metricCode 指标编码
     * @return 污染物指标对象
     */
    public PollutionMetric selectByMetricCode(String metricCode);
}