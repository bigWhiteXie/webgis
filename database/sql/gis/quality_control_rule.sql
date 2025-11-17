-- 创建质控规则表
CREATE TABLE quality_control_rule (
  id SERIAL PRIMARY KEY,
  -- 自增主键
  metric_code VARCHAR(50) unique ,
  -- 指标编码
  metric_name VARCHAR(255) NOT NULL,
  -- 指标名称
  upper_bound DECIMAL,
  -- 上界值
  lower_bound DECIMAL
  -- 下界值
);

-- 添加字段注释
COMMENT ON TABLE quality_control_rule IS '质控规则表，用于存储各项指标的质控规则';
COMMENT ON COLUMN quality_control_rule.id IS '自增主键';
COMMENT ON COLUMN quality_control_rule.metric_code IS '指标编码';
COMMENT ON COLUMN quality_control_rule.metric_name IS '指标名称';
COMMENT ON COLUMN quality_control_rule.upper_bound IS '上界值';
COMMENT ON COLUMN quality_control_rule.lower_bound IS '下界值';