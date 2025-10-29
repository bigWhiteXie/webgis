package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.WaterSourceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地下水型饮用水水源基础信息Mapper接口
 */
public interface WaterSourceInfoMapper {
    /**
     * 批量插入数据，参数是List<String> 里面的String是sourceId，插入的数据只有sourceId还有入库时间(当前时间)。
     * 如果唯一索引冲突了则什么都不做
     *
     * @param sourceCodes 水源编码列表
     * @return 结果
     */
    public int insertWaterSourceInfoBatch(@Param("sourceCodes") List<String> sourceCodes);

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
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 水源列表
     */
    public List<WaterSourceInfo> selectWaterSourceInfoListByConditionPager(@Param("waterSourceInfo") WaterSourceInfo waterSourceInfo, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询水源数据总记录数
     *
     * @param waterSourceInfo 查询条件
     * @return 总记录数
     */
    public int selectWaterSourceInfoCount(@Param("waterSourceInfo") WaterSourceInfo waterSourceInfo);

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

    /**
     * 根据空间范围查询水源地信息（仅返回id、geom和waterQualityCategory字段）
     * @param minX 最小经度
     * @param minY 最小纬度
     * @param maxX 最大经度
     * @param maxY 最大纬度
     * @return 水源地信息列表
     */
    List<WaterSourceInfo> selectWaterSourceInfoSimpleListBySpatialBounds(
        @Param("minX") Double minX, 
        @Param("minY") Double minY, 
        @Param("maxX") Double maxX, 
        @Param("maxY") Double maxY
    );

    /**
     * 根据水源ID查询水源详细信息（关联location表查询省市区名称）
     *
     * @param sourceId 水源ID
     * @return 水源详细信息
     */
    WaterSourceInfo selectWaterSourceInfoById(Long sourceId);

    /**
     * 批量插入水源地信息
     *
     * @param waterSourceInfos 水源地信息列表
     * @return 结果
     */
    int batchInsert(@Param("waterSourceInfos") List<WaterSourceInfo> waterSourceInfos);
}