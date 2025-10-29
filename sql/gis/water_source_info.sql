-- 创建地下水型饮用水水源基础信息表
CREATE TABLE groundwater_source_info (
    -- 基本信息
   source_id SERIAL PRIMARY KEY,
   project_id VARCHAR(255) ,
   province_code VARCHAR(255) ,
   city_code VARCHAR(255) ,
   county_code VARCHAR(255) ,
   source_name VARCHAR(255) ,
   source_code VARCHAR(255) ,
   center_longitude DECIMAL(9, 6) ,
   center_latitude DECIMAL(9, 6) ,
   source_level VARCHAR(255) ,
   water_supply_scale DECIMAL(10, 2),
   service_population DECIMAL(10, 2),
    -- 上报水质信息
   water_quality_category INT,
   water_quality_category_time DATE,
    -- 水文地质信息
   burial_condition VARCHAR(255) ,
   aquifer_medium_type VARCHAR(255) ,
    -- 保护区和补给区划定信息
   is_protection_zone_delineated BOOLEAN ,
   first_level_protection_zone_area DECIMAL(10, 2),
   first_level_protection_zone_pollution_source_count INT,
   second_level_protection_zone_area DECIMAL(10, 2),
   second_level_protection_zone_pollution_source_count INT,
   quasi_protection_zone_area DECIMAL(10, 2),
   quasi_protection_zone_pollution_source_count INT,
   is_recharge_area_delineated BOOLEAN ,
   recharge_area_area DECIMAL(10, 2),
   recharge_area_pollution_source_count INT,
   is_groundwater_pollution_source_nearby BOOLEAN ,
   groundwater_pollution_source_count_nearby INT,
    -- 环境管理信息
   is_priority_controlled BOOLEAN ,
   priority_control_reason VARCHAR(255),
   is_exceeding_standard BOOLEAN ,
   exceeding_standard_reason VARCHAR(255),
   human_factor_exceeding_standard_index VARCHAR(255),
   human_factor_exceeding_standard_reason VARCHAR(255),
   implemented_control_measures VARCHAR(255),
   current_water_quality_status VARCHAR(255),
   planned_control_measures VARCHAR(255),
   expected_standard_reaching_time DATE,
   is_water_supply_up_to_standard BOOLEAN,
    -- 空间数据，用于支持空间查询
   geom geometry(Point, 4490)
);

-- 创建索引以优化查询性能
CREATE unique index idx_groundwater_source_info_source_code ON groundwater_source_info(source_code);
CREATE INDEX idx_groundwater_source_info_source_name ON groundwater_source_info(source_name);
CREATE INDEX idx_groundwater_source_info_geom ON groundwater_source_info USING GIST(geom);
CREATE INDEX idx_groundwater_source_info_water_quality_category ON groundwater_source_info(water_quality_category);

-- 添加注释说明表结构
COMMENT ON TABLE groundwater_source_info IS '地下水型饮用水水源基础信息表';
COMMENT ON COLUMN groundwater_source_info.source_id IS '水源ID';
COMMENT ON COLUMN groundwater_source_info.project_id IS '项目编号';
COMMENT ON COLUMN groundwater_source_info.province_code IS '省份代码';
COMMENT ON COLUMN groundwater_source_info.city_code IS '城市代码';
COMMENT ON COLUMN groundwater_source_info.county_code IS '区县代码';
COMMENT ON COLUMN groundwater_source_info.source_name IS '水源名称';
COMMENT ON COLUMN groundwater_source_info.source_code IS '水源编码';
COMMENT ON COLUMN groundwater_source_info.center_longitude IS '中心经度';
COMMENT ON COLUMN groundwater_source_info.center_latitude IS '中心纬度';
COMMENT ON COLUMN groundwater_source_info.source_level IS '水源级别';
COMMENT ON COLUMN groundwater_source_info.water_supply_scale IS '供水规模';
COMMENT ON COLUMN groundwater_source_info.service_population IS '服务人口';
COMMENT ON COLUMN groundwater_source_info.water_quality_category IS '水质类别';
COMMENT ON COLUMN groundwater_source_info.water_quality_category_time IS '水质类别时间';
COMMENT ON COLUMN groundwater_source_info.burial_condition IS '埋藏条件';
COMMENT ON COLUMN groundwater_source_info.aquifer_medium_type IS '含水层介质类型';
COMMENT ON COLUMN groundwater_source_info.is_protection_zone_delineated IS '是否划定保护区';
COMMENT ON COLUMN groundwater_source_info.first_level_protection_zone_area IS '一级保护区面积';
COMMENT ON COLUMN groundwater_source_info.first_level_protection_zone_pollution_source_count IS '一级保护区污染源数量';
COMMENT ON COLUMN groundwater_source_info.second_level_protection_zone_area IS '二级保护区面积';
COMMENT ON COLUMN groundwater_source_info.second_level_protection_zone_pollution_source_count IS '二级保护区污染源数量';
COMMENT ON COLUMN groundwater_source_info.quasi_protection_zone_area IS '准保护区面积';
COMMENT ON COLUMN groundwater_source_info.quasi_protection_zone_pollution_source_count IS '准保护区污染源数量';
COMMENT ON COLUMN groundwater_source_info.is_recharge_area_delineated IS '是否划定补给区';
COMMENT ON COLUMN groundwater_source_info.recharge_area_area IS '补给区面积';
COMMENT ON COLUMN groundwater_source_info.recharge_area_pollution_source_count IS '补给区污染源数量';
COMMENT ON COLUMN groundwater_source_info.is_groundwater_pollution_source_nearby IS '附近是否有地下水污染源';
COMMENT ON COLUMN groundwater_source_info.groundwater_pollution_source_count_nearby IS '附近地下水污染源数量';
COMMENT ON COLUMN groundwater_source_info.is_priority_controlled IS '是否优先控制';
COMMENT ON COLUMN groundwater_source_info.priority_control_reason IS '优先控制原因';
COMMENT ON COLUMN groundwater_source_info.is_exceeding_standard IS '是否超标';
COMMENT ON COLUMN groundwater_source_info.exceeding_standard_reason IS '超标原因';
COMMENT ON COLUMN groundwater_source_info.human_factor_exceeding_standard_index IS '人为因素超标指标';
COMMENT ON COLUMN groundwater_source_info.human_factor_exceeding_standard_reason IS '人为因素超标原因';
COMMENT ON COLUMN groundwater_source_info.implemented_control_measures IS '已实施的控制措施';
COMMENT ON COLUMN groundwater_source_info.current_water_quality_status IS '当前水质状况';
COMMENT ON COLUMN groundwater_source_info.planned_control_measures IS '拟采取的控制措施';
COMMENT ON COLUMN groundwater_source_info.expected_standard_reaching_time IS '预期达标时间';
COMMENT ON COLUMN groundwater_source_info.is_water_supply_up_to_standard IS '供水是否达标';
COMMENT ON COLUMN groundwater_source_info.geom IS '空间数据（Point类型，坐标系4490）';

-- 创建唯一约束，确保水源编码唯一
