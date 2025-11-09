-- 创建指标标准表
CREATE TABLE metric_standard (
  id SERIAL PRIMARY KEY,
  -- 指标编码
  metric_code VARCHAR(50) NOT NULL,
  -- 质量等级
  quality_level VARCHAR(50) NOT NULL,
  -- 阈值关系类型: between(开区间), between_equal(闭区间), between_up_equal(左开右闭), between_low_equal(左闭右开)
  relation VARCHAR(20) DEFAULT 'between' NOT NULL,
  -- 上界值
  upper_bound DECIMAL,
  -- 下界值
  lower_bound DECIMAL
);

-- 添加字段注释
COMMENT ON TABLE metric_standard IS '指标标准表，存储各指标的不同质量等级对应的阈值';
COMMENT ON COLUMN metric_standard.id IS '自增主键';
COMMENT ON COLUMN metric_standard.metric_code IS '指标编码，关联到pollution_metric表的indicator_code';
COMMENT ON COLUMN metric_standard.quality_level IS '质量等级，如优、良、轻度污染、中度污染、重度污染等';
COMMENT ON COLUMN metric_standard.relation IS '阈值关系类型: between(开区间), between_equal(闭区间), between_up_equal(左开右闭), between_low_equal(左闭右开)';
COMMENT ON COLUMN metric_standard.upper_bound IS '上界值';
COMMENT ON COLUMN metric_standard.lower_bound IS '下界值';