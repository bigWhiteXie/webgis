package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.api.MonitorWellVo;
import com.ruoyi.quartz.domain.api.SimpleWellResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 监测井信息Service接口
 * 
 * @author ruoyi
 */
public interface IMonitorWellService 
{
    /**
     * 根据空间范围查询监测井列表
     * 
     * @param minX 最小经度
     * @param minY 最小纬度
     * @param maxX 最大经度
     * @param maxY 最大纬度
     * @param metricName 指标名称
     * @return 监测井列表
     */
    List<SimpleWellResp> selectMonitorWellListBySpatialBounds(Double minX, Double minY, Double maxX, Double maxY, String metricName);
    
    /**
     * 解析并导入Excel文件
     * 
     * @param file Excel文件
     * @return 导入记录数
     * @throws Exception 异常
     */
    int parseAndImportExcelFile(MultipartFile file) throws Exception;
    
    /**
     * 分页查询监测井列表
     * 
     * @param monitorWellVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 监测井分页数据
     */
    TableDataInfo selectMonitorWellList(MonitorWellVo monitorWellVo, int pageNum, int pageSize);

    /**
     * 全量查询监测井列表
     *
     * @param monitorWellVo 查询条件
     * @return 监测井分页数据
     */
    List<MonitorWell> selectMonitorWellList(MonitorWellVo monitorWellVo);

    /**
     * 根据ID查询监测井信息
     * 
     * @param wellCode 监测井编码
     * @return 监测井信息
     */
    MonitorWell selectMonitorWellById(String wellCode);
    
    /**
     * 更新监测井信息
     * 
     * @param monitorWell 监测井信息
     * @return 结果
     */
    int updateMonitorWell(MonitorWell monitorWell);
    
    /**
     * 查询所有监测井编号
     * 
     * @return 监测井编号列表
     */
    List<String> selectAllWellCodes();
    
    /**
     * 根据监测井编号列表删除监测井
     * 
     * @param wellCodes 监测井编号列表
     * @return 结果
     */
    int deleteMonitorWellByWellCodes(List<String> wellCodes);
}