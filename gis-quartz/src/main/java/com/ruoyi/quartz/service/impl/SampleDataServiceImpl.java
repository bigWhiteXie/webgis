package com.ruoyi.quartz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.WaterQualityLevel;
import com.ruoyi.quartz.domain.MetricStandard;
import com.ruoyi.quartz.domain.SampleData;
import com.ruoyi.quartz.domain.api.*;
import com.ruoyi.quartz.mapper.SampleDataMapper;
import com.ruoyi.quartz.service.ISampleDataService;
import com.ruoyi.quartz.service.IMetricStandardService;
import com.ruoyi.quartz.service.IQualityControlRuleService;
import com.ruoyi.quartz.domain.QualityControlRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SampleDataServiceImpl implements ISampleDataService {
    protected static final Logger log = LoggerFactory.getLogger(SampleDataServiceImpl.class);

    @Autowired
    private SampleDataMapper sampleDataMapper;
    
    @Autowired
    private IMetricStandardService metricStandardService;
    
    @Autowired
    private IQualityControlRuleService qualityControlRuleService;


    @Override
    public int parseAndImportExcelFile(MultipartFile file) throws Exception {
        int totalImported = 0;

        // 读取所有工作表
        try (InputStream inputStream = file.getInputStream()) {
            com.alibaba.excel.ExcelReader excelReader = EasyExcel.read(inputStream).build();
            List<com.alibaba.excel.read.metadata.ReadSheet> sheets = excelReader.excelExecutor().sheetList();

            for (com.alibaba.excel.read.metadata.ReadSheet sheet : sheets) {
                try (InputStream sheetInputStream = file.getInputStream()) {
                    List<Object> sheetData = EasyExcel.read(sheetInputStream)
                            .sheet(sheet.getSheetNo())
                            .doReadSync();
                    log.info("第{}个工作表共有 {} 行数据", sheet.getSheetNo() + 1, sheetData.size());
                    // 分批处理当前工作表的数据，每批最多1000条
                    int batchSize = 1000;
                    for (int i = 0; i < sheetData.size(); i += batchSize) {
                        int endIndex = Math.min(i + batchSize, sheetData.size());
                        List<Object> batchData = sheetData.subList(i, endIndex);

                        // 解析当前批次的数据
                        List<SampleData> sampleDataList = SampleData.parseExcelData(batchData);
                        log.info("第{}张sheet解析出{}条数据", sheet.getSheetNo(), sampleDataList.size());
                        
                        // 对解析后的数据进行质控规则检查
                        sampleDataList.removeIf(sampleData -> {
                            // 获取对应指标的质控规则
                            QualityControlRule rule = qualityControlRuleService.getRuleByCode(sampleData.getMetricCode());
                            // 如果存在质控规则，则进行检查
                            if (rule != null) {
                                rule.validate(new BigDecimal(sampleData.getActualTestValue()));
                            }
                            // 数据符合所有规则
                            return false;
                        });
                        
                        // 批量导入到数据库
                        if (!sampleDataList.isEmpty()) {
                            int imported = sampleDataMapper.batchInsertSampleData(sampleDataList);
                            totalImported += imported;
                            log.info("成功导入{}条地下水样品检测信息，处理进度：{}/{}",
                                    imported, endIndex, sheetData.size());
                        }
                    }
                } catch (Exception e) {
                    log.warn("读取第{}个工作表时出错: {}", sheet.getSheetNo() + 1, e.getMessage());
                    throw e; // 重新抛出异常以便上层处理
                }
            }

            excelReader.finish();
        } catch (Exception e) {
            log.warn("读取Excel文件时出错: {}", e.getMessage());
            throw e; // 重新抛出异常以便上层处理
        }

        log.info("Excel文件解析并导入完成，总共导入{}条记录", totalImported);
        return totalImported;
    }
    
    @Override
    public SampleDataResp getSampleDataByMonitoringWellAndMetrics(
            String monitoringWellCode, 
            Date date, 
            List<String> metricNames) {
        // 先使用mapper拿到小于等于date的最近采样时间
        Date latestSamplingTime = sampleDataMapper.findLatestSamplingTimeByMonitoringWellCodeAndTime(
            monitoringWellCode, date);
        
        // 如果没有找到采样时间，则返回null
        if (latestSamplingTime == null) {
            return null;
        }
        
        // 调用selectSampleDataWithMetrics得到SampleDataResp
        List<SampleDataResp> resps = sampleDataMapper.selectSampleDataWithMetrics(
            monitoringWellCode, metricNames, latestSamplingTime);
        
        if (resps.isEmpty()) {
            return null;
        }
        
        // 创建一个统一的SampleDataResp对象来包含所有指标
        SampleDataResp resp = new SampleDataResp();
        resp.setMonitoringWellCode(monitoringWellCode);
        resp.setSamplingTime(latestSamplingTime);
        
        // 合并所有的指标数据
        List<MetricValItem> allMetricValues = new ArrayList<>();
        for (SampleDataResp r : resps) {
            if (r.getMetricValues() != null) {
                allMetricValues.addAll(r.getMetricValues());
            }
        }
        resp.setMetricValues(allMetricValues);
            
        // 如果resp不为null且包含指标值，则计算质量等级
        if (resp != null && resp.getMetricValues() != null && !resp.getMetricValues().isEmpty()) {
            // 计算每个指标的质量等级并找出最差的等级
            String worstQualityLevel = null;
            String worstMetricCode = null;
            StringBuilder worstMetricNames = new StringBuilder();
            
            for (MetricValItem valItem : resp.getMetricValues()) {
                try {
                    String singleQualityLevel = metricStandardService.calculateSingleQualityLevel(
                        valItem.getMetricCode(), valItem.getValue());
                    valItem.setLevel(singleQualityLevel);
                    
                    // 查找最差质量等级
                    if (worstQualityLevel == null || 
                        WaterQualityLevel.compareLevel(singleQualityLevel, worstQualityLevel) > 0) {
                        // 发现更差的等级，重置worstMetricCode和worstMetricNames
                        worstQualityLevel = singleQualityLevel;
                        worstMetricCode = valItem.getMetricCode();
                        worstMetricNames = new StringBuilder(valItem.getMetricName());
                    } else if (WaterQualityLevel.compareLevel(singleQualityLevel, worstQualityLevel) == 0) {
                        // 发现相同等级的指标，添加到worstMetricNames中
                        if (worstMetricNames.length() > 0) {
                            worstMetricNames.append(",");
                        }
                        worstMetricNames.append(valItem.getMetricName());
                    }
                } catch (Exception e) {
                    log.warn("计算指标 {} 的质量等级时出错: {}", valItem.getMetricCode(), e.getMessage());
                }
            }
            
            // 设置最差质量等级和相关指标信息
            resp.setQualityLevel(worstQualityLevel);
            resp.setMetricsCode(worstMetricCode);
            resp.setMetricsName(worstMetricNames.toString());
        }
        
        return resp;
    }
    
    @Override
    public List<MetricValItem> getSampleDataByTimeRange(String monitoringWellCode, Date startTime, Date endTime, String metricName) {
        return sampleDataMapper.selectSampleDataByTimeRange(monitoringWellCode, startTime, endTime, metricName);
    }
    
    @Override
    public List<MetricPair> getAllWaterQualityMetrics() {
        return sampleDataMapper.selectAllWaterQualityMetrics();
    }
    
    @Override
    public TableDataInfo getSampleDataRespList(
            String monitoringWellCode, 
            Date startTime, 
            Date endTime, 
            String projectId,
            Integer pageNum,
            Integer pageSize) {
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总数
        int total = sampleDataMapper.selectSampleDataRespCount(
            monitoringWellCode, startTime, endTime, projectId);
        
        // 分页查询基本的监测井和采样时间信息
        List<SampleDataResp> baseList = sampleDataMapper.selectSampleDataRespByGroup(
            monitoringWellCode, startTime, endTime, projectId, offset, pageSize);
        
        // 为每个基本项查询详细的指标数据
        List<SampleDataResp> result = new ArrayList<>();
        for (SampleDataResp baseItem : baseList) {
            List<SampleDataResp> items = sampleDataMapper.selectSampleDataRespByGroupWithMetrics(
                baseItem.getMonitoringWellCode(), baseItem.getSamplingTime());
            baseItem.setMetricValues(new ArrayList<MetricValItem>());
            for (SampleDataResp item : items) {
                baseItem.getMetricValues().addAll(item.getMetricValues());
            }
            
            // 计算总体水质等级
            if (baseItem.getMetricValues() != null && !baseItem.getMetricValues().isEmpty()) {
                // 提取指标编码和检测值列表
                List<String> metricCodes = baseItem.getMetricValues().stream()
                        .map(MetricValItem::getMetricCode)
                        .collect(Collectors.toList());

                List<String> values = baseItem.getMetricValues().stream()
                        .map(MetricValItem::getValue)
                        .collect(Collectors.toList());

                // 获取指标标准映射
                var metricStandardMap = metricStandardService.getMetricStandardMap(metricCodes);

                // 计算质量等级
                String qualityLevel = metricStandardService.calculateQualityLevel(metricCodes, values, metricStandardMap);
                baseItem.setQualityLevel(qualityLevel);
                
                // 查找最差质量等级对应的指标编码和名称
                String worstMetricCode = null;
                String worstMetricName = null;
                
                // 找到质量最差的指标
                for (MetricValItem valItem : baseItem.getMetricValues()) {
                    try {
                        String singleQualityLevel = metricStandardService.calculateSingleQualityLevel(
                            valItem.getMetricCode(), valItem.getValue());
                        valItem.setLevel(singleQualityLevel);
                        
                        if (worstMetricCode == null || 
                            WaterQualityLevel.compareLevel(singleQualityLevel, qualityLevel) == 0) {
                            if (worstMetricCode != null) {
                                worstMetricName += "," + valItem.getMetricName();
                            } else {
                                worstMetricCode = valItem.getMetricCode();
                                worstMetricName = valItem.getMetricName();
                            }
                        }
                    } catch (Exception e) {
                        log.warn("计算指标 {} 的质量等级时出错: {}", valItem.getMetricCode(), e.getMessage());
                    }
                }
                
                baseItem.setMetricsCode(worstMetricCode);
                baseItem.setMetricsName(worstMetricName);
            }
            
            result.add(baseItem);
        }

        // 返回分页结果
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(result);
        rspData.setTotal(total);
        return rspData;
    }

    private Map<String, Object> getQualityLevelWithMetrics(SampleDataResp item) {
        // 提取指标编码和检测值列表
        List<String> metricCodes = item.getMetricValues().stream()
                .map(MetricValItem::getMetricCode)
                .collect(Collectors.toList());

        List<String> values = item.getMetricValues().stream()
                .map(MetricValItem::getValue)
                .collect(Collectors.toList());

        // 获取指标标准映射
        var metricStandardMap = metricStandardService.getMetricStandardMap(metricCodes);

        // 计算质量等级
        String qualityLevel = metricStandardService.calculateQualityLevel(metricCodes, values, metricStandardMap);
        
        // 查找最差质量等级对应的指标编码和名称
        String worstMetricCode = null;
        String worstMetricName = null;
        
        if (item.getMetricValues() != null && !item.getMetricValues().isEmpty()) {
            // 找到质量最差的指标
            for (MetricValItem valItem : item.getMetricValues()) {
                try {
                    String singleQualityLevel = metricStandardService.calculateSingleQualityLevel(
                        valItem.getMetricCode(), valItem.getValue());
                    if (worstMetricCode == null || isWorseQualityLevel(singleQualityLevel, qualityLevel)) {
                        worstMetricCode = valItem.getMetricCode();
                        worstMetricName = valItem.getMetricName();
                        // 如果找到了与总体质量等级匹配的指标，就停止查找
                        if (singleQualityLevel.equals(qualityLevel)) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.warn("计算指标 {} 的质量等级时出错: {}", valItem.getMetricCode(), e.getMessage());
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("qualityLevel", qualityLevel);
        result.put("metricsCode", worstMetricCode);
        result.put("metricsName", worstMetricName);
        
        return result;
    }
    
    /**
     * 判断质量等级是否更差
     * @param level1 等级1
     * @param level2 等级2
     * @return 如果level1比level2更差则返回true
     */
    private boolean isWorseQualityLevel(String level1, String level2) {
        // 简化实现：根据等级名称判断（假设等级按名称排序，名称越大越差）
        // 这里需要根据实际的等级定义来实现
        return level1.compareTo(level2) > 0;
    }

    @Override
    public List<SampleData> selectSampleDataList(SampleData sampleData, Date startTime, Date endTime) {
        return sampleDataMapper.selectSampleDataList(sampleData, startTime, endTime);
    }
    
    @Override
    public int addSampleData(SampleData sampleData) {
        return sampleDataMapper.batchInsertSampleData(Arrays.asList(sampleData));
    }
    
    @Override
    public int updateSampleData(SampleDataVo sampleDataVo) {
        String monitoringWellCode = sampleDataVo.getMonitoringWellCode();
        Date samplingTime = sampleDataVo.getSamplingTime();
        List<MetricValItem> metricValues = sampleDataVo.getMetricValues();
        
        // 将metricValItem分为两组：一组有id，一组无id
        // 有id的执行批量修改操作，无id的执行批量插入操作
        List<SampleData> updateList = new ArrayList<>();
        List<SampleData> insertList = new ArrayList<>();
        
        for (MetricValItem item : metricValues) {
            SampleData data = new SampleData();
            data.setMonitoringWellCode(monitoringWellCode);
            data.setSamplingTime(samplingTime);
            data.setMetricCode(item.getMetricCode());
            // 设置其他字段
            data.setActualTestValue(item.getValue());
            
            if (item.getId() != null && item.getId() > 0) {
                // 有ID的加入更新列表
                data.setId(item.getId());
                updateList.add(data);
            } else {
                // 无ID的加入插入列表
                insertList.add(data);
            }
        }
        
        int result = 0;
        // 执行批量更新操作
        if (!updateList.isEmpty()) {
            result += sampleDataMapper.batchUpdateSampleData(updateList);
        }
        
        // 执行批量插入操作
        if (!insertList.isEmpty()) {
            result += sampleDataMapper.batchInsertSampleDataOnly(insertList);
        }
        
        return result;
    }
    
    @Override
    public int deleteSampleDataByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return sampleDataMapper.deleteSampleDataByIds(ids);
    }
    
    @Override
    public List<SampleDataResp> getSampleDataWithQualityLevels(String monitoringWellCode, Date startTime, Date endTime) {
        // 1. 查询startTime到endTime中指定monitoringWellCode的所有采集指标，
        // 相同的sample_code视作同一次采集，聚合该次采集的所有指标的信息(包括指标值、指标编码、指标名称、指标单位)
        List<SampleDataResp> rawData = sampleDataMapper.selectCompleteSampleDataWithMetrics(
            monitoringWellCode, startTime, endTime, null);

        // Group the raw data by sampling event (same sample code means same sampling event)
        Map<String, List<SampleDataResp>> groupedData = rawData.stream()
            .collect(Collectors.groupingBy(
                SampleDataResp::getSampleCode,
                LinkedHashMap::new,
                Collectors.toList()
            ));

        // Process each group to create a single SampleDataResp per sampling event
        List<SampleDataResp> result = new ArrayList<>();
        for (Map.Entry<String, List<SampleDataResp>> entry : groupedData.entrySet()) {
            List<SampleDataResp> group = entry.getValue();
            if (!group.isEmpty()) {
                // Take the first item as base since they all share the same sampling event info
                SampleDataResp consolidated = group.get(0);
            
                // Consolidate all metrics from the group
                List<MetricValItem> allMetrics = group.stream()
                    .flatMap(item -> item.getMetricValues().stream())
                    .collect(Collectors.toList());
                
                consolidated.setMetricValues(allMetrics);
                
                // 2. 使用MetricStandardServiceImpl的calculateQualityLevel方法计算每个SampleDataResp的每个指标的质量等级
                if (!consolidated.getMetricValues().isEmpty()) {
                    // Extract metric codes and values for quality calculation
                    List<String> metricCodes = allMetrics.stream()
                            .map(MetricValItem::getMetricCode)
                            .collect(Collectors.toList());
                    
                    List<String> values = allMetrics.stream()
                            .map(MetricValItem::getValue)
                            .collect(Collectors.toList());
                    
                    // Get metric standard map
                    Map<String, List<com.ruoyi.quartz.domain.MetricStandard>> metricStandardMap = metricStandardService.getMetricStandardMap(metricCodes);
                    
                    // Calculate overall quality level for this sampling event
                    String qualityLevel = metricStandardService.calculateQualityLevel(metricCodes, values, metricStandardMap);
                    consolidated.setQualityLevel(qualityLevel);
                    
                    // 3. 拿到每个指标的质量等级后通过for循环找到最差的那个指标信息，设置到SampleDataResp中
                    String worstMetricCode = null;
                    String worstMetricName = null;
                    String worstQualityLevel = null;
                    
                    for (MetricValItem valItem : allMetrics) {
                        try {
                            String singleQualityLevel = metricStandardService.calculateSingleQualityLevel(
                                valItem.getMetricCode(), valItem.getValue());
                            valItem.setLevel(singleQualityLevel); // Set individual metric quality level
                            
                            // Find the worst quality metric
                            if (worstQualityLevel == null || 
                                WaterQualityLevel.compareLevel(singleQualityLevel, worstQualityLevel) > 0) {
                                worstQualityLevel = singleQualityLevel;
                                worstMetricCode = valItem.getMetricCode();
                                worstMetricName = valItem.getMetricName();
                            }
                        } catch (Exception e) {
                            log.warn("计算指标 {} 的质量等级时出错: {}", valItem.getMetricCode(), e.getMessage());
                        }
                    }
                    
                    consolidated.setMetricsCode(worstMetricCode);
                    consolidated.setMetricsName(worstMetricName);
                }
                
                result.add(consolidated);
            }
        }
        
        return result;
    }
    
    @Override
    public int deleteSampleDataByCodes(List<SampleDataDeleteVo> deleteVos) {
        if (deleteVos == null || deleteVos.isEmpty()) {
            return 0;
        }
        return sampleDataMapper.deleteSampleDataByCodes(deleteVos);
    }
}
