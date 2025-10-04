package com.ruoyi.quartz.service.impl;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.Project;
import com.ruoyi.quartz.mapper.ProjectMapper;
import com.ruoyi.quartz.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-03
 */
@Service
public class ProjectServiceImpl implements IProjectService
{
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 批量插入数据，参数是List<String> 里面的String是ProjectId，插入的数据只有ProjectId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param projectCodes 项目编号列表
     * @return 结果
     */
    @Override
    public int insertProjectBatch(List<String> projectCodes)
    {
        return projectMapper.insertProjectBatch(projectCodes);
    }

    /**
     * 修改数据，参数是project，修改的属性包括projectType、manager。修改前判断参数的字段是否存在
     *
     * @param project 项目对象
     * @return 结果
     */
    @Override
    public int updateProject(Project project)
    {
        return projectMapper.updateProject(project);
    }

    /**
     * 分页查询，按照入库时间倒序排序
     *
     * @param project 项目对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 项目分页数据
     */
    @Override
    public TableDataInfo selectProjectList(Project project, int pageNum, int pageSize)
    {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = projectMapper.selectProjectCount(project);
        
        // 查询分页数据
        List<Project> list = projectMapper.selectProjectListByConditionPager(project, offset, pageSize);
        
        // 返回分页结果
        return new TableDataInfo(list, total);
    }

    /**
     * 插入单条数据，参数就是单个Project
     *
     * @param project 项目对象
     * @return 结果
     */
    @Override
    public int insertProject(Project project)
    {
        return projectMapper.insertProject(project);
    }
    
    /**
     * 查询所有不重复的企业名称
     *
     * @return 企业名称列表
     */
    @Override
    public List<String> selectAllCompanyNames()
    {
        return projectMapper.selectAllCompanyNames();
    }
}