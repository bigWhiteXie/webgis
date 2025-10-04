package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-03
 */
public interface ProjectMapper 
{
    /**
     * 批量插入数据，参数是List<String> 里面的String是ProjectId，插入的数据只有ProjectId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param projectCodes 项目编号列表
     * @return 结果
     */
    public int insertProjectBatch(@Param("projectCodes") List<String> projectCodes);

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
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 项目列表
     */
    public List<Project> selectProjectListByConditionPager(@Param("project") Project project, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 查询项目数据总记录数
     *
     * @param project 查询条件
     * @return 总记录数
     */
    public int selectProjectCount(@Param("project") Project project);

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
}