--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: summer_sys_data_auth; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_data_auth (
                                             id integer NOT NULL,
                                             role_id integer,
                                             menu_id integer,
                                             data_auth_level integer,
                                             create_user_id integer,
                                             create_time timestamp without time zone
);


ALTER TABLE public.summer_sys_data_auth OWNER TO postgres;

--
-- Name: TABLE summer_sys_data_auth; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_data_auth IS '数据权限表';


--
-- Name: crm_sys_data_auth_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_data_auth_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_data_auth_id_seq OWNER TO postgres;

--
-- Name: crm_sys_data_auth_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_data_auth_id_seq OWNED BY public.summer_sys_data_auth.id;


--
-- Name: summer_sys_department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_department (
                                              id integer NOT NULL,
                                              name character varying(255),
                                              p_id integer DEFAULT 0,
                                              type integer,
                                              address character varying(300),
                                              status character varying(10) DEFAULT 0,
                                              create_user_id integer,
                                              create_time character(6),
                                              update_user_id integer,
                                              update_time character(6),
                                              version integer DEFAULT 1,
                                              telephone character varying(255) DEFAULT ''::character varying,
                                              dept_no character varying(255)
);


ALTER TABLE public.summer_sys_department OWNER TO postgres;

--
-- Name: TABLE summer_sys_department; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_department IS '组织部门表';


--
-- Name: COLUMN summer_sys_department.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.name IS '部门名称';


--
-- Name: COLUMN summer_sys_department.p_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.p_id IS '父部门id';


--
-- Name: COLUMN summer_sys_department.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.type IS '部门类型';


--
-- Name: COLUMN summer_sys_department.address; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.address IS '地址';


--
-- Name: COLUMN summer_sys_department.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.status IS '状态';


--
-- Name: COLUMN summer_sys_department.telephone; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.telephone IS '部门手机号';


--
-- Name: COLUMN summer_sys_department.dept_no; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_department.dept_no IS '部门编号';


--
-- Name: crm_sys_dept_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_dept_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_dept_id_seq OWNER TO postgres;

--
-- Name: crm_sys_dept_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_dept_id_seq OWNED BY public.summer_sys_department.id;


--
-- Name: summer_sys_operation_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_operation_log (
                                                 id integer NOT NULL,
                                                 username character varying(50),
                                                 type character varying,
                                                 params character varying(255),
                                                 oprate_time timestamp without time zone,
                                                 url character varying(50) DEFAULT 0,
                                                 module character varying(50)
);


ALTER TABLE public.summer_sys_operation_log OWNER TO postgres;

--
-- Name: TABLE summer_sys_operation_log; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_operation_log IS '系统操作日志表';


--
-- Name: COLUMN summer_sys_operation_log.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.username IS '操作人';


--
-- Name: COLUMN summer_sys_operation_log.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.type IS '操作类型';


--
-- Name: COLUMN summer_sys_operation_log.params; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.params IS '操作内容';


--
-- Name: COLUMN summer_sys_operation_log.oprate_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.oprate_time IS '操作时间';


--
-- Name: COLUMN summer_sys_operation_log.url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.url IS '操作接口';


--
-- Name: COLUMN summer_sys_operation_log.module; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_operation_log.module IS '操作模块';


--
-- Name: crm_sys_login_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_login_log_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_login_log_id_seq OWNER TO postgres;

--
-- Name: crm_sys_login_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_login_log_id_seq OWNED BY public.summer_sys_operation_log.id;


--
-- Name: summer_sys_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_menu (
                                        id integer NOT NULL,
                                        code character varying(255) DEFAULT NULL::character varying,
                                        name character varying(255) DEFAULT NULL::character varying,
                                        p_id integer DEFAULT 0,
                                        href character varying(255) DEFAULT NULL::character varying,
                                        icon character varying(255) DEFAULT NULL::character varying,
                                        "order" integer DEFAULT 1,
                                        remark character varying(255) DEFAULT NULL::character varying,
                                        type integer,
                                        level integer,
                                        create_user_id integer,
                                        update_user_id integer,
                                        version integer DEFAULT 1,
                                        create_time timestamp without time zone,
                                        update_time timestamp without time zone
);


ALTER TABLE public.summer_sys_menu OWNER TO postgres;

