package com.ruoyi.quartz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.quartz.domain.SampleData;
import com.ruoyi.quartz.domain.api.MetricPair;
import com.ruoyi.quartz.domain.api.MetricValItem;
import com.ruoyi.quartz.domain.api.SampleDataResp;
import com.ruoyi.quartz.mapper.SampleDataMapper;
import com.ruoyi.quartz.service.ISampleDataService;
import com.ruoyi.quartz.service.IMetricStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class SampleDataServiceImpl implements ISampleDataService {
    protected static final Logger log = LoggerFactory.getLogger(SampleDataServiceImpl.class);

    @Autowired
    private SampleDataMapper sampleDataMapper;
    
    @Autowired
    private IMetricStandardService metricStandardService;


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
                }
            }

            excelReader.finish();
        } catch (Exception e) {
            log.warn("读取Excel文件时出错: {}", e.getMessage());
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
        SampleDataResp resp = sampleDataMapper.selectSampleDataWithMetrics(
            monitoringWellCode, metricNames, latestSamplingTime);
            
        // 如果resp不为null且包含指标值，则计算质量等级
        if (resp != null && resp.getMetricValues() != null && !resp.getMetricValues().isEmpty()) {
            // 提取指标编码和检测值列表
            List<String> metricCodes = resp.getMetricValues().stream()
                .map(MetricValItem::getMetricCode)
                .collect(Collectors.toList());
                
            List<String> values = resp.getMetricValues().stream()
                .map(MetricValItem::getValue)
                .collect(Collectors.toList());
                
            // 获取指标标准映射
            var metricStandardMap = metricStandardService.getMetricStandardMap(metricCodes);
            
            // 计算质量等级
            String qualityLevel = metricStandardService.calculateQualityLevel(metricCodes, values, metricStandardMap);
            resp.setQualityLevel(qualityLevel);
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
            SampleDataResp item = sampleDataMapper.selectSampleDataRespByGroupWithMetrics(
                baseItem.getMonitoringWellCode(), baseItem.getSamplingTime());
            if (item != null && item.getMetricValues() != null && !item.getMetricValues().isEmpty()) {
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
                item.setQualityLevel(qualityLevel);
            }
            result.add(item);
        }

        // 返回分页结果
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(result);
        rspData.setTotal(total);
        return rspData;
    }
    
    @Override
    public List<SampleData> selectSampleDataList(SampleData sampleData, Date startTime, Date endTime) {
        return sampleDataMapper.selectSampleDataList(sampleData, startTime, endTime);
    }
}