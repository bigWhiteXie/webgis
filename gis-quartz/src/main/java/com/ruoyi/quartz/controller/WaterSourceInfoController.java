package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.WaterSourceInfo;
import com.ruoyi.quartz.service.IWaterSourceInfoService;
import com.ruoyi.quartz.util.GisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 地下水型饮用水水源基础信息Controller
 */
@RestController
@RequestMapping("/waterSourceInfo")
@Api(tags = "水源地信息管理")
public class WaterSourceInfoController extends BaseController {

    @Autowired
    private IWaterSourceInfoService waterSourceInfoService;
    
    /**
     * 批量删除水源信息
     *
     * @param sourceIds 水源ID列表
     * @return 结果
     */
    @DeleteMapping("/batch")
    @ApiOperation("批量删除水源信息")
    public AjaxResult batchDeleteWaterSourceInfo(@ApiParam("水源ID列表") @RequestBody List<Long> sourceIds) {
        try {
            if (sourceIds == null || sourceIds.isEmpty()) {
                return AjaxResult.error("水源ID列表不能为空");
            }
            
            int result = waterSourceInfoService.deleteWaterSourceInfoByIds(sourceIds);
            return toAjax(result);
        } catch (Exception e) {
            logger.error("批量删除水源信息失败", e);
            return AjaxResult.error("批量删除水源信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传水源边界信息
     *
     * @param file SHP文件
     * @return WKT格式数据
     */
    @PostMapping("/importShp")
    @ApiOperation("上传水源边界")
    public AjaxResult importShpData(@ApiParam("SHP文件") @RequestParam("file") MultipartFile file,
                                    @ApiParam("水源编码") @RequestParam String sourceCode) {
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
            // 检查wtkdata的类型必须是POLYGON,否则返回异常告诉用户shp的类型
            if (!wktData.startsWith("POLYGON")) {
                return AjaxResult.error("请上传正确的SHP文件，文件类型必须是POLYGON");
            }
            // 更新水源坐标信息
            WaterSourceInfo waterSourceInfo = new WaterSourceInfo();
            waterSourceInfo.setSourceCode(sourceCode);
            waterSourceInfo.setGeom(wktData);
            waterSourceInfoService.updateWaterSourceInfo(waterSourceInfo);
            // 删除临时文件
            tempFile.delete();

            return AjaxResult.success("解析SHP文件成功", wktData);
        } catch (Exception e) {
            logger.error("解析SHP文件失败", e);
            return AjaxResult.error("解析SHP文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 导入Excel文件
     *
     * @param file Excel文件
     * @return 导入结果
     */
    @PostMapping("/importExcel")
    @ApiOperation("导入Excel文件")
    public AjaxResult importExcelData(@ApiParam("Excel文件") @RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("上传文件为空");
            }

            // 检查文件扩展名
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))) {
                return AjaxResult.error("请上传.xls或.xlsx格式的Excel文件");
            }

            // 调用Service层方法解析并导入Excel数据
            return waterSourceInfoService.parseAndImportExcelFile(file);
        } catch (Exception e) {
            logger.error("导入Excel文件失败", e);
            return AjaxResult.error("导入Excel文件失败: " + e.getMessage());
        }
    }

