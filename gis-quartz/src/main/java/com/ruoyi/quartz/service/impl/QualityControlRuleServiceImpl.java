package com.ruoyi.quartz.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import com.ruoyi.quartz.mapper.QualityControlRuleMapper;
import com.ruoyi.quartz.domain.QualityControlRule;
import com.ruoyi.quartz.service.IQualityControlRuleService;

/**
 * 质控规则Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class QualityControlRuleServiceImpl implements IQualityControlRuleService 
{
    @Autowired
    private QualityControlRuleMapper qualityControlRuleMapper;

    // 本地缓存：以ID为键存储质控规则
    private final Map<Long, QualityControlRule> qualityControlRuleCache = new ConcurrentHashMap<>();
    
    // 本地缓存：以code为唯一键存储质控规则
    private final Map<String, QualityControlRule> codeCache = new ConcurrentHashMap<>();

    /**
     * 系统启动时加载所有质控规则到本地缓存
     */
    @PostConstruct
    public void initCache() {
        List<QualityControlRule> rules = qualityControlRuleMapper.selectQualityControlRuleList(new QualityControlRule());
        qualityControlRuleCache.clear();
        codeCache.clear();
        
        // 按ID存储
        for (QualityControlRule rule : rules) {
            qualityControlRuleCache.put(rule.getId(), rule);
        }
        
        // 按code存储（唯一索引）
        for (QualityControlRule rule : rules) {
            if (StringUtils.isNotEmpty(rule.getMetricCode())) {
                codeCache.put(rule.getMetricCode(), rule);
            }
        }
    }

    /**
     * 根据code查询质控规则
     * 
     * @param code 质控规则编码
     * @return 质控规则
     */
    public QualityControlRule getRuleByCode(String code) {
        return codeCache.get(code);
    }

    /**
     * 查询质控规则
     * 
     * @param id 质控规则主键
     * @return 质控规则
     */
    @Override
    public QualityControlRule selectQualityControlRuleById(Long id) 
    {
        return qualityControlRuleCache.get(id);
    }

    /**
     * 查询质控规则列表
     * 
     * @param qualityControlRule 质控规则
     * @return 质控规则
     */
    @Override
    public List<QualityControlRule> selectQualityControlRuleList(QualityControlRule qualityControlRule) 
    {
        if (qualityControlRule != null && StringUtils.isNotEmpty(qualityControlRule.getMetricCode())) {
            QualityControlRule rule = codeCache.get(qualityControlRule.getMetricCode());
            return rule != null ? List.of(rule) : List.of();
        }
        
        return qualityControlRuleMapper.selectQualityControlRuleList(qualityControlRule);
    }

    /**
     * 新增质控规则
     * 
     * @param qualityControlRule 质控规则
     * @return 结果
     */
    @Override
    public int insertQualityControlRule(QualityControlRule qualityControlRule) 
    {
        int result = qualityControlRuleMapper.insertQualityControlRule(qualityControlRule);
        if (result > 0) {
            // 更新缓存
            qualityControlRuleCache.put(qualityControlRule.getId(), qualityControlRule);
            
            // 更新code唯一索引缓存
            if (StringUtils.isNotEmpty(qualityControlRule.getMetricCode())) {
                codeCache.put(qualityControlRule.getMetricCode(), qualityControlRule);
            }
        }
        return result;
    }

    /**
     * 修改质控规则
     * 
     * @param qualityControlRule 质控规则
     * @return 结果
     */
    @Override
    public int updateQualityControlRule(QualityControlRule qualityControlRule) 
    {
        QualityControlRule oldRule = qualityControlRuleCache.get(qualityControlRule.getId());
        int result = qualityControlRuleMapper.updateQualityControlRule(qualityControlRule);
        if (result > 0) {
            // 更新缓存
            qualityControlRuleCache.put(qualityControlRule.getId(), qualityControlRule);
            
            // 如果指标编码发生变化，需要更新code缓存
            if (oldRule != null && !oldRule.getMetricCode().equals(qualityControlRule.getMetricCode())) {
                // 更新旧的code缓存
                codeCache.remove(oldRule.getMetricCode());
                // 更新新的code缓存
                if (StringUtils.isNotEmpty(qualityControlRule.getMetricCode())) {
                    codeCache.put(qualityControlRule.getMetricCode(), qualityControlRule);
                }
            } else {
                // 指标编码未变化，直接更新code缓存
                if (StringUtils.isNotEmpty(qualityControlRule.getMetricCode())) {
                    codeCache.put(qualityControlRule.getMetricCode(), qualityControlRule);
                }
            }
        }
        return result;
    }

    /**
     * 批量删除质控规则
     * 
     * @param ids 需要删除的质控规则主键
     * @return 结果
     */
    @Override
    public int deleteQualityControlRuleByIds(Long[] ids) 
    {
        int result = qualityControlRuleMapper.deleteQualityControlRuleByIds(ids);
        if (result > 0) {
            // 更新缓存
            for (Long id : ids) {
                QualityControlRule rule = qualityControlRuleCache.remove(id);
                if (rule != null) {
                    // 移除code缓存
                    codeCache.remove(rule.getMetricCode());
                }
            }
        }
        return result;
    }

    /**
     * 删除质控规则信息
     * 
     * @param id 质控规则主键
     * @return 结果
     */
    @Override
    public int deleteQualityControlRuleById(Long id) 
    {
        QualityControlRule rule = qualityControlRuleCache.get(id);
        int result = qualityControlRuleMapper.deleteQualityControlRuleById(id);
        if (result > 0 && rule != null) {
            // 更新缓存
            qualityControlRuleCache.remove(id);
            
            // 移除code缓存
            codeCache.remove(rule.getMetricCode());
        }
        return result;
    }
}