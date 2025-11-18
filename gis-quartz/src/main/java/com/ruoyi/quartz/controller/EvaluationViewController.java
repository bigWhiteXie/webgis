package com.ruoyi.quartz.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.file.DocumentToHtmlConverter;
import com.ruoyi.quartz.domain.EvaluationStandardView;
import com.ruoyi.quartz.mapper.EvaluationStandardViewMapper;
import com.ruoyi.quartz.service.IEvaluationStandardViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/evaluation/view")
@Api(tags = "评价标准文件管理")
public class EvaluationViewController {
    
    @Autowired
    private IEvaluationStandardViewService evaluationStandardViewService;
    
    @Autowired
    private EvaluationStandardViewMapper evaluationStandardViewMapper;
    
    /**
     * 导入评价标准文件
     *
     * @param file 评价标准文件
     * @return 操作结果
     */
    @PostMapping("/import")
    @ApiOperation("导入评价标准文件")
    public AjaxResult importEvaluationStandard(@ApiParam("评价标准文件") @RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("上传文件为空");
            }

            // 检查文件类型是否是常见文档类型、pdf、doc等
            String[] allowedExtension = MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION;
            String fileName = file.getOriginalFilename();
            String extension = FileUploadUtils.getExtension(file);
            if (!FileUploadUtils.isAllowedExtension(extension, allowedExtension)) {
                return AjaxResult.error("不支持的文件类型，请上传常见文档类型、PDF或DOC等文件");
            }

            // 上传文件得到文件路径
            String filePath = RuoYiConfig.getUploadPath();
            String url = FileUploadUtils.upload(filePath, file);

            // 创建一条记录落库
            EvaluationStandardView evaluationStandardView = new EvaluationStandardView();
            evaluationStandardView.setStandardName(fileName);
            evaluationStandardView.setFilePath(url);
            evaluationStandardViewService.insertEvaluationStandardView(evaluationStandardView);

            return AjaxResult.success("文件上传成功", url);
        } catch (Exception e) {
            return AjaxResult.error("文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载评价标准文件
     *
     * @param id 评价标准ID
     * @param response 响应对象
     */
    @GetMapping("/download/{id}")
    @ApiOperation("下载评价标准文件")
    public void downloadEvaluationStandard(@ApiParam("评价标准ID") @PathVariable Long id, HttpServletResponse response) {
        try {
            // 根据ID查找记录
            EvaluationStandardView evaluationStandardView = evaluationStandardViewMapper.selectById(id);
            if (evaluationStandardView == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "未找到指定的评价标准文件");
                return;
            }
            
            // 获取文件路径
            String filePath = evaluationStandardView.getFilePath();
            if (filePath == null || filePath.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件路径不存在");
                return;
            }
            
            // 获取文件在服务器上的真实路径
            String realPath = RuoYiConfig.getProfile() + filePath.substring(filePath.indexOf("/upload"));
            
            // 检查文件是否允许下载
            if (!FileUtils.checkAllowDownload(realPath)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "文件非法，不允许下载");
                return;
            }
            
            // 获取文件名
            String fileName = evaluationStandardView.getStandardName();
            
            // 设置响应头
            response.setContentType("application/octet-stream");
            FileUtils.setAttachmentResponseHeader(response, fileName);
            
            // 写入文件内容
            FileUtils.writeBytes(realPath, response.getOutputStream());
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件下载失败: " + e.getMessage());
            } catch (Exception ex) {
                // 忽略异常
            }
        }
    }
    
    /**
     * 分页查询评价标准
     *
     * @param evaluationStandardView 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @GetMapping("/list")
    @ApiOperation("分页查询评价标准")
    public TableDataInfo list(EvaluationStandardView evaluationStandardView,
                              @ApiParam("页码") @RequestParam(defaultValue = "1") int pageNum,
                              @ApiParam("每页记录数") @RequestParam(defaultValue = "10") int pageSize) {
        return evaluationStandardViewService.selectEvaluationStandardViewList(evaluationStandardView, pageNum, pageSize);
    }
    
    /**
     * 预览评价标准文件
     *
     * @param id 评价标准ID
     * @param response 响应对象
     */
    @GetMapping("/preview/{id}")
    @ApiOperation("预览评价标准文件")
    public void previewEvaluationStandard(@ApiParam("评价标准ID") @PathVariable Long id, HttpServletResponse response) {
        try {
            // 根据ID查找记录
            EvaluationStandardView evaluationStandardView = evaluationStandardViewMapper.selectById(id);
            if (evaluationStandardView == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "未找到指定的评价标准文件");
                return;
            }
            
            // 获取文件路径
            String filePath = evaluationStandardView.getFilePath();
            if (filePath == null || filePath.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件路径不存在");
                return;
            }
            
            // 获取文件在服务器上的真实路径
            String realPath = RuoYiConfig.getProfile() + filePath.substring(filePath.indexOf("/upload"));
            
            // 检查文件是否允许预览
            if (!FileUtils.checkAllowDownload(realPath)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "文件非法，不允许预览");
                return;
            }
            
            // 获取文件扩展名
            String fileName = evaluationStandardView.getStandardName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            
            // 使用DocumentToHtmlConverter将文件转换为HTML
            String htmlContent = DocumentToHtmlConverter.convertToHtml(realPath, extension);
            
            // 设置响应内容类型为HTML
            response.setContentType("text/html;charset=UTF-8");
            
            // 输出HTML内容到响应
            response.getWriter().write(htmlContent);
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件预览失败: " + e.getMessage());
            } catch (IOException ex) {
                // 忽略异常
            }
        }
    }
}