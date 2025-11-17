package com.ruoyi.quartz.service;


import com.ruoyi.quartz.domain.QualityControlRule;

import java.util.List;

public interface IQualityControlRuleService
{
    /**
     * 查询质控规则
     * 
     * @param id 质控规则主键
     * @return 质控规则
     */
    public QualityControlRule selectQualityControlRuleById(Long id);

    /**
     * 查询质控规则列表
     * 
     * @param qualityControlRule 质控规则
     * @return 质控规则集合
     */
    public List<QualityControlRule> selectQualityControlRuleList(QualityControlRule qualityControlRule);

    /**
     * 新增质控规则
     * 
     * @param qualityControlRule 质控规则
     * @return 结果
     */
    public int insertQualityControlRule(QualityControlRule qualityControlRule);

    /**
     * 修改质控规则
     * 
     * @param qualityControlRule 质控规则
     * @return 结果
     */
    public int updateQualityControlRule(QualityControlRule qualityControlRule);

    /**
     * 批量删除质控规则
     * 
     * @param ids 需要删除的质控规则主键集合
     * @return 结果
     */
    public int deleteQualityControlRuleByIds(Long[] ids);

    /**
     * 删除质控规则信息
     * 
     * @param id 质控规则主键
     * @return 结果
     */
    public int deleteQualityControlRuleById(Long id);
    
    /**
     * 根据code查询质控规则
     * 
     * @param code 质控规则编码
     * @return 质控规则
     */
    public QualityControlRule getRuleByCode(String code);
}