--
-- Name: TABLE summer_sys_menu; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_menu IS '系统菜单表';


--
-- Name: COLUMN summer_sys_menu.code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.code IS '菜单编码';


--
-- Name: COLUMN summer_sys_menu.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.name IS '菜单名称';


--
-- Name: COLUMN summer_sys_menu.p_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.p_id IS '父id';


--
-- Name: COLUMN summer_sys_menu.href; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.href IS '路由';


--
-- Name: COLUMN summer_sys_menu.icon; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.icon IS '图标';


--
-- Name: COLUMN summer_sys_menu."order"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu."order" IS '排序';


--
-- Name: COLUMN summer_sys_menu.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.remark IS '描述';


--
-- Name: COLUMN summer_sys_menu.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.type IS '类型';


--
-- Name: COLUMN summer_sys_menu.level; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_menu.level IS '层级';


--
-- Name: crm_sys_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_menu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_menu_id_seq OWNER TO postgres;

--
-- Name: crm_sys_menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_menu_id_seq OWNED BY public.summer_sys_menu.id;


--
-- Name: summer_sys_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_role (
                                        id integer NOT NULL,
                                        name character varying(255),
                                        status character(1),
                                        remark character varying(255),
                                        create_user_id integer,
                                        create_time timestamp without time zone,
                                        update_user_id integer,
                                        update_time timestamp without time zone,
                                        version integer DEFAULT 1
);


ALTER TABLE public.summer_sys_role OWNER TO postgres;

--
-- Name: TABLE summer_sys_role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_role IS '系统角色表';


--
-- Name: COLUMN summer_sys_role.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_role.name IS '名称';


--
-- Name: COLUMN summer_sys_role.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_role.status IS '状态';


--
-- Name: COLUMN summer_sys_role.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_role.remark IS '描述';


--
-- Name: crm_sys_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_role_id_seq OWNER TO postgres;

--
-- Name: crm_sys_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_role_id_seq OWNED BY public.summer_sys_role.id;


--
-- Name: summer_sys_role_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_role_menu (
                                             id integer NOT NULL,
                                             role_id integer,
                                             menu_id integer,
                                             create_user_id integer,
                                             create_time timestamp without time zone
);


ALTER TABLE public.summer_sys_role_menu OWNER TO postgres;

--
-- Name: TABLE summer_sys_role_menu; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_role_menu IS '角色菜单关系表';


--
-- Name: COLUMN summer_sys_role_menu.role_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_role_menu.role_id IS '角色id';


--
-- Name: COLUMN summer_sys_role_menu.menu_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_role_menu.menu_id IS '菜单id';


--
-- Name: crm_sys_role_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_role_menu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_role_menu_id_seq OWNER TO postgres;

--
-- Name: crm_sys_role_menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_role_menu_id_seq OWNED BY public.summer_sys_role_menu.id;


--
-- Name: summer_sys_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_user_role (
                                             id bigint NOT NULL,
                                             user_id bigint,
                                             role_id bigint,
                                             create_user_id bigint,
                                             create_time timestamp without time zone,
                                             update_user_id bigint,
                                             update_time timestamp without time zone
);


ALTER TABLE public.summer_sys_user_role OWNER TO postgres;

--
-- Name: TABLE summer_sys_user_role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_user_role IS '系统级别用户角色关联表';


--
-- Name: COLUMN summer_sys_user_role.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_user_role.user_id IS '登录用户id';


--
-- Name: COLUMN summer_sys_user_role.role_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_user_role.role_id IS '角色id';


--
-- Name: crm_sys_user_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.crm_sys_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.crm_sys_user_role_id_seq OWNER TO postgres;

--
-- Name: crm_sys_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.crm_sys_user_role_id_seq OWNED BY public.summer_sys_user_role.id;


--
-- Name: summer_platform_db_resource; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_platform_db_resource (
                                                    id integer NOT NULL,
                                                    source_name character varying(50) DEFAULT NULL::character varying,
                                                    url character varying(255) DEFAULT NULL::character varying,
                                                    username character varying(50) DEFAULT NULL::character varying,
                                                    password character varying(50) DEFAULT NULL::character varying,
                                                    db_name character varying(50) DEFAULT NULL::character varying,
                                                    create_time timestamp without time zone,
                                                    update_time timestamp without time zone,
                                                    schema character varying(20) DEFAULT 'public'::character varying
);


