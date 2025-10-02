package com.ruoyi.quartz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.quartz.domain.SampleData;
import com.ruoyi.quartz.mapper.SampleDataMapper;
import com.ruoyi.quartz.service.ISampleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class SampleDataServiceImpl implements ISampleDataService {
    protected static final Logger log = LoggerFactory.getLogger(SampleDataServiceImpl.class);

    @Autowired
    private SampleDataMapper sampleDataMapper;

    @Override
    public String importSampleData(List<SampleData> sampleDataList) {
        // 这里应该实现具体的导入逻辑，比如保存到数据库
        // 目前只是示例，简单返回导入的记录数
        int successCount = 0;
        for (SampleData sampleData : sampleDataList) {
            // 在实际应用中，这里应该有保存到数据库的逻辑
            // 例如: sampleDataMapper.insertSampleData(sampleData);
            successCount++;
        }

        return "成功导入" + successCount + "条地下水样品检测信息";
    }

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
}