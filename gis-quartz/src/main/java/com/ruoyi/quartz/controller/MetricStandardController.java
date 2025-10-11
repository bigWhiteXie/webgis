package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.WaterQualityLevel;
import com.ruoyi.quartz.domain.MetricStandard;
import com.ruoyi.quartz.service.IMetricStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
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
        // 验证relation字段是否符合要求
        if (!validateMetricStandard(metricStandard)) {
            return AjaxResult.error("参数验证失败: relation字段必须是between、between_equal、between_up_equal、between_low_equal之一，且上下界值必须符合要求");
        }
        return toAjax(metricStandardService.insertMetricStandard(metricStandard));
    }

    @ApiOperation("修改指标标准")
    @PutMapping
    public AjaxResult edit(@RequestBody MetricStandard metricStandard) {
        // 验证relation字段是否符合要求
        if (!validateMetricStandard(metricStandard)) {
            return AjaxResult.error("参数验证失败: relation字段必须是between、between_equal、between_up_equal、between_low_equal之一，且上下界值必须符合要求");
        }
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
        // 校验质量等级参数是否合法
        if (qualityLevel != null && !qualityLevel.isEmpty() && WaterQualityLevel.getByLabel(qualityLevel) == null) {
            throw new IllegalArgumentException("无效的质量等级参数: " + qualityLevel + "，有效值为: " + 
                String.join(", ", "I", "II", "III", "IV", "V", "InferiorV"));
        }
        return metricStandardService.selectMetricStandardList(metricCode, qualityLevel, pageNum, pageSize);
    }

    /**
     * 验证指标标准参数是否符合要求
     *
     * @param metricStandard 指标标准对象
     * @return 验证结果
     */
    private boolean validateMetricStandard(MetricStandard metricStandard) {
        // 验证relation字段
        List<String> validRelations = Arrays.asList("between", "between_equal", "between_up_equal", "between_low_equal");
        if (metricStandard.getRelation() == null || !validRelations.contains(metricStandard.getRelation())) {
            return false;
        }

        // 验证上下界值
        BigDecimal upperBound = metricStandard.getUpperBound();
        BigDecimal lowerBound = metricStandard.getLowerBound();

        // 上下界值不能为空
        if (upperBound == null || lowerBound == null) {
            return false;
        }

        // 下界值必须小于上界值
        if (lowerBound.compareTo(upperBound) >= 0) {
            return false;
        }

        // 验证质量等级参数
        if (metricStandard.getQualityLevel() != null && !metricStandard.getQualityLevel().isEmpty()) {
            if (WaterQualityLevel.getByLabel(metricStandard.getQualityLevel()) == null) {
                return false;
            }
        }

        return true;
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