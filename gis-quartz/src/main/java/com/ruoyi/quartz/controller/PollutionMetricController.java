package com.ruoyi.quartz.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.PollutionMetric;
import com.ruoyi.quartz.service.IPollutionMetricService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 污染物指标Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/pollution/metric")
@Api(tags = "污染物指标管理")
public class PollutionMetricController extends BaseController
{
    @Autowired
    private IPollutionMetricService pollutionMetricService;

    /**
     * 查询污染物指标列表
     */
    @GetMapping("/list")
    @ApiOperation("查询污染物指标列表")
    public TableDataInfo list(@ApiParam("污染物指标查询条件") PollutionMetric pollutionMetric)
    {
        startPage();
        List<PollutionMetric> list = pollutionMetricService.selectPollutionMetricList(pollutionMetric);
        return getDataTable(list);
    }

    /**
     * 导出污染物指标列表
     */
    @PostMapping("/export")
    @ApiOperation("导出污染物指标列表")
    public void export(@ApiParam("响应对象") HttpServletResponse response, 
                       @ApiParam("污染物指标查询条件") PollutionMetric pollutionMetric)
    {
        List<PollutionMetric> list = pollutionMetricService.selectPollutionMetricList(pollutionMetric);
        ExcelUtil<PollutionMetric> util = new ExcelUtil<PollutionMetric>(PollutionMetric.class);
        util.exportExcel(response, list, "污染物指标数据");
    }

    /**
     * 获取污染物指标详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取污染物指标详细信息")
    public AjaxResult getInfo(@ApiParam("指标ID") @PathVariable("id") Long id)
    {
        return AjaxResult.success(pollutionMetricService.selectPollutionMetricById(id));
    }

    /**
     * 新增污染物指标
     */
    @PostMapping
    @ApiOperation("新增污染物指标")
    public AjaxResult add(@ApiParam("污染物指标信息") @RequestBody PollutionMetric pollutionMetric)
    {
        return toAjax(pollutionMetricService.insertPollutionMetric(pollutionMetric));
    }

    /**
     * 修改污染物指标
     */
    @PutMapping
    @ApiOperation("修改污染物指标")
    public AjaxResult edit(@ApiParam("污染物指标信息") @RequestBody PollutionMetric pollutionMetric)
    {
        return toAjax(pollutionMetricService.updatePollutionMetric(pollutionMetric));
    }

    /**
     * 删除污染物指标
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除污染物指标")
    public AjaxResult remove(@ApiParam("指标ID数组") @PathVariable Long[] ids)
    {
        return toAjax(pollutionMetricService.deletePollutionMetricByIds(ids));
    }

    /**
     * 导入污染物指标数据
     */
    @PostMapping("/import")
    @ApiOperation("导入污染物指标数据")
    public AjaxResult importData(@ApiParam("上传的Excel文件") @RequestParam("file") MultipartFile file) throws Exception {
        ExcelUtil<PollutionMetric> util = new ExcelUtil<PollutionMetric>(PollutionMetric.class);
        List<PollutionMetric> pollutionMetricList = util.importExcel(file.getInputStream());

        if (pollutionMetricList == null || pollutionMetricList.isEmpty()) {
            return AjaxResult.error("Excel文件中没有数据");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (PollutionMetric metric : pollutionMetricList) {
            try {
                pollutionMetricService.insertPollutionMetric(metric);
                successNum++;
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>污染物指标 " + metric.getMetricName() + " 导入失败：" + e.getMessage();
                failureMsg.append(msg);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return AjaxResult.error(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
            return AjaxResult.success(successMsg.toString());
        }
    }
}