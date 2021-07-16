DROP TABLE if exists public.summer_platform_db_resource;
CREATE TABLE public.summer_platform_db_resource (
	id serial,
	source_name varchar(50) NULL DEFAULT NULL::character varying,
	url varchar(255) NULL DEFAULT NULL::character varying,
	username varchar(50) NULL DEFAULT NULL::character varying,
	"password" varchar(50) NULL DEFAULT NULL::character varying,
	db_name varchar(50) NULL DEFAULT NULL::character varying,
	"schema" varchar(20) NULL DEFAULT 'public'::character varying,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_platform_db_resource_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_platform_db_resource IS '数据库资源表';
COMMENT ON COLUMN public.summer_platform_db_resource.source_name IS '数据源名称';
COMMENT ON COLUMN public.summer_platform_db_resource.url IS '数据库连接';
COMMENT ON COLUMN public.summer_platform_db_resource.username IS '数据库用户名';
COMMENT ON COLUMN public.summer_platform_db_resource.password IS '数据库密码';
COMMENT ON COLUMN public.summer_platform_db_resource.db_name IS '数据库名称';

DROP TABLE if exists public.summer_platform_tenant;
CREATE TABLE public.summer_platform_tenant (
	id serial,
	"name" varchar(100) NULL DEFAULT NULL::character varying,
	db_resource_id int4 NULL,
	brand_name varchar(100) NULL DEFAULT NULL::character varying,
	company_name varchar(100) NULL DEFAULT NULL::character varying,
	company_tel varchar(50) NULL DEFAULT NULL::character varying,
	company_email varchar(100) NULL DEFAULT NULL::character varying,
	company_address varchar(255) NULL DEFAULT NULL::character varying,
	status varchar(20) NULL DEFAULT NULL::character varying,
	logo varchar(255) NULL DEFAULT NULL::character varying,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_platform_tenant_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_platform_tenant IS '平台租户信息表';
COMMENT ON COLUMN public.summer_platform_tenant.name IS '租户名称';
COMMENT ON COLUMN public.summer_platform_tenant.db_resource_id IS '数据库资源id';
COMMENT ON COLUMN public.summer_platform_tenant.brand_name IS '品牌名称';
COMMENT ON COLUMN public.summer_platform_tenant.company_name IS '企业名称';
COMMENT ON COLUMN public.summer_platform_tenant.company_tel IS '企业电话';
COMMENT ON COLUMN public.summer_platform_tenant.company_email IS '企业邮箱';
COMMENT ON COLUMN public.summer_platform_tenant.company_address IS '企业地址';
COMMENT ON COLUMN public.summer_platform_tenant.status IS '状态';
COMMENT ON COLUMN public.summer_platform_tenant.logo IS '图标';


DROP TABLE if exists public.summer_platform_user;
CREATE TABLE public.summer_platform_user (
	id serial NOT NULL,
	username varchar(50) NULL DEFAULT NULL::character varying,
	"password" varchar(255) NULL DEFAULT NULL::character varying,
	last_login_time varchar(30) NULL DEFAULT NULL::character varying,
	login_error_count int4 NULL,
	tenant_id int4 NULL,
	user_type varchar(255) NULL DEFAULT NULL::character varying,
	status varchar(2) NULL DEFAULT NULL::character varying,
	profile_photo varchar(50) NULL,
	nickname varchar(50) NULL,
	birthday timestamp NULL,
	address varchar(100) NULL,
	mobile varchar(20) NULL,
	email varchar(50) NULL,
	gender varchar(10) NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_platform_user_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_platform_user IS '平台用户，用于登录系统';
COMMENT ON COLUMN public.summer_platform_user.username IS '用户名';
COMMENT ON COLUMN public.summer_platform_user.password IS '密码';
COMMENT ON COLUMN public.summer_platform_user.last_login_time IS '上次登录时间';
COMMENT ON COLUMN public.summer_platform_user.login_error_count IS '登录错误次数';
COMMENT ON COLUMN public.summer_platform_user.tenant_id IS '拥有的租户';
COMMENT ON COLUMN public.summer_platform_user.user_type IS '用户类型';
COMMENT ON COLUMN public.summer_platform_user.status IS '状态';
COMMENT ON COLUMN public.summer_platform_user.profile_photo IS '用户头像';
COMMENT ON COLUMN public.summer_platform_user.nickname IS '用户昵称';
COMMENT ON COLUMN public.summer_platform_user.birthday IS '生日';
COMMENT ON COLUMN public.summer_platform_user.address IS '地址';
COMMENT ON COLUMN public.summer_platform_user.mobile IS '手机号';
COMMENT ON COLUMN public.summer_platform_user.email IS '邮箱';
COMMENT ON COLUMN public.summer_platform_user.gender IS '性别';


DROP TABLE if exists public.summer_sys_data_auth;
CREATE TABLE public.summer_sys_data_auth (
	id serial,
	role_id int4 NULL,
	menu_id int4 NULL,
	data_auth_level int4 NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_data_auth_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_data_auth IS '数据权限表';



DROP TABLE if exists public.summer_sys_department;
CREATE TABLE public.summer_sys_department (
	id serial,
	"name" varchar(255) NULL,
	p_id int4 NULL DEFAULT 0,
	"type" int4 NULL,
	address varchar(300) NULL,
	status varchar(10) NULL DEFAULT 0,
	telephone varchar(255) NULL DEFAULT ''::character varying,
	dept_no varchar(255) NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_department_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_department IS '组织部门表';
COMMENT ON COLUMN public.summer_sys_department.name IS '部门名称';
COMMENT ON COLUMN public.summer_sys_department.p_id IS '父部门id';
COMMENT ON COLUMN public.summer_sys_department.type IS '部门类型';
COMMENT ON COLUMN public.summer_sys_department.address IS '地址';
COMMENT ON COLUMN public.summer_sys_department.status IS '状态';
COMMENT ON COLUMN public.summer_sys_department.telephone IS '部门手机号';
COMMENT ON COLUMN public.summer_sys_department.dept_no IS '部门编号';

DROP TABLE if exists public.summer_sys_dict;
CREATE TABLE public.summer_sys_dict (
	id serial,
	value varchar(255) NULL DEFAULT NULL::character varying,
	"text" varchar(255) NULL DEFAULT NULL::character varying,
	"module" varchar(255) NULL DEFAULT NULL::character varying,
	sort int8 NULL,
	remark varchar(255) NULL DEFAULT NULL::character varying,
	keyword varchar(20) NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_dict_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_dict IS '字典码表';
COMMENT ON COLUMN public.summer_sys_dict.value IS '字典key';
COMMENT ON COLUMN public.summer_sys_dict.text IS '字典值';
COMMENT ON COLUMN public.summer_sys_dict.module IS '所属模块';
COMMENT ON COLUMN public.summer_sys_dict.sort IS '排序';
COMMENT ON COLUMN public.summer_sys_dict.remark IS '字典描述';
COMMENT ON COLUMN public.summer_sys_dict.keyword IS '查询关键字';


DROP TABLE if exists public.summer_sys_menu;
CREATE TABLE public.summer_sys_menu (
	id serial,
	code varchar(255) NULL DEFAULT NULL::character varying,
	"name" varchar(255) NULL DEFAULT NULL::character varying,
	p_id int4 NULL DEFAULT 0,
	href varchar(255) NULL DEFAULT NULL::character varying,
	icon varchar(255) NULL DEFAULT NULL::character varying,
	"order" int4 NULL DEFAULT 1,
	remark varchar(255) NULL DEFAULT NULL::character varying,
	"type" int4 NULL,
	"level" int4 NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_menu_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_menu IS '系统菜单表';
COMMENT ON COLUMN public.summer_sys_menu.code IS '菜单编码';
COMMENT ON COLUMN public.summer_sys_menu.name IS '菜单名称';
COMMENT ON COLUMN public.summer_sys_menu.p_id IS '父id';
COMMENT ON COLUMN public.summer_sys_menu.href IS '路由';
COMMENT ON COLUMN public.summer_sys_menu.icon IS '图标';
COMMENT ON COLUMN public.summer_sys_menu."order" IS '排序';
COMMENT ON COLUMN public.summer_sys_menu.remark IS '描述';
COMMENT ON COLUMN public.summer_sys_menu.type IS '类型';
COMMENT ON COLUMN public.summer_sys_menu.level IS '层级';


DROP TABLE if exists public.summer_sys_operation_log;
CREATE TABLE public.summer_sys_operation_log (
	id serial,
	"module" varchar(50) NULL,
	"function" varchar NULL,
	url varchar(50) NULL,
	params varchar(255) NULL,
	"result" varchar(255) NULL,
	"cost" varchar(10) NULL,
	"ip_addr" varchar(50) NULL,
	"location" varchar(50) NULL,
	"creator" varchar(50) NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_operation_log_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_operation_log IS '系统操作日志表';
COMMENT ON COLUMN public.summer_sys_operation_log.module IS '操作模块';
COMMENT ON COLUMN public.summer_sys_operation_log.function IS '功能';
COMMENT ON COLUMN public.summer_sys_operation_log.url IS '操作接口';
COMMENT ON COLUMN public.summer_sys_operation_log.params IS '操作内容';
COMMENT ON COLUMN public.summer_sys_operation_log.result IS '操作结果';
COMMENT ON COLUMN public.summer_sys_operation_log.cost IS '操作耗时';
COMMENT ON COLUMN public.summer_sys_operation_log.ip_addr IS '操作人ip';
COMMENT ON COLUMN public.summer_sys_operation_log.location IS '操作人位置';
COMMENT ON COLUMN public.summer_sys_operation_log.creator IS '操作人名称';
COMMENT ON COLUMN public.summer_sys_operation_log.creator_id IS '操作人';
COMMENT ON COLUMN public.summer_sys_operation_log.create_time IS '操作时间';


DROP TABLE if exists public.summer_sys_role;
CREATE TABLE public.summer_sys_role (
	id serial,
	"name" varchar(255) NULL,
	status bpchar(1) NULL,
	remark varchar(255) NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_role_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_role IS '系统角色表';
COMMENT ON COLUMN public.summer_sys_role.name IS '名称';
COMMENT ON COLUMN public.summer_sys_role.status IS '状态';
COMMENT ON COLUMN public.summer_sys_role.remark IS '描述';


DROP TABLE if exists public.summer_sys_role_menu;
CREATE TABLE public.summer_sys_role_menu (
	id serial,
	role_id int4 NULL,
	menu_id int4 NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_role_menu_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_role_menu IS '角色菜单关系表';
COMMENT ON COLUMN public.summer_sys_role_menu.role_id IS '角色id';
COMMENT ON COLUMN public.summer_sys_role_menu.menu_id IS '菜单id';


DROP TABLE if exists public.summer_sys_user_department;
CREATE TABLE public.summer_sys_user_department (
	id serial,
	department_id int8 NULL,
	user_id int8 NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_user_department_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_user_department IS '用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。';
COMMENT ON COLUMN public.summer_sys_user_department.department_id IS '部门id';
COMMENT ON COLUMN public.summer_sys_user_department.user_id IS '登录用户id';
COMMENT ON COLUMN public.summer_sys_user_department.version IS '乐观锁版本控制';

DROP TABLE if exists public.summer_sys_user_role;
CREATE TABLE public.summer_sys_user_role (
	id serial,
	user_id int4 NULL,
	role_id int4 NULL,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_sys_user_role_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.summer_sys_user_role IS '系统级别用户角色关联表';
COMMENT ON COLUMN public.summer_sys_user_role.user_id IS '登录用户id';
COMMENT ON COLUMN public.summer_sys_user_role.role_id IS '角色id';

DROP TABLE if exists public.summer_platform_tenant_datasource;

CREATE TABLE public.summer_platform_tenant_datasource (
	id serial NOT NULL,
	datasource_id int4 NULL,
	tenant_id int4 NULL,
	"tenant_name" varchar(20) NULL DEFAULT 'public'::character varying,
	"service" varchar(20) NULL DEFAULT 'public'::character varying,
	"version" int4 NULL DEFAULT 0,
	is_del int2 NULL DEFAULT 0,
	creator_id int4 NULL,
	updater_id int4 NULL,
	create_time timestamp NULL,
	update_time timestamp NULL,
	CONSTRAINT summer_platform_tenant_datasource_pk PRIMARY KEY (id)
);
