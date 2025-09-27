-- 创建地下水型饮用水水源基础信息表
CREATE TABLE groundwater_source_info (
    -- 基本信息
   source_id SERIAL PRIMARY KEY,
   serial_number INT ,
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


-- 为空间查询创建空间索引
CREATE INDEX idx_geom ON groundwater_drinking_water_source USING GIST (geom);

-- 为上报水质信息中的时间字段创建索引
CREATE INDEX idx_water_quality_category_time ON groundwater_drinking_water_source (water_quality_category_time);


-- 插入数据示例，使用 ST_SetSRID 和 ST_MakePoint 函数将经纬度转换为几何对象
INSERT INTO groundwater_drinking_water_source (
    serial_number, project_id, province_code, city_code, county_code, source_name, source_code, center_longitude, center_latitude, source_level, water_supply_scale, service_population, water_quality_category, water_quality_category_time, burial_condition, aquifer_medium_type, is_protection_zone_delineated, first_level_protection_zone_area, first_level_protection_zone_pollution_source_count, second_level_protection_zone_area, second_level_protection_zone_pollution_source_count, quasi_protection_zone_area, quasi_protection_zone_pollution_source_count, is_recharge_area_delineated, recharge_area_area, recharge_area_pollution_source_count, is_groundwater_pollution_source_nearby, groundwater_pollution_source_count_nearby, is_priority_controlled, priority_control_reason, is_exceeding_standard, exceeding_standard_reason, human_factor_exceeding_standard_index, human_factor_exceeding_standard_reason, implemented_control_measures, current_water_quality_status, planned_control_measures, expected_standard_reaching_time, is_water_supply_up_to_standard,
    geom
) VALUES (
     1, '2020152599S1-40001', '150000', '152500', '152501', '二连浩特市齐哈日格图饮用水水源地', 'SY0001', 112.344818, 43.187655, '地级及以上', 20000, 10, 5, '2022-01-01', '承压水', '裂隙水', true, 0.5, 0, 0, 0, 0, 0, false, NULL, NULL, false, NULL, true, '列入原因1', true, '地质背景', NULL, NULL, NULL, NULL, NULL, NULL, NULL,
     ST_SetSRID(ST_MakePoint(112.344818, 43.187655), 4490)
 );