package com.ruoyi.quartz.controller;

import java.util.List;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.quartz.domain.QualityControlRule;
import com.ruoyi.quartz.service.IQualityControlRuleService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 质控规则Controller
 * 
 */
@RestController
@RequestMapping("/quality/rule")
@Api(tags = "质控规则管理")
public class QualityControlRuleController extends BaseController
{
    @Autowired
    private IQualityControlRuleService qualityControlRuleService;

    /**
     * 查询质控规则列表
     */
    @GetMapping("/list")
    @ApiOperation("查询质控规则列表")
    public TableDataInfo list(@ApiParam("质控规则查询条件") QualityControlRule qualityControlRule)
    {
        startPage();
        List<QualityControlRule> list = qualityControlRuleService.selectQualityControlRuleList(qualityControlRule);
        return getDataTable(list);
    }

    /**
     * 导出质控规则列表
     */
    @PostMapping("/export")
    @ApiOperation("导出质控规则列表")
    public void export(HttpServletResponse response, @ApiParam("质控规则查询条件") QualityControlRule qualityControlRule)
    {
        List<QualityControlRule> list = qualityControlRuleService.selectQualityControlRuleList(qualityControlRule);
        ExcelUtil<QualityControlRule> util = new ExcelUtil<QualityControlRule>(QualityControlRule.class);
        util.exportExcel(response, list, "质控规则数据","","质控规则.xlsx");
    }

    /**
     * 获取质控规则详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取质控规则详细信息")
    public AjaxResult getInfo(@ApiParam("质控规则ID") @PathVariable("id") Long id)
    {
        return AjaxResult.success(qualityControlRuleService.selectQualityControlRuleById(id));
    }

    /**
     * 新增质控规则
     */
    @PostMapping
    @ApiOperation("新增质控规则")
    public AjaxResult add(@ApiParam("质控规则信息") @RequestBody QualityControlRule qualityControlRule)
    {
        return toAjax(qualityControlRuleService.insertQualityControlRule(qualityControlRule));
    }

    /**
     * 修改质控规则
     */
    @PutMapping
    @ApiOperation("修改质控规则")
    public AjaxResult edit(@ApiParam("质控规则信息") @RequestBody QualityControlRule qualityControlRule)
    {
        return toAjax(qualityControlRuleService.updateQualityControlRule(qualityControlRule));
    }

    /**
     * 删除质控规则
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除质控规则")
    public AjaxResult remove(@ApiParam("质控规则ID数组") @PathVariable Long[] ids)
    {
        return toAjax(qualityControlRuleService.deleteQualityControlRuleByIds(ids));
    }

    /**
     * 导入质控规则数据
     */
    @PostMapping("/import")
    @ApiOperation("导入质控规则数据")
    public AjaxResult importData(@ApiParam("上传的Excel文件") @RequestParam("file") MultipartFile file) throws Exception {
        ExcelUtil<QualityControlRule> util = new ExcelUtil<QualityControlRule>(QualityControlRule.class);
        List<QualityControlRule> qualityControlRuleList = util.importExcel(file.getInputStream());

        if (qualityControlRuleList == null || qualityControlRuleList.isEmpty()) {
            return AjaxResult.error("Excel文件中没有数据");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (QualityControlRule rule : qualityControlRuleList) {
            try {
                qualityControlRuleService.insertQualityControlRule(rule);
                successNum++;
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>质控规则 " + rule.getMetricName() + " 导入失败：" + e.getMessage();
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