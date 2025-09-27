package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.vo.MonitorWellVo;
import com.ruoyi.quartz.service.IMonitorWellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 监测井信息Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/well")
@Api(tags = "监测井管理")
public class MonitorWellController extends BaseController {

    @Autowired
    private IMonitorWellService monitorWellService;

    /**
     * 上传Excel文件并导入监测井数据
     * 
     * @param file Excel文件
     * @return 操作结果
     */
    @PostMapping("/import")
    @ApiOperation("上传Excel文件并导入监测井数据")
    public AjaxResult importMonitorWellData(@ApiParam("Excel文件") @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file == null) {
                return AjaxResult.error("未检测到上传文件，请确认请求的Content-Type是否为multipart/form-data");
            }
            
            if (file.isEmpty()) {
                return AjaxResult.error("上传文件为空");
            }

            // 检查文件扩展名
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".xlsx")) {
                return AjaxResult.error("请上传.xlsx格式的Excel文件");
            }

            // 解析Excel文件
            int count = monitorWellService.parseAndImportExcelFile(file);



            return AjaxResult.success("成功导入" + count + "条监测井数据");
        } catch (Exception e) {
            logger.error("导入监测井数据失败", e);
            return AjaxResult.error("导入监测井数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 分页查询监测井列表
     * 
     * @param monitorWellVo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 监测井分页数据
     */
    @GetMapping("/list")
    @ApiOperation("分页查询监测井列表")
    public TableDataInfo list(MonitorWellVo monitorWellVo,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return monitorWellService.selectMonitorWellList(monitorWellVo, pageNum, pageSize);
    }
    
    /**
     * 根据井编码查询监测井详细信息
     * 
     * @param wellCode 监测井编码
     * @return 监测井详情
     */
    @GetMapping(value = "/{wellCode}")
    @ApiOperation("根据井编码查询监测井详细信息")
    public AjaxResult getInfo(@ApiParam("监测井编码") @RequestParam String wellCode) {
        return AjaxResult.success(monitorWellService.selectMonitorWellById(wellCode));
    }
    
    /**
     * 更新监测井信息
     * 
     * @param monitorWell 监测井信息
     * @return 结果
     */
    @PutMapping
    @ApiOperation("更新监测井信息")
    public AjaxResult edit(@RequestBody MonitorWell monitorWell) {
        return toAjax(monitorWellService.updateMonitorWell(monitorWell));
    }
}