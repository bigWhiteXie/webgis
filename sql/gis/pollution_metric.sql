CREATE TABLE pollution_metric (
 id SERIAL PRIMARY KEY,
-- 指标编码（唯一标识，如G0013、G0026等标准化编码）
 indicator_code VARCHAR(50) ,
-- 指标简称（如铅、镉、COD等简短名称）
 indicator_name VARCHAR(100) ,
-- 指标单位（如mg/L、μg/m³等度量单位）
 unit VARCHAR(50) ,
-- 确保指标编码唯一
 CONSTRAINT uk_indicator_code UNIQUE (indicator_code)
);