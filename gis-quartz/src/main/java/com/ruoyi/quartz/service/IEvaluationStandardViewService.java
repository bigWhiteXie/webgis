package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.EvaluationStandardView;

/**
 * 评价标准查看Service接口
 * 
 * @author ruoyi
 */
public interface IEvaluationStandardViewService 
{
    /**
     * 插入评价标准查看记录
     *
     * @param evaluationStandardView 评价标准查看对象
     * @return 结果
     */
    public int insertEvaluationStandardView(EvaluationStandardView evaluationStandardView);
    
    /**
     * 分页查询评价标准查看记录
     *
     * @param evaluationStandardView 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    public TableDataInfo selectEvaluationStandardViewList(EvaluationStandardView evaluationStandardView, int pageNum, int pageSize);
}