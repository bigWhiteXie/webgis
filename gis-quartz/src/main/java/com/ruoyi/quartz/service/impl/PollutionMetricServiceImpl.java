package com.ruoyi.quartz.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.quartz.mapper.PollutionMetricMapper;
import com.ruoyi.quartz.domain.PollutionMetric;
import com.ruoyi.quartz.service.IPollutionMetricService;
import com.ruoyi.quartz.service.ISampleDataService;
import com.ruoyi.quartz.service.IEvaluationStandardConfigService;
import com.ruoyi.quartz.service.IMetricStandardService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.constant.HttpStatus;

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
    
    @Autowired
    private ISampleDataService sampleDataService;
    
    @Autowired
    private IEvaluationStandardConfigService evaluationStandardConfigService;
    
    @Autowired
    private IMetricStandardService metricStandardService;

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
     * @return 污染物指标分页数据
     */
    @Override
    public TableDataInfo selectPollutionMetricList(PollutionMetric pollutionMetric, Integer pageNum, Integer pageSize) 
    {
        int offset = (pageNum - 1) * pageSize;
        List<PollutionMetric> list = pollutionMetricMapper.selectPollutionMetricListByPage(pollutionMetric, offset, pageSize);
        int total = pollutionMetricMapper.selectPollutionMetricCount(pollutionMetric);
        TableDataInfo tableDataInfo = new TableDataInfo(list, total);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        return tableDataInfo;
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
        // First get the metric codes for the given IDs
        List<String> metricCodes = new ArrayList<>();
        for (Long id : ids) {
            PollutionMetric metric = pollutionMetricMapper.selectPollutionMetricById(id);
            if (metric != null) {
                metricCodes.add(metric.getMetricCode());
            }
        }
        
        // Delete related data in sample_data table
        if (!metricCodes.isEmpty()) {
            sampleDataService.deleteSampleDataByMetricCodes(metricCodes);
        }
        
        // Delete related data in evaluation_standard_config table
        if (!metricCodes.isEmpty()) {
            evaluationStandardConfigService.deleteEvaluationStandardConfigByMetricCodes(metricCodes);
        }
        
        // Delete related data in metric_standard table
        if (!metricCodes.isEmpty()) {
            metricStandardService.deleteMetricStandardByMetricCodes(metricCodes);
        }
        
        // Finally delete the pollution metrics themselves
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
        return deletePollutionMetricByIds(new Long[]{id});
    }
}