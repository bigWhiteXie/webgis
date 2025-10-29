package com.ruoyi.quartz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.WaterSourceInfo;
import com.ruoyi.quartz.mapper.WaterSourceInfoMapper;
import com.ruoyi.quartz.service.IWaterSourceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 地下水型饮用水水源基础信息服务实现类
 */
@Service
public class WaterSourceInfoServiceImpl implements IWaterSourceInfoService {
    protected static final Logger log = LoggerFactory.getLogger(WaterSourceInfoServiceImpl.class);
    
    @Autowired
    private WaterSourceInfoMapper waterSourceInfoMapper;

    /**
     * 批量插入数据，参数是List<String> 里面的String是sourceId，插入的数据只有sourceId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param sourceCodes 水源编码列表
     * @return 结果
     */
    @Override
    public int insertWaterSourceInfoBatch(List<String> sourceCodes) {
        return waterSourceInfoMapper.insertWaterSourceInfoBatch(sourceCodes);
    }

    /**
     * 修改数据，参数是waterSourceInfo，修改的属性包括sourceLevel、manager等。修改前判断参数的字段是否存在
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    @Override
    public int updateWaterSourceInfo(WaterSourceInfo waterSourceInfo) {
        return waterSourceInfoMapper.updateWaterSourceInfo(waterSourceInfo);
    }

    /**
     * 分页查询，按照入库时间倒序排序
     *
     * @param waterSourceInfo 水源对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 水源分页数据
     */
    @Override
    public TableDataInfo selectWaterSourceInfoList(WaterSourceInfo waterSourceInfo, int pageNum, int pageSize) {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;

        // 查询总记录数
        int total = waterSourceInfoMapper.selectWaterSourceInfoCount(waterSourceInfo);

        // 查询分页数据
        List<WaterSourceInfo> list = waterSourceInfoMapper.selectWaterSourceInfoListByConditionPager(waterSourceInfo, offset, pageSize);

        // 返回分页结果
        return new TableDataInfo(list, total);
    }

    /**
     * 插入单条数据，参数就是单个WaterSourceInfo
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    @Override
    public int insertWaterSourceInfo(WaterSourceInfo waterSourceInfo) {
        return waterSourceInfoMapper.insertWaterSourceInfo(waterSourceInfo);
    }


    /**
     * 查询所有不重复的水源名称
     *
     * @return 水源名称列表
     */
    @Override
    public List<String> selectAllSourceNames() {
        return waterSourceInfoMapper.selectAllSourceNames();
    }

    @Override
    public List<WaterSourceInfo> selectWaterSourceInfoSimpleListBySpatialBounds(Double minX, Double minY, Double maxX, Double maxY) {
        return waterSourceInfoMapper.selectWaterSourceInfoSimpleListBySpatialBounds(minX, minY, maxX, maxY);
    }

    /**
     * 根据水源ID查询水源详细信息（关联location表查询省市区名称）
     *
     * @param sourceId 水源ID
     * @return 水源详细信息
     */
    @Override
    public WaterSourceInfo selectWaterSourceInfoById(Long sourceId) {
        return waterSourceInfoMapper.selectWaterSourceInfoById(sourceId);
    }

    /**
     * 解析并导入Excel文件
     *
     * @param file Excel文件
     * @return 导入结果
     */
    @Override
    public AjaxResult parseAndImportExcelFile(MultipartFile file) {
        try {
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
                            List<WaterSourceInfo> waterSourceInfos = WaterSourceInfo.parseExcelData(batchData);
                            log.info("第{}张sheet解析出{}条数据", sheet.getSheetNo() + 1, waterSourceInfos.size());

                            // 批量导入到数据库
                            if (!waterSourceInfos.isEmpty()) {
                                int imported = waterSourceInfoMapper.batchInsert(waterSourceInfos);
                                totalImported += imported;
                                log.info("成功导入{}条水源数据，处理进度：{}/{}",
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
            return AjaxResult.success("成功导入 " + totalImported + " 条数据");
        } catch (Exception e) {
            log.error("导入Excel数据失败", e);
            return AjaxResult.error("导入Excel数据失败: " + e.getMessage());
        }
    }
}