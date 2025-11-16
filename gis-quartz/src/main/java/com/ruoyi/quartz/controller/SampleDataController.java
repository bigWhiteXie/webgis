package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.SampleData;
import com.ruoyi.quartz.domain.api.MetricPair;
import com.ruoyi.quartz.domain.api.MetricValItem;
import com.ruoyi.quartz.domain.api.SampleDataResp;
import com.ruoyi.quartz.domain.api.SampleDataVo;
import com.ruoyi.quartz.service.ISampleDataService;
import com.ruoyi.quartz.service.impl.SampleDataServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 地下水样品检测信息Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/sample")
@Api(tags = "监测数据管理")
public class SampleDataController extends BaseController {
    
    @Autowired
    private ISampleDataService sampleDataService;
    
    /**
     * 查询指定时间范围内监测井的单项指标数据
     *
     * @param monitoringWellCode 监测井编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param metricName 指标编号
     * @return 指定时间范围内监测井的单项指标数据列表
     */
    @GetMapping("/data/range")
    @ApiOperation("查询指定时间范围内监测井的单项指标数据")
    public AjaxResult getSampleDataByTimeRange(
            @ApiParam(value = "监测井编号", required = true)
            @RequestParam("monitoringWellCode") String monitoringWellCode,
            @ApiParam(value = "开始时间", required = true)
            @RequestParam("startTime") Date startTime,
            @ApiParam(value = "结束时间", required = true)
            @RequestParam("endTime") Date endTime,
            @ApiParam(value = "指标名称", required = true)
            @RequestParam("metricName") String metricName) {

        List<MetricValItem> dataList = sampleDataService.getSampleDataByTimeRange(monitoringWellCode, startTime, endTime, metricName);
        return AjaxResult.success(dataList);
    }
    
