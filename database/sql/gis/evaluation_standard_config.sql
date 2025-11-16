-- 创建评价标准配置表
CREATE TABLE evaluation_standard_config (
  id SERIAL PRIMARY KEY,
  -- 自增主键
  reference_standard_id Integer not null ,
  -- 参考标准(字符串)
  metric_code VARCHAR(50),
  -- 监测指标编码
  metric_name VARCHAR(255),
  -- 监测指标名称
  class_i_range VARCHAR(255),
  -- I类范围
  class_ii_range VARCHAR(255),
  -- II类范围
  class_iii_range VARCHAR(255),
  -- III类范围
  class_iv_range VARCHAR(255),
  -- IV类范围
  class_v_range VARCHAR(255),
  -- V类范围
  class_inferior_v_range VARCHAR(255),
  -- 劣V类范围
  status INTEGER default 0,
  -- 状态（0-停用，1-启用）
  import_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  -- 导入时间
);
CREATE UNIQUE INDEX uk_refernce_metric ON evaluation_standard_config (reference_standard_id, metric_code);

-- 添加字段注释
COMMENT ON TABLE evaluation_standard_config IS '评价标准配置表，用于存储各项指标不同类别范围的标准配置';
COMMENT ON COLUMN evaluation_standard_config.id IS '自增主键';
COMMENT ON COLUMN evaluation_standard_config.reference_standard_id IS '参考标准ID';
COMMENT ON COLUMN evaluation_standard_config.metric_code IS '监测指标编码';
COMMENT ON COLUMN evaluation_standard_config.metric_name IS '监测指标名称';
COMMENT ON COLUMN evaluation_standard_config.class_i_range IS 'I类范围';
COMMENT ON COLUMN evaluation_standard_config.class_ii_range IS 'II类范围';
COMMENT ON COLUMN evaluation_standard_config.class_iii_range IS 'III类范围';
COMMENT ON COLUMN evaluation_standard_config.class_iv_range IS 'IV类范围';
COMMENT ON COLUMN evaluation_standard_config.class_v_range IS 'V类范围';
COMMENT ON COLUMN evaluation_standard_config.class_inferior_v_range IS '劣V类范围';
COMMENT ON COLUMN evaluation_standard_config.status IS '状态（0-正常，1-停用）';
COMMENT ON COLUMN evaluation_standard_config.import_time IS '导入时间';