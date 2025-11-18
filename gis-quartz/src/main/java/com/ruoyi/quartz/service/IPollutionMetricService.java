package com.ruoyi.quartz.service;

import com.ruoyi.quartz.domain.PollutionMetric;
import java.util.List;

/**
 * 污染物指标Service接口
 * 
 * @author ruoyi
 */
public interface IPollutionMetricService 
{
    /**
     * 查询污染物指标
     * 
     * @param id 污染物指标主键
     * @return 污染物指标
     */
    public PollutionMetric selectPollutionMetricById(Long id);

    /**
     * 查询污染物指标列表
     * 
     * @param pollutionMetric 污染物指标
     * @return 污染物指标集合
     */
    public List<PollutionMetric> selectPollutionMetricList(PollutionMetric pollutionMetric);
    
    /**
     * 查询污染物指标列表（分页）
     *
     * @param pollutionMetric 污染物指标
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return 污染物指标集合
     */
    public List<PollutionMetric> selectPollutionMetricList(PollutionMetric pollutionMetric, Integer pageNum, Integer pageSize);

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
     * 批量删除污染物指标
     * 
     * @param ids 需要删除的污染物指标主键集合
     * @return 结果
     */
    public int deletePollutionMetricByIds(Long[] ids);
    
    /**
     * 删除污染物指标信息
     * 
     * @param id 污染物指标主键
     * @return 结果
     */
    public int deletePollutionMetricById(Long id);
}