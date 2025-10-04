-- 创建指标标准表
CREATE TABLE metric_standard (
  id SERIAL PRIMARY KEY,
  -- 指标编码
  metric_code VARCHAR(50) NOT NULL,
  -- 阈值
  threshold_value DECIMAL NOT NULL,
  -- 质量等级
  quality_level VARCHAR(50) NOT NULL,
  -- 指标编码和质量等级联合唯一索引
  CONSTRAINT uk_indicator_code_quality_level UNIQUE (metric_code, quality_level)
);

-- 添加字段注释
COMMENT ON TABLE metric_standard IS '指标标准表，存储各指标的不同质量等级对应的阈值';
COMMENT ON COLUMN metric_standard.id IS '自增主键';
COMMENT ON COLUMN metric_standard.metric_code IS '指标编码，关联到pollution_metric表的indicator_code';
COMMENT ON COLUMN metric_standard.threshold_value IS '阈值，用于判断质量等级的下界临界值';
COMMENT ON COLUMN metric_standard.quality_level IS '质量等级，如优、良、轻度污染、中度污染、重度污染等';