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
import java.util.List;

/**
 * 地下水型饮用水水源基础信息Controller
 */
@RestController
@RequestMapping("/waterSourceInfo")
@Api(tags = "水源信息管理")
public class WaterSourceInfoController extends BaseController {

    @Autowired
    private IWaterSourceInfoService waterSourceInfoService;

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

}