    /**
     * 更新水源信息
     *
     * @param waterSourceInfo 水源信息
     * @return 结果
     */
    @PutMapping
    @ApiOperation("更新水源信息")
    public AjaxResult edit(@ApiParam("水源信息") @RequestBody WaterSourceInfo waterSourceInfo) {
        try {
            // 检查sourceCode是否为空
            if (waterSourceInfo.getSourceCode() == null || waterSourceInfo.getSourceCode().trim().isEmpty()) {
                return AjaxResult.error("水源编码不能为空");
            }

            return toAjax(waterSourceInfoService.updateWaterSourceInfo(waterSourceInfo));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 分页查询水源列表
     *
     * @param waterSourceInfo 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 水源分页数据
     */
    @GetMapping("/list")
    @ApiOperation("分页查询水源列表")
    public TableDataInfo list(WaterSourceInfo waterSourceInfo,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize) {
        TableDataInfo table = waterSourceInfoService.selectWaterSourceInfoList(waterSourceInfo, pageNum, pageSize);
        return table;
    }

    /**
     * 插入单个水源数据
     *
     * @param waterSourceInfo 水源信息
     * @return 结果
     */
    @PostMapping
    @ApiOperation("插入单个水源数据")
    public AjaxResult insertWaterSourceInfo(@ApiParam("水源信息") @RequestBody WaterSourceInfo waterSourceInfo) {
        try {
            int result = waterSourceInfoService.insertWaterSourceInfo(waterSourceInfo);
            return toAjax(result);
        } catch (Exception e) {
            logger.error("插入水源数据失败", e);
            return AjaxResult.error("插入水源数据失败: " + e.getMessage());
        }
    }
    /**
     * 获取所有水源名称
     *
     * @return 水源名称列表
     */
    @GetMapping("/sourceNames")
    @ApiOperation("获取所有水源名称")
    public AjaxResult getAllSourceNames() {
        try {
            List<String> sourceNames = waterSourceInfoService.selectAllSourceNames();
            return AjaxResult.success("查询成功", sourceNames);
        } catch (Exception e) {
            logger.error("查询水源名称失败", e);
            return AjaxResult.error("查询水源名称失败: " + e.getMessage());
        }
    }

    // 在类的其他方法之后添加
    /**
     * 查询指定空间范围内的所有水源地信息
     *
     * @param minX 最小经度
     * @param minY 最小纬度
     * @param maxX 最大经度
     * @param maxY 最大纬度
     * @return 水源地列表（只包含sourceId、geom和waterQualityCategory）
     */
    @GetMapping("/spatial")
    @ApiOperation("查询指定空间范围内的所有水源地信息")
    public AjaxResult listBySpatialBounds(
            @ApiParam("最小经度") @RequestParam(required = false) Double minX,
            @ApiParam("最小纬度") @RequestParam(required = false) Double minY,
            @ApiParam("最大经度") @RequestParam(required = false) Double maxX,
            @ApiParam("最大纬度") @RequestParam(required = false) Double maxY) {
        try {
            // 调用service层方法查询水源地信息
            List<WaterSourceInfo> list = waterSourceInfoService.selectWaterSourceInfoSimpleListBySpatialBounds(minX, minY, maxX, maxY);
            
            // 由于无法创建新的响应对象类，这里使用一个简单的方式返回所需字段
            List<Map<String, Object>> resultList = list.stream().map(source -> {
                Map<String, Object> map = new HashMap<>();
                map.put("sourceId", source.getSourceId());
                map.put("geom", source.getGeom());
                map.put("waterQualityCategory", source.getWaterQualityCategory());
                return map;
            }).collect(Collectors.toList());
            
            return AjaxResult.success(resultList);
        } catch (Exception e) {
            logger.error("查询空间范围内水源地信息失败", e);
            return AjaxResult.error("查询空间范围内水源地信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据空间范围查询水源地信息（仅返回id、geom和waterQualityCategory字段）
     */
    @GetMapping("/listBySpatialBoundsSimple")
    public AjaxResult listBySpatialBoundsSimple(
        @RequestParam(value = "minX", required = false) Double minX,
        @RequestParam(value = "minY", required = false) Double minY,
        @RequestParam(value = "maxX", required = false) Double maxX,
        @RequestParam(value = "maxY", required = false) Double maxY
    ) {
        List<WaterSourceInfo> list = waterSourceInfoService.selectWaterSourceInfoSimpleListBySpatialBounds(minX, minY, maxX, maxY);
        // 只返回需要的字段
        List<Map<String, Object>> resultList = list.stream().map(source -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sourceId", source.getSourceId());
            map.put("geom", source.getGeom());
            map.put("waterQualityCategory", source.getWaterQualityCategory());
            return map;
        }).collect(Collectors.toList());
        return AjaxResult.success(resultList);
    }

    /**
     * 根据水源ID查询水源详细信息（关联location表查询省市区名称）
     *
     * @param sourceId 水源ID
     * @return 水源详细信息
     */
    @GetMapping("/{sourceId}")
    @ApiOperation("根据水源ID查询水源详细信息")
    public AjaxResult getWaterSourceInfoById(@ApiParam("水源ID") @PathVariable Long sourceId) {
        try {
            WaterSourceInfo waterSourceInfo = waterSourceInfoService.selectWaterSourceInfoById(sourceId);
            if (waterSourceInfo == null) {
                return AjaxResult.error("未找到对应的水源信息");
            }
            return AjaxResult.success("查询成功", waterSourceInfo);
        } catch (Exception e) {
            logger.error("查询水源详细信息失败", e);
            return AjaxResult.error("查询水源详细信息失败: " + e.getMessage());
        }
    }
}