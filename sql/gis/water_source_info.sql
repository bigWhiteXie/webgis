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
