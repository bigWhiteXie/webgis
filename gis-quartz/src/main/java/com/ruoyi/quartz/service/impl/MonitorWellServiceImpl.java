package com.ruoyi.quartz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.api.MonitorWellVo;
import com.ruoyi.quartz.domain.api.SimpleWellResp;
import com.ruoyi.quartz.mapper.MonitorWellMapper;
import com.ruoyi.quartz.service.IMonitorWellService;
import com.ruoyi.quartz.service.IProjectService;
import com.ruoyi.quartz.service.ISampleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorWellServiceImpl implements IMonitorWellService {
    protected static final Logger log = LoggerFactory.getLogger(MonitorWellServiceImpl.class);
    
    @Autowired
    private MonitorWellMapper monitorWellMapper;
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private ISampleDataService sampleDataService;

    @Override
    public List<SimpleWellResp> selectMonitorWellListBySpatialBounds(Double minX, Double minY, Double maxX, Double maxY, String metricName) {
        return monitorWellMapper.selectMonitorWellListBySpatialBounds(minX, minY, maxX, maxY, metricName);
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
                        List<MonitorWell> monitorWells = MonitorWell.parseExcelData(batchData);
                        log.info("第{}张sheet解析出{}条数据",i+1, monitorWells.size());
                        // 批量导入到数据库
                        if (!monitorWells.isEmpty()) {
                            int imported = monitorWellMapper.batchInsertMonitorWells(monitorWells);
                            
                            // 将当前的MonitorWell的projectID去重后插入project表
                            List<String> projectIds = monitorWells.stream()
                                    .map(MonitorWell::getProjectId)
                                    .filter(projectId -> projectId != null && !projectId.isEmpty())
                                    .distinct()
                                    .collect(Collectors.toList());
                            
                            if (!projectIds.isEmpty()) {
                                projectService.insertProjectBatch(projectIds);
                            }
                            
                            totalImported += imported;
                            log.info("成功导入{}条监测井数据，处理进度：{}/{}", 
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
    public TableDataInfo selectMonitorWellList(MonitorWellVo monitorWellVo, int pageNum, int pageSize) {
        // 将VO转换为实体对象用于查询
        MonitorWell monitorWell = new MonitorWell();
        monitorWell.setWellCode(monitorWellVo.getWellCode());
        monitorWell.setProjectId(monitorWellVo.getProjectId());
        monitorWell.setProvinceCode(monitorWellVo.getProvinceCode());
        monitorWell.setCityCode(monitorWellVo.getCityCode());
        monitorWell.setCountyCode(monitorWellVo.getCountyCode());
        if (monitorWellVo.getCompletionTime() != null) {
            monitorWell.setCompletionTime(monitorWellVo.getCompletionTime());
        }
        
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = monitorWellMapper.selectMonitorWellCount(monitorWell);
        
        // 查询分页数据
        List<MonitorWell> list = monitorWellMapper.selectMonitorWellList(monitorWell, offset, pageSize);
        
        // 返回分页结果
        return new TableDataInfo(list, total);
    }
    
    /**
     * 查询监测井列表（用于导出所有数据）
     * 
     * @param monitorWellVo 查询条件
     * @return 监测井列表
     */
    public List<MonitorWell> selectMonitorWellList(MonitorWellVo monitorWellVo) {
        // 将VO转换为实体对象用于查询
        MonitorWell monitorWell = new MonitorWell();
        monitorWell.setWellCode(monitorWellVo.getWellCode());
        monitorWell.setProjectId(monitorWellVo.getProjectId());
        monitorWell.setProvinceCode(monitorWellVo.getProvinceCode());
        monitorWell.setCityCode(monitorWellVo.getCityCode());
        monitorWell.setCountyCode(monitorWellVo.getCountyCode());
        if (monitorWellVo.getCompletionTime() != null) {
            monitorWell.setCompletionTime(monitorWellVo.getCompletionTime());
        }
        
        // 查询所有数据
        return monitorWellMapper.selectMonitorWellList(monitorWell, 0, Integer.MAX_VALUE);
    }
    
    @Override
    public MonitorWell selectMonitorWellById(String wellCode) {
        return monitorWellMapper.selectMonitorWellById(wellCode);
    }
    
    @Override
    public int updateMonitorWell(MonitorWell monitorWell) {
        // 设置geom字段为WKT格式
        if (monitorWell.getLongitude() != null && monitorWell.getLatitude() != null) {
            monitorWell.setGeom("POINT(" + monitorWell.getLongitude() + " " + monitorWell.getLatitude() + ")");
        }
        return monitorWellMapper.updateMonitorWell(monitorWell);
    }
    
    @Override
    public List<String> selectAllWellCodes() {
        return monitorWellMapper.selectAllWellCodes();
    }
    
    @Override
    public int deleteMonitorWellByWellCodes(List<String> wellCodes) {
        if (wellCodes == null || wellCodes.isEmpty()) {
            return 0;
        }
        // First delete associated sample data
        sampleDataService.deleteSampleDataByWellCodes(wellCodes);
        
        // Then delete the monitor wells themselves
        return monitorWellMapper.deleteMonitorWellByWellCodes(wellCodes);
    }
}