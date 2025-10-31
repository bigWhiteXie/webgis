package com.ruoyi.quartz.service.impl;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.WaterQualityLevel;
import com.ruoyi.quartz.domain.MetricStandard;
import com.ruoyi.quartz.mapper.MetricStandardMapper;
import com.ruoyi.quartz.service.IMetricStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 指标标准Service实现类
 *
 * @author ruoyi
 * @date 2025-10-02
 */
@Service
public class MetricStandardServiceImpl implements IMetricStandardService, CommandLineRunner {

    @Autowired
    private MetricStandardMapper metricStandardMapper;
    
    // 本地缓存实现
    private final Map<String, List<MetricStandard>> metricStandardCache = new ConcurrentHashMap<>();

    // 缓存键常量
    private static final String METRIC_STANDARD_CACHE_KEY_PREFIX = "metric_standard:";

    /**
     * 应用启动后加载所有指标标准到缓存
     * 
     * @param args 启动参数
     */
    @Override
    public void run(String... args) {
        loadAllMetricStandardsToCache();
    }
    
    /**
     * 加载所有指标标准到缓存
     */
    private void loadAllMetricStandardsToCache() {
        List<MetricStandard> allStandards = metricStandardMapper.selectMetricStandardList();
        
        // 按指标编码分组并缓存
        Map<String, List<MetricStandard>> groupedStandards = allStandards.stream()
                .collect(Collectors.groupingBy(MetricStandard::getMetricCode));
                
        for (Map.Entry<String, List<MetricStandard>> entry : groupedStandards.entrySet()) {
            String metricCode = entry.getKey();
            List<MetricStandard> standards = entry.getValue();
            metricStandardCache.put(metricCode, standards);
        }
    }

    /**
     * 插入指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    @Override
    @Transactional
    public int insertMetricStandard(MetricStandard metricStandard) {
        int result = metricStandardMapper.insertMetricStandard(metricStandard);
        
        // 更新缓存
        List<MetricStandard> cachedList = metricStandardCache.get(metricStandard.getMetricCode());
        if (cachedList != null) {
            // 如果缓存中已存在该metricCode的列表，则添加新数据到列表中
            cachedList.add(metricStandard);
        } else {
            // 如果缓存中不存在该metricCode的列表，则从数据库重新加载
            List<MetricStandard> newList = metricStandardMapper.selectMetricStandardListByMetricCode(metricStandard.getMetricCode());
            metricStandardCache.put(metricStandard.getMetricCode(), newList);
        }

        return result;
    }

    /**
     * 修改指标标准记录
     *
     * @param metricStandard 指标标准对象
     * @return 结果
     */
    @Override
    @Transactional
    public int updateMetricStandard(MetricStandard metricStandard) {
        int result = metricStandardMapper.updateMetricStandard(metricStandard);
        
        // 更新缓存
        List<MetricStandard> cachedList = metricStandardCache.get(metricStandard.getMetricCode());
        if (cachedList != null) {
            // 如果缓存中存在该metricCode的列表，则更新列表中的数据
            for (int i = 0; i < cachedList.size(); i++) {
                if (cachedList.get(i).getId().equals(metricStandard.getId())) {
                    cachedList.set(i, metricStandard);
                    break;
                }
            }
        }
        
        return result;
    }

    /**
     * 根据ID删除指标标准记录
     *
     * @param id 指标标准ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteMetricStandardById(Long id) {
        // 先查询要删除的对象，用于后续缓存更新
        MetricStandard metricCodeOfDeleted = metricStandardMapper.selectMetricStandardById(id);

        
        // 先从数据库删除
        int result = metricStandardMapper.deleteMetricStandardById(id);
        
        // 更新缓存
        if (metricCodeOfDeleted != null) {
            List<MetricStandard> cachedList = metricStandardCache.get(metricCodeOfDeleted);
            if (cachedList != null) {
                // 如果缓存中存在该metricCode的列表，则从列表中移除被删除的数据
                cachedList.removeIf(standard -> standard.getId().equals(id));
            }
        }
        
        return result;
    }
    
    /**
     * 批量删除指标标准记录
     *
     * @param ids 需要删除的指标标准ID数组
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteMetricStandardByIds(Long[] ids) {
        int count = 0;
        for (Long id : ids) {
            count += deleteMetricStandardById(id);
        }

        return count;
    }

    /**
     * 查询所有指标标准记录
     *
     * @return 指标标准列表
     */
    @Override
    public List<MetricStandard> selectMetricStandardList() {
        // 缓存未命中，从数据库查询
        List<MetricStandard> dbList = metricStandardMapper.selectMetricStandardList();
        return dbList;
    }

    /**
     * 根据指标编码查询指标标准记录
     *
     * @param metricCode 指标编码
     * @return 指标标准列表
     */
    @Override
    public List<MetricStandard> selectMetricStandardListByMetricCode(String metricCode) {
        List<MetricStandard> cachedList = metricStandardCache.get(metricCode);
        if (cachedList != null) {
            return cachedList;
        }
        
        // 缓存未命中，从数据库查询
        List<MetricStandard> dbList = metricStandardMapper.selectMetricStandardListByMetricCode(metricCode);
        metricStandardCache.put(metricCode, dbList);
        return dbList;
    }
    
