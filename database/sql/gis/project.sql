-- 项目表
DROP TABLE IF EXISTS project;
CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,                 -- 自增主键
    project_code VARCHAR(64) NOT NULL UNIQUE, -- 项目编号，唯一
    project_type VARCHAR(64),                 -- 项目类型

    storage_time TIMESTAMP DEFAULT NOW(),     -- 入库时间
    manager VARCHAR(64),                      -- 负责人
    geom GEOMETRY(POLYGON, 4490),             -- 项目边界，使用POLYGON类型
    
    company_code VARCHAR(64) UNIQUE,          -- 企业编号，唯一
    company_name VARCHAR(128) UNIQUE          -- 企业名称，唯一
);

COMMENT ON TABLE project IS '项目表';
COMMENT ON COLUMN project.id IS '自增主键';
COMMENT ON COLUMN project.project_code IS '项目编号';
COMMENT ON COLUMN project.project_type IS '项目类型';
COMMENT ON COLUMN project.storage_time IS '入库时间';
COMMENT ON COLUMN project.manager IS '负责人';
COMMENT ON COLUMN project.geom IS '项目边界';
COMMENT ON COLUMN project.company_code IS '企业编号';
COMMENT ON COLUMN project.company_name IS '企业名称';

CREATE INDEX idx_project_geom ON project USING GIST (geom);