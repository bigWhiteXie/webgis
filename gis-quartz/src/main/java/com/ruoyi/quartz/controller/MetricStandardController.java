package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.MetricStandard;
import com.ruoyi.quartz.service.IMetricStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor/metric")
@Api(tags = "检测指标管理")
public class MetricStandardController {

    @Autowired
    private IMetricStandardService metricStandardService;

    @ApiOperation("新增指标标准")
    @PostMapping
    public AjaxResult add(@RequestBody MetricStandard metricStandard) {
        return toAjax(metricStandardService.insertMetricStandard(metricStandard));
    }

    @ApiOperation("修改指标标准")
    @PutMapping
    public AjaxResult edit(@RequestBody MetricStandard metricStandard) {
        try {
            return toAjax(metricStandardService.updateMetricStandard(metricStandard));
        } catch (Exception e) {
            return AjaxResult.error("修改指标标准失败：" + e.getMessage());
        }
    }

    @ApiOperation("删除指标标准")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(metricStandardService.deleteMetricStandardByIds(ids));
    }

    @ApiOperation("分页查询指标标准")
    @GetMapping("/list")
    public TableDataInfo list(@ApiParam("指标编码") @RequestParam(required = false) String metricCode,
                              @ApiParam("质量等级") @RequestParam(required = false) String qualityLevel,
                              @ApiParam("页码") @RequestParam(defaultValue = "1") int pageNum,
                              @ApiParam("每页记录数") @RequestParam(defaultValue = "10") int pageSize) {
        return metricStandardService.selectMetricStandardList(metricCode, qualityLevel, pageNum, pageSize);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}