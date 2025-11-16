package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.PollutionMetric;
import java.util.List;

/**
 * 污染物指标Mapper接口
 * 
 * @author ruoyi
 */
public interface PollutionMetricMapper 
{
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