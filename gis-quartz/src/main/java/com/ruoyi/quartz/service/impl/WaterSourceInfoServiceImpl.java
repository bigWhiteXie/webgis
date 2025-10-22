package com.ruoyi.quartz.service.impl;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.WaterSourceInfo;
import com.ruoyi.quartz.mapper.WaterSourceInfoMapper;
import com.ruoyi.quartz.service.IWaterSourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地下水型饮用水水源基础信息服务实现类
 */
@Service
public class WaterSourceInfoServiceImpl implements IWaterSourceInfoService {
    @Autowired
    private WaterSourceInfoMapper waterSourceInfoMapper;

    /**
     * 批量插入数据，参数是List<String> 里面的String是sourceId，插入的数据只有sourceId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param sourceCodes 水源编码列表
     * @return 结果
     */
    @Override
    public int insertWaterSourceInfoBatch(List<String> sourceCodes) {
        return waterSourceInfoMapper.insertWaterSourceInfoBatch(sourceCodes);
    }

    /**
     * 修改数据，参数是waterSourceInfo，修改的属性包括sourceLevel、manager等。修改前判断参数的字段是否存在
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    @Override
    public int updateWaterSourceInfo(WaterSourceInfo waterSourceInfo) {
        return waterSourceInfoMapper.updateWaterSourceInfo(waterSourceInfo);
    }

    /**
     * 分页查询，按照入库时间倒序排序
     *
     * @param waterSourceInfo 水源对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 水源分页数据
     */
    @Override
    public TableDataInfo selectWaterSourceInfoList(WaterSourceInfo waterSourceInfo, int pageNum, int pageSize) {
        // 计算分页起始位置
        int offset = (pageNum - 1) * pageSize;

        // 查询总记录数
        int total = waterSourceInfoMapper.selectWaterSourceInfoCount(waterSourceInfo);

        // 查询分页数据
        List<WaterSourceInfo> list = waterSourceInfoMapper.selectWaterSourceInfoListByConditionPager(waterSourceInfo, offset, pageSize);

        // 返回分页结果
        return new TableDataInfo(list, total);
    }

    /**
     * 插入单条数据，参数就是单个WaterSourceInfo
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    @Override
    public int insertWaterSourceInfo(WaterSourceInfo waterSourceInfo) {
        return waterSourceInfoMapper.insertWaterSourceInfo(waterSourceInfo);
    }


    /**
     * 查询所有不重复的水源名称
     *
     * @return 水源名称列表
     */
    @Override
    public List<String> selectAllSourceNames() {
        return waterSourceInfoMapper.selectAllSourceNames();
    }

}
