package com.ruoyi.quartz.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.Project;
import com.ruoyi.quartz.service.IProjectService;
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
 * 项目信息Controller
 * 
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目管理")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    /**
     * 查询项目详细信息
     *
     * @param id 项目ID
     * @return 项目详细信息
     */
    @GetMapping("/{id}")
    @ApiOperation("查询项目详细信息")
    public AjaxResult getProjectDetail(@ApiParam("项目ID") @PathVariable Long id) {
        try {
            Project project = projectService.selectProjectById(id);
            if (project == null) {
                return AjaxResult.error("未找到指定项目");
            }
            return AjaxResult.success(project);
        } catch (Exception e) {
            logger.error("查询项目详细信息失败", e);
            return AjaxResult.error("查询项目详细信息失败: " + e.getMessage());
        }
    }

    /**
     * 上传项目边界信息
     *
     * @param file SHP文件
     * @return WKT格式数据
     */
    @PostMapping("/importShp")
    @ApiOperation("上传项目边界")
    public AjaxResult importShpData(@ApiParam("SHP文件") @RequestParam("file") MultipartFile file,
                                    @ApiParam("项目编号")@RequestParam String projectCode) {
        try {
            // todo:公共逻辑，可以进行封装
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
            if (!wktData.startsWith("POLYGON")) {
                return AjaxResult.error("请上传正确的SHP文件，文件类型必须是POLYGON");
            }
            // 更新项目坐标信息
            Project project = new Project();
            project.setProjectCode(projectCode);
            project.setGeom(wktData);
            projectService.updateProject(project);
            // 删除临时文件
            tempFile.delete();

            return AjaxResult.success("解析SHP文件成功", wktData);
        } catch (Exception e) {
            logger.error("解析SHP文件失败", e);
            return AjaxResult.error("解析SHP文件失败: " + e.getMessage());
        }
    }
    /**
     * 更新项目信息
     * 
     * @param project 项目信息
     * @return 结果
     */
    @PutMapping
    @ApiOperation("更新项目信息")
    public AjaxResult edit(@ApiParam("项目信息") @RequestBody Project project) {
        try {
            // 检查projectCode是否为空
            if (project.getProjectCode() == null || project.getProjectCode().trim().isEmpty()) {
                return AjaxResult.error("项目编号不能为空");
            }
            
            return toAjax(projectService.updateProject(project));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 分页查询项目列表
     * 
     * @param project 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 项目分页数据
     */
    @GetMapping("/list")
    @ApiOperation("分页查询项目列表")
    public TableDataInfo list(Project project,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize) {
        TableDataInfo table = projectService.selectProjectList(project, pageNum, pageSize);
        return table;
    }

    /**
     * 插入单个项目数据
     * 
     * @param project 项目信息
     * @return 结果
     */
    @PostMapping
    @ApiOperation("插入单个项目数据")
    public AjaxResult insertProject(@ApiParam("项目信息") @RequestBody Project project) {
        try {
            int result = projectService.insertProject(project);
            return toAjax(result);
        } catch (Exception e) {
            logger.error("插入项目数据失败", e);
            return AjaxResult.error("插入项目数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有企业名称
     * 
     * @return 企业名称列表
     */
    @GetMapping("/companyNames")
    @ApiOperation("获取所有企业名称")
    public AjaxResult getAllCompanyNames() {
        try {
            List<String> companyNames = projectService.selectAllCompanyNames();
            return AjaxResult.success("查询成功", companyNames);
        } catch (Exception e) {
            logger.error("查询企业名称失败", e);
            return AjaxResult.error("查询企业名称失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据项目编号列表批量删除项目
     * 
     * @param projectCodes 项目编号列表
     * @return 结果
     */
    @DeleteMapping
    @ApiOperation("批量删除项目")
    public AjaxResult deleteProjects(@ApiParam("项目编号列表") @RequestBody List<String> projectCodes) {
        if (projectCodes == null || projectCodes.isEmpty()) {
            return AjaxResult.error("项目编号列表不能为空");
        }
        return toAjax(projectService.deleteProjectsByProjectCodes(projectCodes));
    }
}