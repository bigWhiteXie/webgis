package com.ruoyi.quartz.service;

import com.ruoyi.quartz.domain.SampleData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 地下水样品检测信息服务接口
 * 
 * @author ruoyi
 */
public interface ISampleDataService {
    
    /**
     * 导入地下水样品检测信息
     * 
     * @param sampleDataList 地下水样品检测信息列表
     * @return 结果
     */
    public String importSampleData(List<SampleData> sampleDataList);
    
    /**
     * 解析并导入Excel文件
     *
     * @param file Excel文件
     * @return 导入的记录数
     * @throws Exception 导入异常
     */
    int parseAndImportExcelFile(MultipartFile file) throws Exception;
}