    /**
     * 上传Excel文件并导入地下水样品检测信息
     * 
     * @param file Excel文件
     * @return 操作结果
     */
    @PostMapping("/import")
    @ApiOperation("上传Excel文件并导入地下水样品检测信息")
    public AjaxResult importSampleData(@ApiParam("Excel文件") @RequestParam(value = "file", required = false) MultipartFile file) {
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
            int count = sampleDataService.parseAndImportExcelFile(file);

            return AjaxResult.success("成功导入" + count + "条地下水样品检测信息");
        } catch (Exception e) {
            logger.error("导入地下水样品检测信息失败", e);
            return AjaxResult.error("导入地下水样品检测信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 导出原始地下水样品检测数据为Excel
     *
     * @param sampleData 条件查询对象
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return excel 文件
     */
    @PostMapping("/export/raw")
    @ApiOperation("导出原始地下水样品检测数据为Excel")
    public void exportRaw(SampleData sampleData,
                          @ApiParam("开始时间") @RequestParam(value = "startTime", required = false) Date startTime,
                          @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) Date endTime,
                          HttpServletResponse response) {
        List<SampleData> list = sampleDataService.selectSampleDataList(sampleData, startTime, endTime);
        ExcelUtil<SampleData> util = new ExcelUtil<SampleData>(SampleData.class);
        util.exportExcel(response, list, "原始地下水样品检测数据", "", "地下水样品检测数据.xlsx");
    }
    
    /**
     * 获取监测井的水质数据
     * 
     * @param monitoringWellCode 监测井编号(必需传)
     * @param date 时间(默认当前时间)
     * @return 监测井的水质数据
     */
    @GetMapping("/data")
    @ApiOperation("获取监测井的水质数据")
    public AjaxResult getSampleData(
            @ApiParam(value = "监测井编号", required = true) 
            @RequestParam("monitoringWellCode") String monitoringWellCode,
            @ApiParam(value = "时间(默认当前时间)", required = false) 
            @RequestParam(value = "date", required = false) Date date) {
        // 处理默认时间值
        if (date == null) {
            date = new Date();
        }
        // 调用service方法获取数据
        SampleDataResp sampleData = sampleDataService.getSampleDataByMonitoringWellAndMetrics(
                monitoringWellCode, date, null);
        
        // 返回结果
        if (sampleData == null) {
            return AjaxResult.error("未找到相关数据");
        }
        
        return AjaxResult.success(sampleData);
    }
    
    /**
     * 获取所有水质指标
     * 
     * @return 所有水质指标列表
     */
    @GetMapping("/metrics")
    @ApiOperation("获取所有水质指标")
    public AjaxResult getAllWaterQualityMetrics() {
        List<MetricPair> metrics = sampleDataService.getAllWaterQualityMetrics();
        return AjaxResult.success(metrics);
    }
    
    /**
     * 分页查询SampleDataResp列表
     * 
     * @param monitoringWellCode 监测井编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @param projectId 项目编号（可选）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @GetMapping("/list")
    @ApiOperation("分页查询监测数据列表")
    public TableDataInfo getSampleDataRespList(
            @ApiParam(value = "监测井编号", required = false)
            @RequestParam(value = "monitoringWellCode", required = false) String monitoringWellCode,
            @ApiParam(value = "开始时间", required = false)
            @RequestParam(value = "startTime", required = false) Date startTime,
            @ApiParam(value = "结束时间", required = false)
            @RequestParam(value = "endTime", required = false) Date endTime,
            @ApiParam(value = "项目编号", required = false)
            @RequestParam(value = "projectId", required = false) String projectId,
            @ApiParam(value = "页码", required = false, defaultValue = "1")
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", required = false, defaultValue = "10")
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return sampleDataService.getSampleDataRespList(monitoringWellCode, startTime, endTime, projectId, pageNum, pageSize);
    }
    
    /**
     * 修改监测井的相关指标检测数据
     *
     * @param sampleDataVo 监测井指标检测数据
     * @return 操作结果
     */
    @PutMapping("/update")
    @ApiOperation("修改监测井的相关指标检测数据")
    public AjaxResult updateSampleData(@RequestBody SampleDataVo sampleDataVo) {
        try {
            // 检查必要参数
            if (sampleDataVo == null) {
                return AjaxResult.error("参数不能为空");
            }
            
            if (sampleDataVo.getMonitoringWellCode() == null || sampleDataVo.getMonitoringWellCode().trim().isEmpty()) {
                return AjaxResult.error("监测井编码不能为空");
            }
            
            if (sampleDataVo.getSamplingTime() == null) {
                return AjaxResult.error("采样时间不能为空");
            }
            
            if (sampleDataVo.getMetricValues() == null || sampleDataVo.getMetricValues().isEmpty()) {
                return AjaxResult.error("指标值列表不能为空");
            }
            

             sampleDataService.updateSampleData(sampleDataVo);
            
            return AjaxResult.success("数据更新成功");
        } catch (Exception e) {
            logger.error("更新监测井指标检测数据失败", e);
            return AjaxResult.error("数据更新失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除地下水样品检测信息
     * 
     * @param ids 需要删除的ID列表
     * @return 操作结果
     */
    @ApiOperation("批量删除地下水样品检测信息")
    @DeleteMapping("/delete")
    public AjaxResult deleteSampleDataByIds(
            @ApiParam("ID列表")
            @RequestBody List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return AjaxResult.error("请选择需要删除的数据");
            }
            
            int result = sampleDataService.deleteSampleDataByIds(ids);
            return AjaxResult.success("删除成功，共删除" + result + "条记录");
        } catch (Exception e) {
            logger.error("删除地下水样品检测信息失败", e);
            return AjaxResult.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取监测井的水质数据及各指标的质量等级
     * 
     * @param monitoringWellCode 监测井编号
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 监测井的水质数据列表，包含各指标的质量等级
     */
    @GetMapping("/quality-levels")
    @ApiOperation("获取监测井的水质数据及各指标的质量等级")
    public AjaxResult getSampleDataWithQualityLevels(
            @ApiParam(value = "监测井编号", required = true)
            @RequestParam("monitoringWellCode") String monitoringWellCode,
            @ApiParam(value = "开始时间", required = false)
            @RequestParam(value = "startTime", required = false) Date startTime,
            @ApiParam(value = "结束时间", required = false)
            @RequestParam(value = "endTime", required = false) Date endTime) {
        try {
            List<SampleDataResp> dataList = sampleDataService.getSampleDataWithQualityLevels(
                    monitoringWellCode, startTime, endTime);
            return AjaxResult.success(dataList);
        } catch (Exception e) {
            logger.error("获取监测井的水质数据及质量等级失败", e);
            return AjaxResult.error("获取数据失败: " + e.getMessage());
        }
    }
}