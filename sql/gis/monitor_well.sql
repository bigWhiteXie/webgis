-- 创建监测井信息表
CREATE EXTENSION postgis;
CREATE TABLE monitoring_well (
-- 基本信息
  id SERIAL PRIMARY KEY,
-- 自增主键，用于唯一标识每条监测井记录
  project_id VARCHAR(255) ,
-- 记录监测井所属项目的编号
  province_code VARCHAR(255) ,
-- 表示监测井所在省（区、市）的代码
  city_code VARCHAR(255) ,
-- 表示监测井所在地市的代码
  county_code VARCHAR(255) ,
-- 表示监测井所在区县的代码
  well_code VARCHAR(255) UNIQUE,
-- 监测井的唯一编码
  longitude DECIMAL(9, 6) ,
-- 监测井所在位置的经度
  latitude DECIMAL(9, 6) ,
-- 监测井所在位置的纬度
  is_new_well BOOLEAN ,
-- 布尔值，表明该监测井是否为调查评估项目新建井，是则为 true，否则为 false
-- 监测井性质
  is_area_monitoring_point BOOLEAN ,
-- 布尔值，表明该监测井是否为区域监测点，是则为 true，否则为 false
  area_monitoring_point_type VARCHAR(255),
-- 若该监测井是区域监测点，记录其具体类型
  is_water_source_monitoring_point BOOLEAN ,
-- 布尔值，表明该监测井是否为水源监测点，是则为 true，否则为 false
  water_source_info VARCHAR(255),
-- 若该监测井是水源监测点，记录水源代码、水源名称和监测点类型等相关信息
  is_pollution_source_monitoring_point BOOLEAN ,
-- 布尔值，表明该监测井是否为污染源监测点，是则为 true，否则为 false
  pollution_source_info VARCHAR(255),
-- 若该监测井是污染源监测点，记录污染源代码、污染源名称和监测点类型等相关信息
-- 成井信息
  completion_time DATE,
-- 监测井的成井时间
  water_level_depth DECIMAL(10, 2),
-- 监测井的水位埋深（单位：m）
  wellhead_elevation DECIMAL(10, 2),
-- 监测井井口的高程（单位：m）
  well_depth DECIMAL(10, 2) ,
-- 监测井的成井深度（单位：m）
  wellhead_inner_diameter DECIMAL(10, 2),
-- 监测井井口的内径（单位：mm）
  well_pipe_material VARCHAR(255) ,
-- 监测井井管的材质
  is_multiple_screen_pipe BOOLEAN ,
-- 布尔值，表明该监测井是否为多段筛管，是则为 true，否则为 false
  screen_pipe_depth VARCHAR(255) ,
-- 若该监测井有筛管，记录筛管上部和下部的埋深（单位：m）
-- 地下水类型
  burial_condition VARCHAR(255) ,
-- 地下水的埋藏条件
  aquifer_medium VARCHAR(255) ,
-- 地下水的含水介质类型
-- 环境管理信息
  well_ownership_unit VARCHAR(255) ,
-- 监测井的权属单位名称
  is_suitable_for_long_term_monitoring BOOLEAN ,
-- 布尔值，表明该监测井是否符合长期监测井要求，是则为 true，否则为 false
  is_converted_to_long_term_monitoring BOOLEAN,
-- 布尔值，表明该监测井是否已转为长期监测井，是则为 true，否则为 false
  is_maintenance_management_carried_out BOOLEAN,
-- 布尔值，表明该监测井是否开展了监测井维护管理工作，是则为 true，否则为 false
  actual_maintenance_management_unit VARCHAR(255),
-- 若开展了监测井维护管理工作，记录实际的维护管理单位名称
  is_sealed_backfilled_for_non_long_term BOOLEAN ,
-- 布尔值，对于非长期监测井，表明是否已进行封井回填，是则为 true，否则为 false
  sealed_backfilled_status VARCHAR(255),
-- 若进行了封井回填，记录封井回填的状态
-- 空间数据，用于支持空间查询，采用 CGCS2000 坐标系（SRID 为 4490）
  geom geometry(Point, 4490),
-- 存储监测井地理位置的几何对象，基于 CGCS2000 坐标系，用于空间查询操作
  image_url VARCHAR(255)
);

