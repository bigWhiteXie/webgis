-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigserial      not null    ,
  parent_id         bigint         default 0                  ,
  ancestors         varchar(50)     default ''                 ,
  dept_name         varchar(30)     default ''                 ,
  order_num         int             default 0                  ,
  leader            varchar(20)     default null               ,
  phone             varchar(11)     default null               ,
  email             varchar(50)     default null               ,
  status            char(1)         default '0'                ,
  del_flag          char(1)         default '0'                ,
  create_by         varchar(64)     default ''                 ,
  create_time 	    timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  primary key (dept_id)
);

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '若依科技',   0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(101,  100, '0,100',      '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(102,  100, '0,100',      '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研发部门',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '市场部门',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '测试部门',   3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '财务部门',   4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '运维部门',   5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '市场部门',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '财务部门',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', now(), '', null);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigserial      not null    ,
  dept_id           bigint         default null               ,
  user_name         varchar(30)     not null                   ,
  nick_name         varchar(30)     not null                   ,
  user_type         varchar(2)      default '00'               ,
  email             varchar(50)     default ''                 ,
  phonenumber       varchar(11)     default ''                 ,
  sex               char(1)         default '0'                ,
  avatar            varchar(100)    default ''                 ,
  password          varchar(100)    default ''                 ,
  status            char(1)         default '0'                ,
  del_flag          char(1)         default '0'                ,
  login_ip          varchar(128)    default ''                 ,
  login_date        timestamp                                   ,
  pwd_update_date   timestamp                                   ,
  create_by         varchar(64)     default ''                 ,
  create_time       timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  remark            varchar(500)    default null               ,
  primary key (user_id)
);

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', now(), now(), 'admin', now(), '', null, '管理员');
insert into sys_user values(2,  105, 'ry',    '若依', '00', 'ry@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', now(), now(), 'admin', now(), '', null, '测试员');


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigserial      not null    ,
  post_code     varchar(64)     not null                   ,
  post_name     varchar(50)     not null                   ,
  post_sort     int             not null                   ,
  status        char(1)         not null                   ,
  create_by     varchar(64)     default ''                 ,
  create_time   timestamp                                   ,
  update_by     varchar(64)     default ''			       ,
  update_time   timestamp                                   ,
  remark        varchar(500)    default null               ,
  primary key (post_id)
);

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '董事长',    1, '0', 'admin', now(), '', null, '');
insert into sys_post values(2, 'se',   '项目经理',  2, '0', 'admin', now(), '', null, '');
insert into sys_post values(3, 'hr',   '人力资源',  3, '0', 'admin', now(), '', null, '');
insert into sys_post values(4, 'user', '普通员工',  4, '0', 'admin', now(), '', null, '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigserial      not null    ,
  role_name            varchar(30)     not null                   ,
  role_key             varchar(100)    not null                   ,
  role_sort            int             not null                   ,
  data_scope           char(1)         default '1'                ,
  menu_check_strictly  smallint        default 1                  ,
  dept_check_strictly  smallint        default 1                  ,
  status               char(1)         not null                   ,
  del_flag             char(1)         default '0'                ,
  create_by            varchar(64)     default ''                 ,
  create_time          timestamp                                   ,
  update_by            varchar(64)     default ''                 ,
  update_time          timestamp                                   ,
  remark               varchar(500)    default null               ,
  primary key (role_id)
);

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values(1, '超级管理员',  'admin',  1, '1', 1, 1, '0', '0', 'admin', now(), '', null, '超级管理员');
insert into sys_role values(2, '普通角色',    'common', 2, '2', 1, 1, '0', '0', 'admin', now(), '', null, '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigserial      not null    ,
  menu_name         varchar(50)     not null                   ,
  parent_id         bigint          default 0                  ,
  order_num         int             default 0                  ,
  path              varchar(200)    default ''                 ,
  component         varchar(255)    default null               ,
  query             varchar(255)    default null               ,
  route_name        varchar(50)     default ''                 ,
  is_frame          int             default 1                  ,
  is_cache          int             default 0                  ,
  menu_type         char(1)         default ''                 ,
  visible           char(1)         default '0'                ,
  status            char(1)         default '0'                ,
  perms             varchar(100)    default null               ,
  icon              varchar(100)    default '#'                ,
  create_by         varchar(64)     default ''                 ,
  create_time       timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  remark            varchar(500)    default ''                 ,
  primary key (menu_id)
);

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values(1, '系统管理', 0, 1, 'system', null, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', now(), '', null, '系统管理目录');
insert into sys_menu values(2, '系统监控', 0, 2, 'monitor', null, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', now(), '', null, '系统监控目录');
insert into sys_menu values(3, '系统工具', 0, 3, 'tool', null, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', now(), '', null, '系统工具目录');
insert into sys_menu values(4, '若依官网', 0, 4, 'http://ruoyi.vip', null, '', '', 0, 0, 'M', '0', '0', '', 'guide', 'admin', now(), '', null, '若依官网地址');
-- 二级菜单
insert into sys_menu values(100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', now(), '', null, '用户管理菜单');
insert into sys_menu values(101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', now(), '', null, '角色管理菜单');
insert into sys_menu values(102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', now(), '', null, '菜单管理菜单');
insert into sys_menu values(103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', now(), '', null, '部门管理菜单');
insert into sys_menu values(104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', now(), '', null, '岗位管理菜单');
insert into sys_menu values(105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', now(), '', null, '字典管理菜单');
insert into sys_menu values(106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', now(), '', null, '参数设置菜单');
insert into sys_menu values(107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', now(), '', null, '通知公告菜单');
insert into sys_menu values(108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', now(), '', null, '日志管理菜单');
insert into sys_menu values(109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', now(), '', null, '在线用户菜单');
insert into sys_menu values(110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', now(), '', null, '定时任务菜单');
insert into sys_menu values(111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', now(), '', null, '数据监控菜单');
insert into sys_menu values(112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', now(), '', null, '服务监控菜单');
insert into sys_menu values(113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', now(), '', null, '缓存监控菜单');
insert into sys_menu values(114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', now(), '', null, '缓存列表菜单');
insert into sys_menu values(115, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', now(), '', null, '表单构建菜单');
insert into sys_menu values(116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', now(), '', null, '代码生成菜单');
insert into sys_menu values(117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', now(), '', null, '系统接口菜单');
-- 三级菜单
insert into sys_menu values(500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', now(), '', null, '操作日志菜单');
insert into sys_menu values(501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', now(), '', null, '登录日志菜单');
-- 用户管理按钮
insert into sys_menu values(1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', now(), '', null, '');
-- 角色管理按钮
insert into sys_menu values(1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', now(), '', null, '');
-- 菜单管理按钮
insert into sys_menu values(1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', now(), '', null, '');
-- 部门管理按钮
insert into sys_menu values(1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', now(), '', null, '');
-- 岗位管理按钮
insert into sys_menu values(1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', now(), '', null, '');
-- 字典管理按钮
insert into sys_menu values(1025, '字典查询', 105, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1026, '字典新增', 105, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1027, '字典修改', 105, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1028, '字典删除', 105, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(01029, '字典导出', 105, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', now(), '', null, '');
-- 参数设置按钮
insert into sys_menu values(1030, '参数查询', 106, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1031, '参数新增', 106, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1032, '参数修改', 106, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1033, '参数删除', 106, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1034, '参数导出', 106, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', now(), '', null, '');
-- 通知公告按钮
insert into sys_menu values(1035, '公告查询', 107, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1036, '公告新增', 107, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1037, '公告修改', 107, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1038, '公告删除', 107, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', now(), '', null, '');
-- 操作日志按钮
insert into sys_menu values(1039, '操作查询', 500, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1040, '操作删除', 500, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1041, '日志导出', 500, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', now(), '', null, '');
-- 登录日志按钮
insert into sys_menu values(1042, '登录查询', 501, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1043, '登录删除', 501, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1044, '日志导出', 501, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1045, '账户解锁', 501, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', now(), '', null, '');
-- 在线用户按钮
insert into sys_menu values(1046, '在线查询', 109, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1047, '批量强退', 109, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1048, '单条强退', 109, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', now(), '', null, '');
-- 定时任务按钮
insert into sys_menu values(1049, '任务查询', 110, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1050, '任务新增', 110, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1051, '任务修改', 110, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1052, '任务删除', 110, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1053, '状态修改', 110, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1054, '任务导出', 110, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', now(), '', null, '');
-- 代码生成按钮
insert into sys_menu values(1055, '生成查询', 116, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1056, '生成修改', 116, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1057, '生成删除', 116, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1058, '导入代码', 116, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1059, '预览代码', 116, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', now(), '', null, '');
insert into sys_menu values(1060, '生成代码', 116, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', now(), '', null, '');


-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint not null,
  role_id   bigint not null,
  primary key(user_id, role_id)
);

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values (1, 1);
insert into sys_user_role values (2, 2);


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint not null,
  menu_id   bigint not null,
  primary key(role_id, menu_id)
);

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
-- 省略初始化数据，因为数据量较大


-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint not null,
  dept_id   bigint not null,
  primary key(role_id, dept_id)
);

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values (2, 100);
insert into sys_role_dept values (2, 101);
insert into sys_role_dept values (2, 105);


-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint not null,
  post_id   bigint not null,
  primary key (user_id, post_id)
);

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values (1, 1);
insert into sys_user_post values (2, 2);


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigserial      not null    ,
  title             varchar(50)     default ''                 ,
  business_type     int             default 0                  ,
  method            varchar(200)    default ''                 ,
  request_method    varchar(10)     default ''                 ,
  operator_type     int             default 0                  ,
  oper_name         varchar(50)     default ''                 ,
  dept_name         varchar(50)     default ''                 ,
  oper_url          varchar(255)    default ''                 ,
  oper_ip           varchar(128)    default ''                 ,
  oper_location     varchar(255)    default ''                 ,
  oper_param        varchar(2000)   default ''                 ,
  json_result       varchar(2000)   default ''                 ,
  status            int             default 0                  ,
  error_msg         varchar(2000)   default ''                 ,
  oper_time         timestamp                                   ,
  cost_time         bigint          default 0                  ,
  primary key (oper_id)
);


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigserial      not null    ,
  dict_name        varchar(100)    default ''                 ,
  dict_type        varchar(100)    default ''                 ,
  status           char(1)         default '0'                ,
  create_by        varchar(64)     default ''                 ,
  create_time      timestamp                                   ,
  update_by        varchar(64)     default ''                 ,
  update_time      timestamp                                   ,
  remark           varchar(500)    default null               ,
  primary key (dict_id),
  unique (dict_type)
);

insert into sys_dict_type values(1, '用户性别', 'sys_user_sex', '0', 'admin', now(), '', null, '用户性别列表');
insert into sys_dict_type values(2, '菜单状态', 'sys_show_hide', '0', 'admin', now(), '', null, '菜单状态列表');
insert into sys_dict_type values(3, '系统开关', 'sys_normal_disable', '0', 'admin', now(), '', null, '系统开关列表');
insert into sys_dict_type values(4, '任务状态', 'sys_job_status', '0', 'admin', now(), '', null, '任务状态列表');
insert into sys_dict_type values(5, '任务分组', 'sys_job_group', '0', 'admin', now(), '', null, '任务分组列表');
insert into sys_dict_type values(6, '系统是否', 'sys_yes_no', '0', 'admin', now(), '', null, '系统是否列表');
insert into sys_dict_type values(7, '通知类型', 'sys_notice_type', '0', 'admin', now(), '', null, '通知类型列表');
insert into sys_dict_type values(8, '通知状态', 'sys_notice_status', '0', 'admin', now(), '', null, '通知状态列表');
insert into sys_dict_type values(9, '操作类型', 'sys_oper_type', '0', 'admin', now(), '', null, '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status', '0', 'admin', now(), '', null, '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigserial      not null    ,
  dict_sort        int             default 0                  ,
  dict_label       varchar(100)    default ''                 ,
  dict_value       varchar(100)    default ''                 ,
  dict_type        varchar(100)    default ''                 ,
  css_class        varchar(100)    default null               ,
  list_class       varchar(100)    default null               ,
  is_default       char(1)         default 'N'                ,
  status           char(1)         default '0'                ,
  create_by        varchar(64)     default ''                 ,
  create_time      timestamp                                   ,
  update_by        varchar(64)     default ''                 ,
  update_time      timestamp                                   ,
  remark           varchar(500)    default null               ,
  primary key (dict_code)
);

-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         serial         not null    ,
  config_name       varchar(100)    default ''                 ,
  config_key        varchar(100)    default ''                 ,
  config_value      varchar(500)    default ''                 ,
  config_type       char(1)         default 'N'                ,
  create_by         varchar(64)     default ''                 ,
  create_time       timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  remark            varchar(500)    default null               ,
  primary key (config_id)
);

-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigserial     not null   ,
  user_name      varchar(50)    default ''                ,
  ipaddr         varchar(128)   default ''                ,
  login_location varchar(255)   default ''                ,
  browser        varchar(50)    default ''                ,
  os             varchar(50)    default ''                ,
  status         char(1)        default '0'               ,
  msg            varchar(255)   default ''                ,
  login_time     timestamp                                 ,
  primary key (info_id)
);

-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigserial    not null    ,
  job_name            varchar(64)   default ''                 ,
  job_group           varchar(64)   default 'DEFAULT'          ,
  invoke_target       varchar(500)  not null                   ,
  cron_expression     varchar(255)  default ''                 ,
  misfire_policy      varchar(20)   default '3'                ,
  concurrent          char(1)       default '1'                ,
  status              char(1)       default '0'                ,
  create_by           varchar(64)   default ''                 ,
  create_time         timestamp                                 ,
  update_by           varchar(64)   default ''                 ,
  update_time         timestamp                                 ,
  remark              varchar(500)  default ''                 ,
  primary key (job_id, job_name, job_group)
);

-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigserial     not null    ,
  job_name            varchar(64)    not null                   ,
  job_group           varchar(64)    not null                   ,
  invoke_target       varchar(500)   not null                   ,
  job_message         varchar(500)                              ,
  status              char(1)        default '0'                ,
  exception_info      varchar(2000)  default ''                 ,
  create_time         timestamp                                  ,
  primary key (job_log_id)
);

-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         serial          not null    ,
  notice_title      varchar(50)     not null                   ,
  notice_type       char(1)         not null                   ,
  notice_content    text            default null               ,
  status            char(1)         default '0'                ,
  create_by         varchar(64)     default ''                 ,
  create_time       timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  remark            varchar(255)    default null               ,
  primary key (notice_id)
);

-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigserial      not null    ,
  table_name        varchar(200)    default ''                 ,
  table_comment     varchar(500)    default ''                 ,
  sub_table_name    varchar(64)     default null               ,
  sub_table_fk_name varchar(64)     default null               ,
  class_name        varchar(100)    default ''                 ,
  tpl_category      varchar(200)    default 'crud'             ,
  tpl_web_type      varchar(30)     default ''                 ,
  package_name      varchar(100)                               ,
  module_name       varchar(30)                                ,
  business_name     varchar(30)                                ,
  function_name     varchar(50)                                ,
  function_author   varchar(50)                                ,
  gen_type          char(1)         default '0'                ,
  gen_path          varchar(200)    default '/'                ,
  options           varchar(1000)                              ,
  create_by         varchar(64)     default ''                 ,
  create_time 	    timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  remark            varchar(500)    default null               ,
  primary key (table_id)
);

-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigserial      not null    ,
  table_id          bigint                                     ,
  column_name       varchar(200)                               ,
  column_comment    varchar(500)                               ,
  column_type       varchar(100)                               ,
  java_type         varchar(500)                               ,
  java_field        varchar(200)                               ,
  is_pk             char(1)                                    ,
  is_increment      char(1)                                    ,
  is_required       char(1)                                    ,
  is_insert         char(1)                                    ,
  is_edit           char(1)                                    ,
  is_list           char(1)                                    ,
  is_query          char(1)                                    ,
  query_type        varchar(200)    default 'EQ'               ,
  html_type         varchar(200)                               ,
  dict_type         varchar(200)    default ''                 ,
  sort              int                                        ,
  create_by         varchar(64)     default ''                 ,
  create_time 	    timestamp                                   ,
  update_by         varchar(64)     default ''                 ,
  update_time       timestamp                                   ,
  primary key (column_id)
);