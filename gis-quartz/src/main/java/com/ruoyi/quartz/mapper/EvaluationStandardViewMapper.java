package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.EvaluationStandardView;
import java.util.List;

/**
 * 评价标准查看Mapper接口
 * 
 * @author ruoyi
 */
public interface EvaluationStandardViewMapper 
{
    /**
     * 插入评价标准查看记录
     *
     * @param evaluationStandardView 评价标准查看对象
     * @return 结果
     */
    public int insertEvaluationStandardView(EvaluationStandardView evaluationStandardView);
    
    /**
     * 根据ID查询评价标准查看记录
     *
     * @param id 评价标准查看ID
     * @return 评价标准查看对象
     */
    public EvaluationStandardView selectById(Long id);
    
    /**
     * 根据ID列表查询评价标准查看记录
     *
     * @param ids 评价标准查看ID列表
     * @return 评价标准查看记录列表
     */
    public List<EvaluationStandardView> selectEvaluationStandardViewByIds(List<Long> ids);
    
    /**
     * 查询评价标准查看记录列表
     *
     * @param evaluationStandardView 查询条件
     * @param offset 偏移量
     * @param pageSize 每页记录数
     * @return 评价标准查看记录列表
     */
    public List<EvaluationStandardView> selectEvaluationStandardViewList(EvaluationStandardView evaluationStandardView, int offset, int pageSize);
    
    /**
     * 查询评价标准查看记录数量
     *
     * @param evaluationStandardView 查询条件
     * @return 记录数量
     */
    public int selectEvaluationStandardViewCount(EvaluationStandardView evaluationStandardView);
}