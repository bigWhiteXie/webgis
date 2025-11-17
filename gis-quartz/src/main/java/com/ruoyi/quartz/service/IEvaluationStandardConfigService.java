package com.ruoyi.quartz.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.EvaluationStandardConfig;
import com.ruoyi.quartz.domain.api.EvaluationStandardConfDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 评价标准配置Service接口
 * 
 * @author ruoyi
 */
public interface IEvaluationStandardConfigService 
{
    /**
     * 插入评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public int insertEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 批量插入评价标准配置记录
     *
     * @param evaluationStandardConfigs 评价标准配置对象列表
     * @return 结果
     */
    public int insertEvaluationStandardConfigBatch(List<EvaluationStandardConfig> evaluationStandardConfigs);
    
    /**
     * 查询评价标准配置记录列表
     *
     * @param evaluationStandardConfig 查询条件
     * @return 评价标准配置记录列表
     */
    public List<EvaluationStandardConfig> selectEvaluationStandardConfigList(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 分页查询评价标准配置记录
     *
     * @param evaluationStandardConfig 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    public TableDataInfo selectEvaluationStandardConfigListByPage(EvaluationStandardConfig evaluationStandardConfig, int pageNum, int pageSize);
    
    /**
     * 分页查询评价标准配置DTO记录
     *
     * @param evaluationStandardConfig 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    public TableDataInfo selectEvaluationStandardConfDTOListByPage(EvaluationStandardConfig evaluationStandardConfig, int pageNum, int pageSize);
    
    /**
     * 解析并导入Excel文件
     *
     * @param file Excel文件
     * @param evaluationViewId 评价标准视图ID
     * @return 导入结果
     */
    public AjaxResult parseAndImportExcelFile(MultipartFile file, Long evaluationViewId);
    
    /**
     * 批量激活评价标准配置
     *
     * @param ids 评价标准配置ID列表
     * @return 操作结果
     */
    public AjaxResult batchActivate(List<Long> ids);
    
    /**
     * 根据评价标准视图ID和参考标准ID批量激活评价标准配置
     *
     * @param referenceStandardId 参考标准ID
     * @return 操作结果
     */
    public AjaxResult batchActivateByViewAndReference(Long referenceStandardId);
    
    /**
     * 更新评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public int updateEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig);
    
    /**
     * 批量删除评价标准配置记录
     *
     * @param ids 评价标准配置ID列表
     * @return 结果
     */
    public int deleteEvaluationStandardConfigByIds(List<Long> ids);
    
    /**
     * 插入单条评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    public AjaxResult insertEvaluationStandardConfigSingle(EvaluationStandardConfig evaluationStandardConfig);
}