ALTER TABLE public.summer_platform_db_resource OWNER TO postgres;

--
-- Name: TABLE summer_platform_db_resource; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_platform_db_resource IS '数据库资源表';


--
-- Name: COLUMN summer_platform_db_resource.source_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_db_resource.source_name IS '数据源名称';


--
-- Name: COLUMN summer_platform_db_resource.url; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_db_resource.url IS '数据库连接';


--
-- Name: COLUMN summer_platform_db_resource.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_db_resource.username IS '数据库用户名';


--
-- Name: COLUMN summer_platform_db_resource.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_db_resource.password IS '数据库密码';


--
-- Name: COLUMN summer_platform_db_resource.db_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_db_resource.db_name IS '数据库名称';


--
-- Name: summer_platform_tenant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_platform_tenant (
                                               id integer NOT NULL,
                                               name character varying(100) DEFAULT NULL::character varying,
                                               db_resource_id integer,
                                               brand_name character varying(100) DEFAULT NULL::character varying,
                                               company_name character varying(100) DEFAULT NULL::character varying,
                                               company_tel character varying(50) DEFAULT NULL::character varying,
                                               company_email character varying(100) DEFAULT NULL::character varying,
                                               company_address character varying(255) DEFAULT NULL::character varying,
                                               status character varying(20) DEFAULT NULL::character varying,
                                               logo character varying(255) DEFAULT NULL::character varying,
                                               create_time timestamp without time zone,
                                               update_time timestamp without time zone
);


ALTER TABLE public.summer_platform_tenant OWNER TO postgres;

--
-- Name: TABLE summer_platform_tenant; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_platform_tenant IS '平台租户信息表';


--
-- Name: COLUMN summer_platform_tenant.name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.name IS '租户名称';


--
-- Name: COLUMN summer_platform_tenant.db_resource_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.db_resource_id IS '数据库资源id';


--
-- Name: COLUMN summer_platform_tenant.brand_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.brand_name IS '品牌名称';


--
-- Name: COLUMN summer_platform_tenant.company_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.company_name IS '企业名称';


--
-- Name: COLUMN summer_platform_tenant.company_tel; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.company_tel IS '企业电话';


--
-- Name: COLUMN summer_platform_tenant.company_email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.company_email IS '企业邮箱';


--
-- Name: COLUMN summer_platform_tenant.company_address; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.company_address IS '企业地址';


--
-- Name: COLUMN summer_platform_tenant.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.status IS '状态';


--
-- Name: COLUMN summer_platform_tenant.logo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_tenant.logo IS '图标';


--
-- Name: summer_platform_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_platform_user (
                                             id integer NOT NULL,
                                             username character varying(50) DEFAULT NULL::character varying,
                                             password character varying(255) DEFAULT NULL::character varying,
                                             last_login_time character varying(30) DEFAULT NULL::character varying,
                                             login_error_count integer,
                                             tenant_ids integer[],
                                             user_type character varying(255) DEFAULT NULL::character varying,
                                             status character varying(2) DEFAULT NULL::character varying,
                                             profile_photo character varying(50),
                                             create_time timestamp without time zone,
                                             update_time timestamp without time zone,
                                             nickname character varying(50),
                                             birthday timestamp without time zone,
                                             address character varying(100),
                                             mobile character varying(20),
                                             email character varying(50),
                                             gender character varying(10)
);


ALTER TABLE public.summer_platform_user OWNER TO postgres;

--
-- Name: TABLE summer_platform_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_platform_user IS '平台用户，用于登录系统';


--
-- Name: COLUMN summer_platform_user.username; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.username IS '用户名';


--
-- Name: COLUMN summer_platform_user.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.password IS '密码';


--
-- Name: COLUMN summer_platform_user.last_login_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.last_login_time IS '上次登录时间';


--
-- Name: COLUMN summer_platform_user.login_error_count; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.login_error_count IS '登录错误次数';


--
-- Name: COLUMN summer_platform_user.tenant_ids; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.tenant_ids IS '拥有的租户';


--
-- Name: COLUMN summer_platform_user.user_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.user_type IS '用户类型';


--
-- Name: COLUMN summer_platform_user.status; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.status IS '状态';


