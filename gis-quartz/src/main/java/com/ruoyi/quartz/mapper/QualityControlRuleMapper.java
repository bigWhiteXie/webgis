package com.ruoyi.quartz.mapper;

import java.util.List;
import com.ruoyi.quartz.domain.QualityControlRule;
import org.apache.ibatis.annotations.Param;

/**
 * 质控规则Mapper接口
 * 
 * @author ruoyi
 */
public interface QualityControlRuleMapper 
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
     * 查询质控规则列表（分页）
     *
     * @param qualityControlRule 质控规则
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 质控规则集合
     */
    public List<QualityControlRule> selectQualityControlRuleListPaged(@Param("qualityControlRule") QualityControlRule qualityControlRule, @Param("offset") int offset, @Param("limit") int limit);

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
     * 删除质控规则
     * 
     * @param id 质控规则主键
     * @return 结果
     */
    public int deleteQualityControlRuleById(Long id);

    /**
     * 批量删除质控规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQualityControlRuleByIds(Long[] ids);
}