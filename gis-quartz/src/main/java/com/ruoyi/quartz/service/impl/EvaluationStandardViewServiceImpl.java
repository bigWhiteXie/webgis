package com.ruoyi.quartz.service.impl;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.EvaluationStandardView;
import com.ruoyi.quartz.mapper.EvaluationStandardViewMapper;
import com.ruoyi.quartz.service.IEvaluationStandardViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价标准查看Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class EvaluationStandardViewServiceImpl implements IEvaluationStandardViewService
{
    @Autowired
    private EvaluationStandardViewMapper evaluationStandardViewMapper;

    /**
     * 插入评价标准查看记录
     *
     * @param evaluationStandardView 评价标准查看对象
     * @return 结果
     */
    @Override
    public int insertEvaluationStandardView(EvaluationStandardView evaluationStandardView)
    {
        return evaluationStandardViewMapper.insertEvaluationStandardView(evaluationStandardView);
    }
    
    /**
     * 分页查询评价标准查看记录
     *
     * @param evaluationStandardView 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @Override
    public TableDataInfo selectEvaluationStandardViewList(EvaluationStandardView evaluationStandardView, int pageNum, int pageSize)
    {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = evaluationStandardViewMapper.selectEvaluationStandardViewCount(evaluationStandardView);
        
        // 查询分页数据
        List<EvaluationStandardView> list = evaluationStandardViewMapper.selectEvaluationStandardViewList(evaluationStandardView, offset, pageSize);
        
        // 返回分页结果
        return new TableDataInfo(list, total);
    }
}