--
-- Name: COLUMN summer_platform_user.profile_photo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.profile_photo IS '用户头像';


--
-- Name: COLUMN summer_platform_user.nickname; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.nickname IS '用户昵称';


--
-- Name: COLUMN summer_platform_user.birthday; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.birthday IS '生日';


--
-- Name: COLUMN summer_platform_user.address; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.address IS '地址';


--
-- Name: COLUMN summer_platform_user.mobile; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.mobile IS '手机号';


--
-- Name: COLUMN summer_platform_user.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.email IS '邮箱';


--
-- Name: COLUMN summer_platform_user.gender; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_platform_user.gender IS '性别';


--
-- Name: summer_platform_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.summer_platform_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.summer_platform_user_id_seq OWNER TO postgres;

--
-- Name: summer_platform_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.summer_platform_user_id_seq OWNED BY public.summer_platform_user.id;


--
-- Name: summer_sys_dict; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_dict (
                                        id bigint NOT NULL,
                                        value character varying(255) DEFAULT NULL::character varying,
                                        text character varying(255) DEFAULT NULL::character varying,
                                        module character varying(255) DEFAULT NULL::character varying,
                                        sort bigint,
                                        create_user_id bigint,
                                        create_time timestamp without time zone,
                                        update_user_id bigint,
                                        update_time timestamp without time zone,
                                        version bigint DEFAULT 1,
                                        remark character varying(255) DEFAULT NULL::character varying,
                                        keyword character varying(20)
);


ALTER TABLE public.summer_sys_dict OWNER TO postgres;

--
-- Name: TABLE summer_sys_dict; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_dict IS '字典码表';


--
-- Name: COLUMN summer_sys_dict.value; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.value IS '字典key';


--
-- Name: COLUMN summer_sys_dict.text; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.text IS '字典值';


--
-- Name: COLUMN summer_sys_dict.module; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.module IS '所属模块';


--
-- Name: COLUMN summer_sys_dict.sort; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.sort IS '排序';


--
-- Name: COLUMN summer_sys_dict.remark; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.remark IS '字典描述';


--
-- Name: COLUMN summer_sys_dict.keyword; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_dict.keyword IS '查询关键字';


--
-- Name: summer_sys_dict_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.summer_sys_dict_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.summer_sys_dict_id_seq OWNER TO postgres;

--
-- Name: summer_sys_dict_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.summer_sys_dict_id_seq OWNED BY public.summer_sys_dict.id;


--
-- Name: summer_sys_user_department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.summer_sys_user_department (
                                                   id bigint NOT NULL,
                                                   department_id bigint,
                                                   user_id bigint,
                                                   create_user_id bigint,
                                                   version bigint DEFAULT 1,
                                                   create_time timestamp without time zone
);


ALTER TABLE public.summer_sys_user_department OWNER TO postgres;

--
-- Name: TABLE summer_sys_user_department; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.summer_sys_user_department IS '用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。';


--
-- Name: COLUMN summer_sys_user_department.department_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_user_department.department_id IS '部门id';


--
-- Name: COLUMN summer_sys_user_department.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_user_department.user_id IS '登录用户id';


--
-- Name: COLUMN summer_sys_user_department.version; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.summer_sys_user_department.version IS '乐观锁版本控制';


--
-- Name: summer_sys_user_department_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.summer_sys_user_department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.summer_sys_user_department_id_seq OWNER TO postgres;

--
-- Name: summer_sys_user_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.summer_sys_user_department_id_seq OWNED BY public.summer_sys_user_department.id;


--
-- Name: summer_tenant_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.summer_tenant_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.summer_tenant_id_seq OWNER TO postgres;

--
-- Name: summer_tenant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.summer_tenant_id_seq OWNED BY public.summer_platform_tenant.id;


--
-- Name: sys_db_resource_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sys_db_resource_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_db_resource_id_seq OWNER TO postgres;

--
-- Name: sys_db_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sys_db_resource_id_seq OWNED BY public.summer_platform_db_resource.id;


--
-- Name: summer_platform_db_resource id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_db_resource ALTER COLUMN id SET DEFAULT nextval('public.sys_db_resource_id_seq'::regclass);


--
-- Name: summer_platform_tenant id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_tenant ALTER COLUMN id SET DEFAULT nextval('public.summer_tenant_id_seq'::regclass);


