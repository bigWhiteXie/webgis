package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.EvaluationStandardConfig;
import com.ruoyi.quartz.domain.api.EvaluationStandardConfDTO;
import java.util.List;
import java.util.Map;

/**
 * 评价标准配置Mapper接口
 * 
 * @author ruoyi
 */
public interface EvaluationStandardConfigMapper 
{
    /**
     * 插入评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public int insertEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 批量插入评价标准配置记录
     *
     * @param evaluationStandardConfigs 评价标准配置对象列表
     * @return 结果
     */
    public int insertEvaluationStandardConfigBatch(List<EvaluationStandardConfig> evaluationStandardConfigs);
    
    /**
     * 根据ID查询评价标准配置记录
     *
     * @param id 评价标准配置ID
     * @return 评价标准配置对象
     */
    public EvaluationStandardConfig selectById(Long id);
    
    /**
     * 查询评价标准配置记录列表
     *
     * @param evaluationStandardConfig 查询条件
     * @return 评价标准配置记录列表
     */
    public List<EvaluationStandardConfig> selectEvaluationStandardConfigList(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 分页查询评价标准配置记录列表
     *
     * @param params 查询参数Map，包含evaluationStandardConfig(查询条件), offset(偏移量), limit(限制条数)
     * @return 评价标准配置记录列表
     */
    public List<EvaluationStandardConfig> selectEvaluationStandardConfigListByPage(Map<String, Object> params);
    
    /**
     * 查询评价标准配置记录数量
     *
     * @param evaluationStandardConfig 查询条件
     * @return 记录数量
     */
    public int selectEvaluationStandardConfigCount(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 根据ID列表批量查询评价标准配置记录
     *
     * @param ids 评价标准配置ID列表
     * @return 评价标准配置记录列表
     */
    public List<EvaluationStandardConfig> selectEvaluationStandardConfigByIds(List<Long> ids);
    
    /**
     * 更新评价标准配置记录状态
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public int updateEvaluationStandardConfigStatus(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 更新评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public int updateEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 根据ID删除评价标准配置记录
     *
     * @param id 评价标准配置ID
     * @return 结果
     */
    public int deleteEvaluationStandardConfigById(Long id);
    
    /**
     * 根据ID列表批量删除评价标准配置记录
     *
     * @param ids 评价标准配置ID列表
     * @return 结果
     */
    public int deleteEvaluationStandardConfigByIds(List<Long> ids);
    
    /**
     * 根据指标编码列表批量删除评价标准配置记录
     *
     * @param metricCodes 指标编码列表
     * @return 结果
     */
    public int deleteEvaluationStandardConfigByMetricCodes(List<String> metricCodes);
    
    /**
     * 分页查询评价标准配置DTO记录列表
     *
     * @param params 查询参数Map，包含evaluationStandardConfig(查询条件), offset(偏移量), limit(限制条数)
     * @return 评价标准配置DTO记录列表
     */
    public List<EvaluationStandardConfDTO> selectEvaluationStandardConfDTOListByPage(Map<String, Object> params);
    
    /**
     * 查询评价标准配置DTO记录数量
     *
     * @param evaluationStandardConfig 查询条件
     * @return 记录数量
     */
    public int selectEvaluationStandardConfDTOCount(EvaluationStandardConfig evaluationStandardConfig);
}