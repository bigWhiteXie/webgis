-- 创建行政区划信息表
CREATE TABLE location (
 id SERIAL PRIMARY KEY,
 province_code VARCHAR(20) ,
 province_name VARCHAR(100) ,
 city_code VARCHAR(20) ,
 city_name VARCHAR(100) ,
 county_code VARCHAR(20) ,
 county_name VARCHAR(100) ,
-- 添加唯一约束，确保每个区县编码唯一
 CONSTRAINT uk_county_code UNIQUE (county_code)
);

-- 添加字段注释
COMMENT ON TABLE location IS '行政区划信息表，存储省、市、区县三级行政区划编码及名称';
COMMENT ON COLUMN location.id IS '自增主键，唯一标识一条行政区划记录';
COMMENT ON COLUMN location.province_code IS '省份编码，如150000代表内蒙古自治区';
COMMENT ON COLUMN location.province_name IS '省份名称，如内蒙古自治区';
COMMENT ON COLUMN location.city_code IS '地市编码，如152500代表锡林郭勒盟';
COMMENT ON COLUMN location.city_name IS '地市名称，如锡林郭勒盟';
COMMENT ON COLUMN location.county_code IS '区县编码，如152501代表二连浩特市';
COMMENT ON COLUMN location.county_name IS '区县名称，如二连浩特市';

-- 创建索引以提高查询性能
-- 按省份查询的索引
CREATE INDEX idx_province_code ON location (province_code);
-- 按地市查询的索引
CREATE INDEX idx_city_code ON location (city_code);
-- 按区县查询的索引
CREATE unique INDEX unique_county_code ON location (county_code);
-- 按省市联合查询的索引
CREATE INDEX idx_province_city ON location (province_code, city_code, county_code);

select * from location where county_code = '152531.0';

UPDATE location
SET county_code = LEFT(county_code, LENGTH(county_code) - 2)
WHERE county_code LIKE '%.0';