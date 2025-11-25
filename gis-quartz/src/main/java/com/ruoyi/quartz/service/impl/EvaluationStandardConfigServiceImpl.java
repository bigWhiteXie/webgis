package com.ruoyi.quartz.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.EvaluationStandardConfig;
import com.ruoyi.quartz.domain.MetricStandard;
import com.ruoyi.quartz.domain.PollutionMetric;
import com.ruoyi.quartz.domain.api.EvaluationStandardConfDTO;
import com.ruoyi.quartz.mapper.EvaluationStandardConfigMapper;
import com.ruoyi.quartz.mapper.EvaluationStandardViewMapper;
import com.ruoyi.quartz.mapper.MetricStandardMapper;
import com.ruoyi.quartz.mapper.PollutionMetricMapper;
import com.ruoyi.quartz.service.IEvaluationStandardConfigService;
import com.ruoyi.quartz.service.IMetricStandardService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评价标准配置Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class EvaluationStandardConfigServiceImpl implements IEvaluationStandardConfigService
{
    private static final Logger log = LoggerFactory.getLogger(EvaluationStandardConfigServiceImpl.class);
    
    // 批量插入每次处理的数据条数
    private static final int BATCH_SIZE = 1000;

    @Autowired
    private EvaluationStandardConfigMapper evaluationStandardConfigMapper;
    
    @Autowired
    private MetricStandardMapper metricStandardMapper;
    
    @Autowired
    private EvaluationStandardViewMapper evaluationStandardViewMapper;
    
    @Autowired
    private PollutionMetricMapper pollutionMetricMapper;
    
    @Autowired
    private IMetricStandardService metricStandardService;

    /**
     * 插入评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    @Override
    public int insertEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig)
    {
        // 如果metricCode为空但metricName不为空，则根据metricName查找对应的metricCode
        if ((evaluationStandardConfig.getMetricCode() == null || evaluationStandardConfig.getMetricCode().isEmpty()) 
                && evaluationStandardConfig.getMetricName() != null && !evaluationStandardConfig.getMetricName().isEmpty()) {
            PollutionMetric pollutionMetric = pollutionMetricMapper.selectByMetricName(evaluationStandardConfig.getMetricName());
            if (pollutionMetric != null) {
                evaluationStandardConfig.setMetricCode(pollutionMetric.getMetricCode());
                evaluationStandardConfig.setMetricName(pollutionMetric.getMetricName());
            }
        }
        
        return evaluationStandardConfigMapper.insertEvaluationStandardConfig(evaluationStandardConfig);
    }
    
    /**
     * 批量插入评价标准配置记录
     *
     * @param evaluationStandardConfigs 评价标准配置对象列表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertEvaluationStandardConfigBatch(List<EvaluationStandardConfig> evaluationStandardConfigs) {
        if (evaluationStandardConfigs == null || evaluationStandardConfigs.isEmpty()) {
            return 0;
        }
        
        int totalInserted = 0;
        
        // 分批处理，避免一次性插入太多数据
        for (int i = 0; i < evaluationStandardConfigs.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, evaluationStandardConfigs.size());
            List<EvaluationStandardConfig> subList = evaluationStandardConfigs.subList(i, endIndex);
            
            // 处理每条记录，确保metricCode字段正确填充
            for (EvaluationStandardConfig config : subList) {
                // 如果metricCode为空但metricName不为空，则根据metricName查找对应的metricCode
                if ((config.getMetricCode() == null || config.getMetricCode().isEmpty()) 
                        && config.getMetricName() != null && !config.getMetricName().isEmpty()) {
                    PollutionMetric pollutionMetric = pollutionMetricMapper.selectByMetricName(config.getMetricName());
                    if (pollutionMetric != null) {
                        config.setMetricCode(pollutionMetric.getMetricCode());
                        config.setMetricName(pollutionMetric.getMetricName());
                    }
                }
            }
            
            // 批量插入
            try {
                int inserted = evaluationStandardConfigMapper.insertEvaluationStandardConfigBatch(subList);
                totalInserted += inserted;
                log.info("成功插入{}条评价标准配置记录，进度：{}/{}", inserted, endIndex, evaluationStandardConfigs.size());
            } catch (Exception e) {
                log.error("批量插入评价标准配置记录失败，索引范围：{}-{}", i, endIndex - 1, e);
                // 可以选择继续处理下一批，或抛出异常中断整个过程
                throw e;
            }
        }
        
        return totalInserted;
    }
    
    /**
     * 查询评价标准配置记录列表
     *
     * @param evaluationStandardConfig 查询条件
     * @return 评价标准配置记录列表
     */
    @Override
    public List<EvaluationStandardConfig> selectEvaluationStandardConfigList(EvaluationStandardConfig evaluationStandardConfig)
    {
        return evaluationStandardConfigMapper.selectEvaluationStandardConfigList(evaluationStandardConfig);
    }
    
    /**
     * 分页查询评价标准配置记录
     *
     * @param evaluationStandardConfig 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @Override
    public TableDataInfo selectEvaluationStandardConfigListByPage(EvaluationStandardConfig evaluationStandardConfig, int pageNum, int pageSize)
    {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = evaluationStandardConfigMapper.selectEvaluationStandardConfigCount(evaluationStandardConfig);
        
        // 查询分页数据，使用Map传递多个参数以匹配Mapper XML中的定义
        Map<String, Object> params = new HashMap<>();
        params.put("evaluationStandardConfig", evaluationStandardConfig);
        params.put("offset", offset);
        params.put("limit", pageSize);
        
        List<EvaluationStandardConfig> rows = evaluationStandardConfigMapper.selectEvaluationStandardConfigListByPage(params);
        
        return new TableDataInfo(rows, total);
    }
    
    /**
     * 分页查询评价标准配置DTO记录
     *
     * @param evaluationStandardConfig 查询条件
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    @Override
    public TableDataInfo selectEvaluationStandardConfDTOListByPage(EvaluationStandardConfig evaluationStandardConfig, int pageNum, int pageSize)
    {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;
        
        // 查询总记录数
        int total = evaluationStandardConfigMapper.selectEvaluationStandardConfDTOCount(evaluationStandardConfig);
        
        // 查询分页数据，使用Map传递多个参数以匹配Mapper XML中的定义
        Map<String, Object> params = new HashMap<>();
        params.put("evaluationStandardConfig", evaluationStandardConfig);
        params.put("offset", offset);
        params.put("limit", pageSize);
        
        List<EvaluationStandardConfDTO> rows = evaluationStandardConfigMapper.selectEvaluationStandardConfDTOListByPage(params);
        
        return new TableDataInfo(rows, total);
    }
    
    /**
     * 解析并导入Excel文件
     *
     * @param file Excel文件
     * @param evaluationViewId 评价标准视图ID
     * @return 导入结果
     */
    @Override
    @Transactional
    public AjaxResult parseAndImportExcelFile(MultipartFile file, Long evaluationViewId) {
        try {
            // 解析Excel数据
            ExcelUtil<EvaluationStandardConfig> util = new ExcelUtil<EvaluationStandardConfig>(EvaluationStandardConfig.class);
            List<EvaluationStandardConfig> evaluationStandardConfigList = util.importExcel(file.getInputStream());
            
            if (evaluationStandardConfigList == null || evaluationStandardConfigList.isEmpty()) {
                return AjaxResult.error("Excel文件中没有数据");
            }
            
            // 设置referenceStandardId
            if (evaluationViewId != null) {
                for (EvaluationStandardConfig config : evaluationStandardConfigList) {
                    config.setReferenceStandardId(evaluationViewId);
                }
            }

            // 批量插入数据到数据库
            int totalInserted = insertEvaluationStandardConfigBatch(evaluationStandardConfigList);

            StringBuilder resultMsg = new StringBuilder();
            resultMsg.append("导入完成，成功插入 ").append(totalInserted).append(" 条数据");

            return AjaxResult.success(resultMsg.toString());
        } catch (Exception e) {
            log.error("导入Excel文件失败", e);
            return AjaxResult.error("导入Excel文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量激活评价标准配置
     *
     * @param configs 评价标准配置列表
     * @return 操作结果
     */
    @Override
    @Transactional
    public AjaxResult batchActivate(List<EvaluationStandardConfig> configs) {
        try {
            // 单独处理每个config
            for (EvaluationStandardConfig config : configs) {
                // 2. 取出metricCode的所有evaluationId(空值则不取)
                Set<Long> evaluationIds = new HashSet<>();
                if (config.getMetricCode() != null && !config.getMetricCode().isEmpty()) {
                    List<MetricStandard> metricStandards = metricStandardMapper.selectMetricStandardListByMetricCode(config.getMetricCode());
                    for (MetricStandard standard : metricStandards) {
                        if (standard.getEvaluationId() != null) {
                            evaluationIds.add(standard.getEvaluationId());
                        }
                    }
                }

                // 3. 如果evaluationId不是自己则将其对应的evaluationConfig记录的status设置成0
                for (Long evaluationId : evaluationIds) {
                    if (!config.getId().equals(evaluationId)) {
                        EvaluationStandardConfig configToUpdate = new EvaluationStandardConfig();
                        configToUpdate.setId(evaluationId);
                        configToUpdate.setStatus(0); // 设置为停用状态
                        evaluationStandardConfigMapper.updateEvaluationStandardConfigStatus(configToUpdate);
                    }
                }
                
                // 4. 更新当前选中的记录状态为1（启用）
                config.setStatus(1); // 设置为启用状态
                evaluationStandardConfigMapper.updateEvaluationStandardConfigStatus(config);

                // 5. 针对这些evaluationConfig转成多条metricStandard
                List<MetricStandard> metricStandards = config.toMetricStandards();
                // 删除该metricCode对应的standard
                if (config.getMetricCode() != null && !config.getMetricCode().isEmpty()) {
                    metricStandardMapper.deleteMetricStandardByMetricCodes(Arrays.asList(config.getMetricCode()));
                }

                // 6. 批量插入这些metricStandard
                if (!metricStandards.isEmpty()) {
                    metricStandardService.insertMetricStandardBatch(metricStandards);
                }
            }
            
            return AjaxResult.success("批量激活成功，共处理 " + configs.size() + " 条记录");
        } catch (Exception e) {
            log.error("批量激活评价标准配置失败", e);
            return AjaxResult.error("批量激活失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据评价标准视图ID和参考标准ID批量激活评价标准配置
     *
     * @param referenceStandardId 参考标准ID
     * @return 操作结果
     */
    @Override
    @Transactional
    public AjaxResult batchActivateByViewAndReference(Long referenceStandardId) {
        try {
            // 构造查询条件
            EvaluationStandardConfig queryConfig = new EvaluationStandardConfig();
            queryConfig.setReferenceStandardId(referenceStandardId);
            
            // 查询符合条件的记录
            List<EvaluationStandardConfig> configs = evaluationStandardConfigMapper.selectEvaluationStandardConfigList(queryConfig);
            
            // 获取ID列表
            List<Long> ids = configs.stream().map(EvaluationStandardConfig::getId).collect(Collectors.toList());
            
            if (ids.isEmpty()) {
                return AjaxResult.success("没有找到符合条件的评价标准配置记录");
            }
            
            // 调用原有的批量激活方法
            return batchActivate(configs);
        } catch (Exception e) {
            log.error("根据评价标准视图ID和参考标准ID批量激活评价标准配置失败", e);
            return AjaxResult.error("批量激活失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    @Override
    public int updateEvaluationStandardConfig(EvaluationStandardConfig evaluationStandardConfig) {
        metricStandardMapper.deleteMetricStandardByMetricCodes(Arrays.asList(evaluationStandardConfig.getMetricCode()));
        if (evaluationStandardConfig.getStatus() == 1) {
            List<MetricStandard> metricStandards = evaluationStandardConfig.toMetricStandards();
            metricStandardService.insertMetricStandardBatch(metricStandards);
        }

        return evaluationStandardConfigMapper.updateEvaluationStandardConfig(evaluationStandardConfig);
    }
    
    /**
     * 批量删除评价标准配置记录
     *
     * @param ids 评价标准配置ID列表
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteEvaluationStandardConfigByIds(List<Long> ids) {
        // ids转成Long[]
        Long[] idsArray = ids.stream().map(Long::valueOf).toArray(Long[]::new);
        metricStandardService.deleteMetricStandardByIds(idsArray);
        return evaluationStandardConfigMapper.deleteEvaluationStandardConfigByIds(ids);
    }
    
    /**
     * 插入单条评价标准配置记录
     *
     * @param evaluationStandardConfig 评价标准配置对象
     * @return 结果
     */
    @Override
    public AjaxResult insertEvaluationStandardConfigSingle(EvaluationStandardConfig evaluationStandardConfig) {
        try {
            // 检查必填字段
            if (evaluationStandardConfig.getReferenceStandardId() == null) {
                return AjaxResult.error("参考标准ID不能为空");
            }
            
            if (StringUtils.isEmpty(evaluationStandardConfig.getMetricCode()) && 
                StringUtils.isEmpty(evaluationStandardConfig.getMetricName())) {
                return AjaxResult.error("指标编码和指标名称不能同时为空");
            }
            
            // 设置默认状态为停用
            if (evaluationStandardConfig.getStatus() == null) {
                evaluationStandardConfig.setStatus(0);
            }
            
            // 插入记录
            int result = insertEvaluationStandardConfig(evaluationStandardConfig);
            
            if (result > 0) {
                return AjaxResult.success("新增成功");
            } else {
                return AjaxResult.error("新增失败");
            }
        } catch (Exception e) {
            log.error("新增评价标准配置记录失败", e);
            return AjaxResult.error("新增失败: " + e.getMessage());
        }
    }
}