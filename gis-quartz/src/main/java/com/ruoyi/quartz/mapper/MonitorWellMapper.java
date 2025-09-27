package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.SysJobLog;

import java.util.List;

public interface MonitorWellMapper {
    
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
    public List<MonitorWell> selectMonitorWellList(MonitorWell monitorWell);
    
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
}