package com.ruoyi.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.quartz.mapper.PollutionMetricMapper;
import com.ruoyi.quartz.domain.PollutionMetric;
import com.ruoyi.quartz.service.IPollutionMetricService;

/**
 * 污染物指标Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class PollutionMetricServiceImpl implements IPollutionMetricService 
{
    @Autowired
    private PollutionMetricMapper pollutionMetricMapper;

    /**
     * 查询污染物指标
     * 
     * @param id 污染物指标主键
     * @return 污染物指标
     */
    @Override
    public PollutionMetric selectPollutionMetricById(Long id) 
    {
        return pollutionMetricMapper.selectPollutionMetricById(id);
    }

    /**
     * 查询污染物指标列表
     * 
     * @param pollutionMetric 污染物指标
     * @return 污染物指标
     */
    @Override
    public List<PollutionMetric> selectPollutionMetricList(PollutionMetric pollutionMetric) 
    {
        return pollutionMetricMapper.selectPollutionMetricList(pollutionMetric);
    }
    
    /**
     * 查询污染物指标列表（分页）
     *
     * @param pollutionMetric 污染物指标
     * @param pageNum 当前页码
     * @param pageSize 每页数量
     * @return 污染物指标集合
     */
    @Override
    public List<PollutionMetric> selectPollutionMetricList(PollutionMetric pollutionMetric, Integer pageNum, Integer pageSize) 
    {
        int offset = (pageNum - 1) * pageSize;
        return pollutionMetricMapper.selectPollutionMetricList(pollutionMetric, offset, pageSize);
    }

    /**
     * 新增污染物指标
     * 
     * @param pollutionMetric 污染物指标
     * @return 结果
     */
    @Override
    public int insertPollutionMetric(PollutionMetric pollutionMetric) 
    {
        return pollutionMetricMapper.insertPollutionMetric(pollutionMetric);
    }

    /**
     * 修改污染物指标
     * 
     * @param pollutionMetric 污染物指标
     * @return 结果
     */
    @Override
    public int updatePollutionMetric(PollutionMetric pollutionMetric) 
    {
        return pollutionMetricMapper.updatePollutionMetric(pollutionMetric);
    }

    /**
     * 批量删除污染物指标
     * 
     * @param ids 需要删除的污染物指标主键数组
     * @return 结果
     */
    @Override
    public int deletePollutionMetricByIds(Long[] ids) 
    {
        return pollutionMetricMapper.deletePollutionMetricByIds(ids);
    }
    
    /**
     * 删除污染物指标信息
     * 
     * @param id 污染物指标主键
     * @return 结果
     */
    @Override
    public int deletePollutionMetricById(Long id)
    {
        return pollutionMetricMapper.deletePollutionMetricById(id);
    }
}