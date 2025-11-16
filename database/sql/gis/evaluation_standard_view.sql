-- 创建评价标准查看表
CREATE TABLE evaluation_standard_view (
  id SERIAL PRIMARY KEY,
  -- 自增主键
  standard_name VARCHAR(255) NOT NULL,
  -- 标准名称
  file_path VARCHAR(500),
  -- 文件路径
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  -- 创建时间
);

-- 添加字段注释
COMMENT ON TABLE evaluation_standard_view IS '评价标准查看表，用于存储评价标准的基本信息';
COMMENT ON COLUMN evaluation_standard_view.id IS '自增主键';
COMMENT ON COLUMN evaluation_standard_view.standard_name IS '标准名称';
COMMENT ON COLUMN evaluation_standard_view.file_path IS '文件路径';
COMMENT ON COLUMN evaluation_standard_view.create_time IS '创建时间';