--
-- Name: summer_platform_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_user ALTER COLUMN id SET DEFAULT nextval('public.summer_platform_user_id_seq'::regclass);


--
-- Name: summer_sys_data_auth id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_data_auth ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_data_auth_id_seq'::regclass);


--
-- Name: summer_sys_department id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_department ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_dept_id_seq'::regclass);


--
-- Name: summer_sys_dict id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_dict ALTER COLUMN id SET DEFAULT nextval('public.summer_sys_dict_id_seq'::regclass);


--
-- Name: summer_sys_menu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_menu ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_menu_id_seq'::regclass);


--
-- Name: summer_sys_operation_log id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_operation_log ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_login_log_id_seq'::regclass);


--
-- Name: summer_sys_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_role ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_role_id_seq'::regclass);


--
-- Name: summer_sys_role_menu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_role_menu ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_role_menu_id_seq'::regclass);


--
-- Name: summer_sys_user_department id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_user_department ALTER COLUMN id SET DEFAULT nextval('public.summer_sys_user_department_id_seq'::regclass);


--
-- Name: summer_sys_user_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_user_role ALTER COLUMN id SET DEFAULT nextval('public.crm_sys_user_role_id_seq'::regclass);

--
-- Name: crm_sys_data_auth_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_data_auth_id_seq', 1, false);


--
-- Name: crm_sys_dept_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_dept_id_seq', 1, false);


--
-- Name: crm_sys_login_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_login_log_id_seq', 1, false);


--
-- Name: crm_sys_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_menu_id_seq', 1, false);


--
-- Name: crm_sys_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_role_id_seq', 1, false);


--
-- Name: crm_sys_role_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_role_menu_id_seq', 1, false);


--
-- Name: crm_sys_user_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.crm_sys_user_role_id_seq', 1, false);


--
-- Name: summer_platform_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.summer_platform_user_id_seq', 1, false);


--
-- Name: summer_sys_dict_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.summer_sys_dict_id_seq', 1, false);


--
-- Name: summer_sys_user_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.summer_sys_user_department_id_seq', 1, false);


--
-- Name: summer_tenant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.summer_tenant_id_seq', 1, false);


--
-- Name: sys_db_resource_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sys_db_resource_id_seq', 1, false);


--
-- Name: summer_platform_db_resource summer_platform_db_resource_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_db_resource
    ADD CONSTRAINT summer_platform_db_resource_pk PRIMARY KEY (id);


--
-- Name: summer_platform_tenant summer_platform_tenant_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_tenant
    ADD CONSTRAINT summer_platform_tenant_pk PRIMARY KEY (id);


--
-- Name: summer_platform_user summer_platform_user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_platform_user
    ADD CONSTRAINT summer_platform_user_pk PRIMARY KEY (id);


--
-- Name: summer_sys_data_auth summer_sys_data_auth_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_data_auth
    ADD CONSTRAINT summer_sys_data_auth_pk PRIMARY KEY (id);


--
-- Name: summer_sys_department summer_sys_department_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_department
    ADD CONSTRAINT summer_sys_department_pk PRIMARY KEY (id);


--
-- Name: summer_sys_dict summer_sys_dict_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_dict
    ADD CONSTRAINT summer_sys_dict_pk PRIMARY KEY (id);


--
-- Name: summer_sys_menu summer_sys_menu_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_menu
    ADD CONSTRAINT summer_sys_menu_pk PRIMARY KEY (id);


--
-- Name: summer_sys_operation_log summer_sys_operation_log_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_operation_log
    ADD CONSTRAINT summer_sys_operation_log_pk PRIMARY KEY (id);


--
-- Name: summer_sys_role_menu summer_sys_role_menu_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_role_menu
    ADD CONSTRAINT summer_sys_role_menu_pk PRIMARY KEY (id);


--
-- Name: summer_sys_role summer_sys_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_role
    ADD CONSTRAINT summer_sys_role_pk PRIMARY KEY (id);


--
-- Name: summer_sys_user_department summer_sys_user_department_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_user_department
    ADD CONSTRAINT summer_sys_user_department_pk PRIMARY KEY (id);


--
-- Name: summer_sys_user_role summer_sys_user_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.summer_sys_user_role
    ADD CONSTRAINT summer_sys_user_role_pk PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--