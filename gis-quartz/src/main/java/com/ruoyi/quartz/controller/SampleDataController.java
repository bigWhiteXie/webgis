package com.ruoyi.quartz.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.quartz.service.ISampleDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}