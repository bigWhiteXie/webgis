-- 创建地下水样品检测信息表
CREATE TABLE sample_data (
    -- 基本信息
  id SERIAL PRIMARY KEY,
-- 监测井编码，关联对应的监测井
  monitoring_well_code VARCHAR(255) ,
-- 样品编码，唯一标识每个检测样品
  sample_code VARCHAR(255) ,
-- 采样时间，记录样品采集的时间
  sampling_time TIMESTAMP ,
-- 检测实验室，负责进行检测工作的实验室名称
  testing_laboratory VARCHAR(255) ,
-- 检测结果
-- 检测指标代码，对应具体的检测项目
  metric_code VARCHAR(255) ,
-- 检出限，该检测项目能够检测到的最低值
  detection_limit VARCHAR(255) ,
-- 实际检测值，该样品在该检测项目下的实际检测数值
  actual_test_value VARCHAR(255) 
);

-- 添加字段注释
COMMENT ON TABLE sample_data IS '地下水样品检测信息表，记录地下水样品的检测相关信息';
COMMENT ON COLUMN sample_data.id IS '自增主键，唯一标识每条地下水样品检测记录';
COMMENT ON COLUMN sample_data.monitoring_well_code IS '监测井编码，关联对应的监测井';
COMMENT ON COLUMN sample_data.sample_code IS '样品编码，唯一标识每个检测样品';
COMMENT ON COLUMN sample_data.sampling_time IS '采样时间，记录样品采集的时间';
COMMENT ON COLUMN sample_data.testing_laboratory IS '检测实验室，负责进行检测工作的实验室名称';
COMMENT ON COLUMN sample_data.metric_code IS '检测指标代码，对应具体的检测项目';
COMMENT ON COLUMN sample_data.detection_limit IS '检出限，该检测项目能够检测到的最低值';
COMMENT ON COLUMN sample_data.actual_test_value IS '实际检测值，该样品在该检测项目下的实际检测数值';

-- 创建索引
-- 为基本信息中的监测井编码创建索引，方便按监测井查询检测信息
CREATE INDEX idx_monitoring_well_code ON sample_data (monitoring_well_code);
-- 为检测结果中的检测项目代码创建索引，便于按检测项目筛选数据
CREATE INDEX idx_test_item_code ON sample_data (metric_code);
-- 为采样时间创建索引，可按时间范围查询检测信息
CREATE INDEX idx_sampling_time ON sample_data (sampling_time);

CREATE INDEX idx_union_wellcode_time ON sample_data (monitoring_well_code,sampling_time);
