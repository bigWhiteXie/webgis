package com.ruoyi.quartz.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.quartz.domain.MonitorWell;
import com.ruoyi.quartz.domain.api.MonitorWellVo;
import com.ruoyi.quartz.domain.api.SimpleWellResp;
import com.ruoyi.quartz.service.IMonitorWellService;
import com.ruoyi.quartz.util.GisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    private static final Logger log = LoggerFactory.getLogger(MonitorWellController.class);
    @Autowired
    private IMonitorWellService monitorWellService;

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询指定空间范围内的所有监测井
     *
     * @param minX 最小经度
     * @param minY 最小纬度
     * @param maxX 最大经度
     * @param maxY 最大纬度
     * @return 监测井列表
     */
    @GetMapping("/spatial")
    @ApiOperation("查询指定空间范围内的所有监测井")
    public AjaxResult listBySpatialBounds(
            @ApiParam("最小经度") @RequestParam(required = false) Double minX,
            @ApiParam("最小纬度") @RequestParam(required = false) Double minY,
            @ApiParam("最大经度") @RequestParam(required = false) Double maxX,
            @ApiParam("最大纬度") @RequestParam(required = false) Double maxY) {
        List<SimpleWellResp> list = monitorWellService.selectMonitorWellListBySpatialBounds(minX, minY, maxX, maxY);
        return AjaxResult.success(list);
    }

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
     * 上传SHP文件并解析获取WKT格式数据
     *
     * @param file SHP文件
     * @return WKT格式数据
     */
    @PostMapping("/importShp")
    @ApiOperation("上传SHP文件并解析获取WKT格式数据")
    public AjaxResult importShpData(@ApiParam("SHP文件") @RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("上传文件为空");
            }

            // 检查文件扩展名
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".shp") && !fileName.endsWith(".SHP"))) {
                return AjaxResult.error("请上传.shp格式的SHP文件");
            }

            // 创建临时文件
            File tempFile = File.createTempFile("shp_", ".shp");
            file.transferTo(tempFile);

            // 使用GisUtil解析SHP文件获取WKT数据
            String wktData = GisUtil.shpToWKT(tempFile);
            
            // 删除临时文件
            tempFile.delete();

            return AjaxResult.success("解析SHP文件成功", wktData);
        } catch (Exception e) {
            logger.error("解析SHP文件失败", e);
            return AjaxResult.error("解析SHP文件失败: " + e.getMessage());
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
    public AjaxResult getInfo(@ApiParam("监测井编码") @PathVariable String wellCode) {
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
    public AjaxResult edit(MonitorWell monitorWell, @ApiParam("附件") @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // 检查wellCode是否为空
            if (monitorWell.getWellCode() == null || monitorWell.getWellCode().trim().isEmpty()) {
                return AjaxResult.error("监测井编码不能为空");
            }
            
            if (file != null && !file.isEmpty()) {
                // 上传文件路径
                String filePath = RuoYiConfig.getUploadPath();
                // 上传并返回新文件名称
                String url = FileUploadUtils.upload(filePath, file);
                monitorWell.setImageUrl(url);
                log.info("上传文件成功，文件路径：{}", url);
            }
            return toAjax(monitorWellService.updateMonitorWell(monitorWell));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


}