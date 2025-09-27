package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.vo.MonitorWellVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMonitorWellService {
    
    /**
     * 分批解析Excel文件并导入数据库
     *
     * @param file Excel文件
     * @return 导入的记录总数
     * @throws Exception 解析或导入异常
     */
    public int parseAndImportExcelFile(MultipartFile file) throws Exception;
    
    /**
     * 分页查询监测井数据
     * 
     * @param monitorWellVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    public TableDataInfo selectMonitorWellList(MonitorWellVo monitorWellVo, int pageNum, int pageSize);
    
    /**
     * 根据ID查询监测井信息
     * 
     * @param id 监测井ID
     * @return 监测井信息
     */
    public MonitorWell selectMonitorWellById(String id);
    
    /**
     * 更新监测井信息
     * 
     * @param monitorWell 监测井信息
     * @return 更新结果
     */
    public int updateMonitorWell(MonitorWell monitorWell);
}