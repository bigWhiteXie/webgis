package com.ruoyi.quartz.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.EvaluationStandardConfig;
import com.ruoyi.quartz.domain.api.EvaluationStandardConfDTO;
import com.ruoyi.quartz.service.IEvaluationStandardConfigService;
import com.ruoyi.quartz.service.IEvaluationStandardViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/evaluation/config")
@Api(tags = "评价标准配置管理")
public class EvaluationConfigController {
    
    @Autowired
    private IEvaluationStandardConfigService evaluationStandardConfigService;
    
    /**
     * 分页查询评价标准配置列表
     * 
     * @param evaluationStandardConfDTO 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @GetMapping("/list")
    @ApiOperation("分页查询评价标准配置列表")
    public TableDataInfo list(EvaluationStandardConfDTO evaluationStandardConfDTO,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        return evaluationStandardConfigService.selectEvaluationStandardConfDTOListByPage(evaluationStandardConfDTO, pageNum, pageSize);
    }
    
    /**
     * 导出评价标准配置列表
     */
    @PostMapping("/export")
    @ApiOperation("导出评价标准配置")
    public void export(HttpServletResponse response, EvaluationStandardConfig evaluationStandardConfig)
    {
        List<EvaluationStandardConfig> list = evaluationStandardConfigService.selectEvaluationStandardConfigList(evaluationStandardConfig);
        ExcelUtil<EvaluationStandardConfig> util = new ExcelUtil<EvaluationStandardConfig>(EvaluationStandardConfig.class);
        util.exportExcel(response, list, "评价标准配置表", "", "评价标准.xlsx");
    }
    
    /**
     * 导入评价标准配置列表
     */
    @PostMapping("/import")
    @ApiOperation("导入评价标准配置")
    public AjaxResult importData(@ApiParam("Excel文件") @RequestParam("file") MultipartFile file,
                                 @ApiParam("评价标准视图ID") @RequestParam("evaluationViewId") Long evaluationViewId) {
        try {
            if (file.isEmpty()) {
                return AjaxResult.error("上传文件不能为空");
            }
            // 检查文件扩展名
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".xlsx")) {
                return AjaxResult.error("请上传.xlsx格式的Excel文件");
            }
            return evaluationStandardConfigService.parseAndImportExcelFile(file, evaluationViewId);
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量激活评价标准配置
     */
    @PostMapping("/batchActivate")
    @ApiOperation("批量激活评价标准配置")
    public AjaxResult batchActivate(@ApiParam("参考标准ID") @RequestParam Long referenceStandardId) {
        return evaluationStandardConfigService.batchActivateByViewAndReference(referenceStandardId);
    }
    
    /**
     * 修改评价标准配置
     */
    @PutMapping("/update")
    @ApiOperation("修改评价标准配置")
    public AjaxResult update(@RequestBody EvaluationStandardConfig evaluationStandardConfig) {
        try {
            int result = evaluationStandardConfigService.updateEvaluationStandardConfig(evaluationStandardConfig);
            if (result > 0) {
                return AjaxResult.success("修改成功");
            } else {
                return AjaxResult.error("修改失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("修改失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量删除评价标准配置
     */
    @DeleteMapping("/batchDelete")
    @ApiOperation("批量删除评价标准配置")
    public AjaxResult batchDelete(@ApiParam("ID列表") @RequestBody List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return AjaxResult.error("请选择需要删除的数据");
            }
            
            int result = evaluationStandardConfigService.deleteEvaluationStandardConfigByIds(ids);
            return AjaxResult.success("删除成功，共删除" + result + "条记录");
        } catch (Exception e) {
            return AjaxResult.error("删除失败：" + e.getMessage());
        }
    }
    
    /**
     * 新增单条评价标准配置
     */
    @PostMapping("/add")
    @ApiOperation("新增单条评价标准配置")
    public AjaxResult add(@RequestBody EvaluationStandardConfig evaluationStandardConfig) {
        return evaluationStandardConfigService.insertEvaluationStandardConfigSingle(evaluationStandardConfig);
    }
}