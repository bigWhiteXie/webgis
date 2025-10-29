CREATE TABLE groundwater_pollution_source (
-- 基本信息
  source_id SERIAL PRIMARY KEY,
-- 所属项目编号
  project_id VARCHAR(255) ,
-- 省（区、市）代码
  province_code VARCHAR(255) ,
-- 地市代码
  city_code VARCHAR(255) ,
-- 区县代码
  county_code VARCHAR(255) ,
-- 污染源名称
  source_name VARCHAR(255) ,
-- 污染源类型
  source_type VARCHAR(255) ,
-- 污染源编码
  source_code VARCHAR(255) ,
-- 中心经度，用于地理位置定位
  center_longitude DECIMAL(9, 6) ,
-- 中心纬度，用于地理位置定位
  center_latitude DECIMAL(9, 6) ,
-- 生产（运行）状态，如在产、停产等
  production_status VARCHAR(255) ,
-- 投产（起始运行）年份
  start_year INT,
-- 关闭年份
  close_year INT,
-- 水质信息
-- 对标水质类别
  reference_water_quality_category INT,
-- 实际水质类别
  actual_water_quality_category INT,
-- 特征指标代码
  characteristic_index_code VARCHAR(255),
-- 周边敏感受体信息
-- 周边1km是否存在敏感受体，布尔值
  has_sensitive_receptor BOOLEAN ,
-- 集中式地下水型饮用水水源数量
  groundwater_drinking_water_source_count INT,
-- 集中式地表水型饮用水水源数量
  surface_water_drinking_water_source_count INT,
-- 农村分散式水源数量
  rural_scattered_water_source_count INT,
-- 灌溉水井数量
  irrigation_well_count INT,
-- 自然保护区数量
  nature_reserve_count INT,
-- 地表水体数量
  surface_water_body_count INT,
-- 国家地下水环境质量考核点数量
  national_groundwater_quality_checkpoint_count INT,
-- 环境管理信息
-- 是否土壤污染重点监管单位，布尔值
  is_soil_pollution_key_unit BOOLEAN ,
-- 是否地下水污染防治重点排污单位，布尔值
  is_groundwater_pollution_key_unit BOOLEAN ,
-- 位于不同地下水脆弱性等级
  groundwater_vulnerability_level VARCHAR(255) ,
-- 是否开展渗漏排查，布尔值
  is_leakage_inspection BOOLEAN ,
-- 是否已采取防渗漏措施
  leakage_prevention VARCHAR(255) ,
-- 是否开展地下水监测工作，布尔值
  is_groundwater_monitoring BOOLEAN ,
-- 地下水监测情况
  groundwater_monitoring_situation VARCHAR(255),
-- 污染源地下水环境管理分类
  management_classification VARCHAR(255) ,
-- 是否列入高风险污染源，布尔值
  is_high_risk_source BOOLEAN ,
-- 已采取的解决措施
  implemented_solutions VARCHAR(255),
-- 污染趋势
  pollution_trend VARCHAR(255),
-- 拟采取的解决措施
  planned_solutions VARCHAR(255),
-- 预计实现污染不加重、不扩散的时间
  expected_control_time DATE,
-- 空间数据，用于支持空间查询，采用 CGCS2000 坐标系（SRID 为 4490）
  geom geometry(Point, 4490)
);

-- 创建索引
-- 为基本信息中的常用查询字段创建索引
CREATE INDEX idx_project_id ON groundwater_pollution_source (project_id);
CREATE INDEX idx_province_code ON groundwater_pollution_source (province_code);
CREATE INDEX idx_city_code ON groundwater_pollution_source (city_code);
CREATE INDEX idx_county_code ON groundwater_pollution_source (county_code);
CREATE INDEX idx_source_name ON groundwater_pollution_source (source_name);
CREATE INDEX idx_source_type ON groundwater_pollution_source (source_type);

-- 为空间查询创建空间索引
CREATE INDEX idx_geom ON groundwater_pollution_source USING GIST (geom);

-- 为水质信息中的类别字段创建索引
CREATE INDEX idx_reference_water_quality_category ON groundwater_pollution_source (reference_water_quality_category);
CREATE INDEX idx_actual_water_quality_category ON groundwater_pollution_source (actual_water_quality_category);

-- 为环境管理信息中的常用查询字段创建索引
CREATE INDEX idx_is_soil_pollution_key_unit ON groundwater_pollution_source (is_soil_pollution_key_unit);
CREATE INDEX idx_is_groundwater_pollution_key_unit ON groundwater_pollution_source (is_groundwater_pollution_key_unit);
CREATE INDEX idx_is_high_risk_source ON groundwater_pollution_source (is_high_risk_source);

