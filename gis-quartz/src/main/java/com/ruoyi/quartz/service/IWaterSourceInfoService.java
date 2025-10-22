package com.ruoyi.quartz.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.domain.WaterSourceInfo;

import java.util.List;

/**
 * 地下水型饮用水水源基础信息服务接口
 */
public interface IWaterSourceInfoService {
    /**
     * 批量插入数据，参数是List<String> 里面的String是sourceId，插入的数据只有sourceId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param sourceCodes 水源编码列表
     * @return 结果
     */
    public int insertWaterSourceInfoBatch(List<String> sourceCodes);

    /**
     * 修改数据，参数是waterSourceInfo，修改的属性包括sourceLevel、manager等。修改前判断参数的字段是否存在
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    public int updateWaterSourceInfo(WaterSourceInfo waterSourceInfo);

    /**
     * 分页查询，按照入库时间倒序排序
     *
     * @param waterSourceInfo 水源对象
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return 水源分页数据
     */
    public TableDataInfo selectWaterSourceInfoList(WaterSourceInfo waterSourceInfo, int pageNum, int pageSize);

    /**
     * 插入单条数据，参数就是单个WaterSourceInfo
     *
     * @param waterSourceInfo 水源对象
     * @return 结果
     */
    public int insertWaterSourceInfo(WaterSourceInfo waterSourceInfo);

    /**
     * 查询所有不重复的水源名称
     *
     * @return 水源名称列表
     */
    public List<String> selectAllSourceNames();


}