    /**
     * 根据ID查询指标标准记录
     *
     * @param id 指标标准ID
     * @return 指标标准对象
     */
    @Override
    public MetricStandard selectMetricStandardById(Long id) {
        return metricStandardMapper.selectMetricStandardById(id);
    }


    /**
     * 分页查询指标标准
     * 
     * @param metricCode 指标编码
     * @param qualityLevel 质量等级
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @Override
    public TableDataInfo selectMetricStandardList(String metricCode, String qualityLevel, int pageNum, int pageSize) {
        // 构造查询条件对象
        MetricStandard metricStandard = new MetricStandard();
        if (metricCode != null && !metricCode.isEmpty()) {
            metricStandard.setMetricCode(metricCode);
        }
        if (qualityLevel != null && !qualityLevel.isEmpty()) {
            metricStandard.setQualityLevel(qualityLevel);
        }
        
        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = metricStandardMapper.selectMetricStandardCount(metricStandard);
        
        // 查询分页数据
        List<MetricStandard> list = metricStandardMapper.selectMetricStandardListByConditionPager(metricStandard, offset, pageSize);
        
        // 返回分页结果
        return new TableDataInfo(list, total);
    }


    /**
     * 等级计算方法
     *
     * @param metricCodes 指标编码列表
     * @param values      检测值列表
     * @return 所有指标中最差的质量等级
     */
    @Override
    public String calculateQualityLevel(List<String> metricCodes, List<String> values, Map<String, List<MetricStandard>> metricStandardMap) {
        // 参数校验
        if (metricCodes == null || values == null || metricCodes.size() != values.size()) {
            throw new IllegalArgumentException("指标编码列表和检测值列表不能为空且长度必须相等");
        }

        // 质量等级列表，用于存储有效的质量等级（忽略"无质量等级"的指标）
        List<String> qualityLevels = new ArrayList<>();
        String worstQualityLevel = WaterQualityLevel.CLASS_I.getLabel();

        // 计算每个指标的质量等级
        for (int i = 0; i < metricCodes.size(); i++) {
            String metricCode = metricCodes.get(i);
            String valueStr = values.get(i);
            try {
                BigDecimal value = new BigDecimal(valueStr);
                List<MetricStandard> standards = metricStandardMap.get(metricCode);
                if (standards != null && !standards.isEmpty()) {
                    for (MetricStandard standard : standards) {
                        // 检测值是否在当前区间内
                        if (standard.compute(value)) {
                            // 判断是否比当前最差等级还差
                            if (standard.compareLevel(worstQualityLevel) > 0) {
                                worstQualityLevel = standard.getQualityLevel();
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                // 如果检测值不是有效数字，则忽略
                continue;
            }
        }
        
        return worstQualityLevel;
    }

    @Override
    public Map<String, List<MetricStandard>> getMetricStandardMap(List<String> metricCodes) {
        // 去重后的指标编码列表
        Set<String> uniqueMetricCodes = new HashSet<>(metricCodes);

        Map<String, List<MetricStandard>> metricStandardMap = new HashMap<>();

        // 先尝试从缓存获取
        // 创建一个待删除列表，避免在遍历时修改集合
        Set<String> cachedMetricCodes = new HashSet<>();
        for (String metricCode : uniqueMetricCodes) {
            List<MetricStandard> cachedList = metricStandardCache.get(metricCode);
            if (cachedList != null) {
                metricStandardMap.put(metricCode, cachedList);
                cachedMetricCodes.add(metricCode); // 记录已缓存的metricCode
            }
        }
        // 从待查询列表中移除已缓存的元素
        uniqueMetricCodes.removeAll(cachedMetricCodes);

        // 剩余未在缓存中找到的，批量从数据库查询
        if (!uniqueMetricCodes.isEmpty()) {
            List<MetricStandard> dbStandards = metricStandardMapper.selectMetricStandardListByMetricCodes(new ArrayList<>(uniqueMetricCodes));

            // 按指标编码分组
            Map<String, List<MetricStandard>> dbGrouped = dbStandards.stream()
                    .collect(Collectors.groupingBy(MetricStandard::getMetricCode));

            // 放入缓存并加入最终结果
            for (Map.Entry<String, List<MetricStandard>> entry : dbGrouped.entrySet()) {
                String metricCode = entry.getKey();
                List<MetricStandard> standards = entry.getValue();
                metricStandardMap.put(metricCode, standards);

                // 放入缓存
                metricStandardCache.put(metricCode, standards);
            }
        }

        // 按指标编码分组，并按阈值从大到小排序
        Map<String, List<MetricStandard>> sortedMetricStandardMap = metricStandardMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted((s1, s2) -> {
                                    // 排序逻辑可以根据实际需求调整
                                    return s1.getId().compareTo(s2.getId());
                                })
                                .collect(Collectors.toList())
                ));
        return sortedMetricStandardMap;
    }
}