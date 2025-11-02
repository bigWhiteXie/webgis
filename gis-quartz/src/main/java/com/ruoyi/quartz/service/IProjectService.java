package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.Project;

import java.util.List;

/**
 * 项目Service接口
 * 
 * @author ruoyi
 * @date 2025-10-03
 */
public interface IProjectService 
{
    /**
     * 批量插入数据，参数是List<String> 里面的String是ProjectId，插入的数据只有ProjectId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param projectCodes 项目编号列表
     * @return 结果
     */
    public int insertProjectBatch(List<String> projectCodes);

    /**
     * 修改数据，参数是project，修改的属性包括projectType、manager。修改前判断参数的字段是否存在
     *
     * @param project 项目对象
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 分页查询，按照入库时间倒序排序
     *
     * @param project 项目对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 项目分页数据
     */
    public TableDataInfo selectProjectList(Project project, int pageNum, int pageSize);

    /**
     * 插入单条数据，参数就是单个Project
     *
     * @param project 项目对象
     * @return 结果
     */
    public int insertProject(Project project);
    
    /**
     * 查询所有不重复的企业名称
     *
     * @return 企业名称列表
     */
    public List<String> selectAllCompanyNames();
    
    /**
     * 根据主键查询项目详情
     *
     * @param id 项目主键
     * @return 项目信息
     */
    public Project selectProjectById(Long id);
}