-- 创建空间索引，提高空间查询性能
CREATE INDEX idx_monitoring_well_geom ON monitoring_well USING GIST (geom);
-- 为well_code创建唯一索引
-- 为成井时间创建索引
CREATE INDEX idx_completion_time ON monitoring_well (completion_time);
CREATE UNIQUE INDEX unique_index_well_code ON monitoring_well (well_code);
-- 插入数据时，使用 ST_SetSRID 和 ST_MakePoint 函数将经纬度转换为几何对象，采用 CGCS2000 坐标系（SRID 为 4490）
-- 示例插入语句


-- 添加字段注释
COMMENT ON COLUMN monitoring_well.id IS '自增主键，用于唯一标识每条监测井记录';
COMMENT ON COLUMN monitoring_well.project_id IS '记录监测井所属项目的编号';
COMMENT ON COLUMN monitoring_well.province_code IS '表示监测井所在省（区、市）的代码';
COMMENT ON COLUMN monitoring_well.city_code IS '表示监测井所在地市的代码';
COMMENT ON COLUMN monitoring_well.county_code IS '表示监测井所在区县的代码';
COMMENT ON COLUMN monitoring_well.well_code IS '监测井的唯一编码';
COMMENT ON COLUMN monitoring_well.longitude IS '监测井所在位置的经度';
COMMENT ON COLUMN monitoring_well.latitude IS '监测井所在位置的纬度';
COMMENT ON COLUMN monitoring_well.is_new_well IS '布尔值，表明该监测井是否为调查评估项目新建井，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.is_area_monitoring_point IS '布尔值，表明该监测井是否为区域监测点，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.area_monitoring_point_type IS '若该监测井是区域监测点，记录其具体类型';
COMMENT ON COLUMN monitoring_well.is_water_source_monitoring_point IS '布尔值，表明该监测井是否为水源监测点，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.water_source_info IS '若该监测井是水源监测点，记录水源代码、水源名称和监测点类型等相关信息';
COMMENT ON COLUMN monitoring_well.is_pollution_source_monitoring_point IS '布尔值，表明该监测井是否为污染源监测点，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.pollution_source_info IS '若该监测井是污染源监测点，记录污染源代码、污染源名称和监测点类型等相关信息';
COMMENT ON COLUMN monitoring_well.completion_time IS '监测井的成井时间';
COMMENT ON COLUMN monitoring_well.water_level_depth IS '监测井的水位埋深（单位：m）';
COMMENT ON COLUMN monitoring_well.wellhead_elevation IS '监测井井口的高程（单位：m）';
COMMENT ON COLUMN monitoring_well.well_depth IS '监测井的成井深度（单位：m）';
COMMENT ON COLUMN monitoring_well.wellhead_inner_diameter IS '监测井井口的内径（单位：mm）';
COMMENT ON COLUMN monitoring_well.well_pipe_material IS '监测井井管的材质';
COMMENT ON COLUMN monitoring_well.is_multiple_screen_pipe IS '布尔值，表明该监测井是否为多段筛管，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.screen_pipe_depth IS '若该监测井有筛管，记录筛管上部和下部的埋深（单位：m）';
COMMENT ON COLUMN monitoring_well.burial_condition IS '地下水的埋藏条件';
COMMENT ON COLUMN monitoring_well.aquifer_medium IS '地下水的含水介质类型';
COMMENT ON COLUMN monitoring_well.well_ownership_unit IS '监测井的权属单位名称';
COMMENT ON COLUMN monitoring_well.is_suitable_for_long_term_monitoring IS '布尔值，表明该监测井是否符合长期监测井要求，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.is_converted_to_long_term_monitoring IS '布尔值，表明该监测井是否已转为长期监测井，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.is_maintenance_management_carried_out IS '布尔值，表明该监测井是否开展了监测井维护管理工作，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.actual_maintenance_management_unit IS '若开展了监测井维护管理工作，记录实际的维护管理单位名称';
COMMENT ON COLUMN monitoring_well.is_sealed_backfilled_for_non_long_term IS '布尔值，对于非长期监测井，表明是否已进行封井回填，是则为 true，否则为 false';
COMMENT ON COLUMN monitoring_well.sealed_backfilled_status IS '若进行了封井回填，记录封井回填的状态';
COMMENT ON COLUMN monitoring_well.geom IS '存储监测井地理位置的几何对象，基于 CGCS2000 坐标系，用于空间查询操作';