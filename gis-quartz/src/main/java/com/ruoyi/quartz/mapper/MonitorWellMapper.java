package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.domain.api.SimpleWellResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonitorWellMapper {
    
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
    List<SimpleWellResp> selectMonitorWellListBySpatialBounds(@Param("minX") Double minX, @Param("minY") Double minY, 
                                                              @Param("maxX") Double maxX, @Param("maxY") Double maxY, 
                                                              @Param("metricName") String metricName);
    
    /**
     * 批量插入监测井数据
     * 
     * @param monitorWells 监测井列表
     * @return 插入记录数
     */
    public int batchInsertMonitorWells(List<MonitorWell> monitorWells);
    
    /**
     * 分页查询监测井数据
     * 
     * @param monitorWell 查询条件
     * @return 监测井列表
     */
    public List<MonitorWell> selectMonitorWellList(@Param("monitorWell") MonitorWell monitorWell, @Param("offset") int offset, @Param("pageSize") int pageSize);
    
    /**
     * 查询监测井数据总记录数
     * 
     * @param monitorWell 查询条件
     * @return 总记录数
     */
    public int selectMonitorWellCount(MonitorWell monitorWell);
    
    /**
     * 根据ID查询监测井信息
     * 
     * @param wellCode 监测井编码
     * @return 监测井信息
     */
    public MonitorWell selectMonitorWellById(String wellCode);
    
    /**
     * 更新监测井信息
     * 
     * @param monitorWell 监测井信息
     * @return 更新结果
     */
    public int updateMonitorWell(MonitorWell monitorWell);
    
    /**
     * 查询所有监测井编号
     * 
     * @return 监测井编号列表
     */
    public List<String> selectAllWellCodes();
    
    /**
     * 根据监测井编号列表删除监测井
     * 
     * @param wellCodes 监测井编号列表
     * @return 删除记录数
     */
    public int deleteMonitorWellByWellCodes(List<String> wellCodes);
}