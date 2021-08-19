-- 自定义表用于保存流程模板,如果是xml流程文件部署,只保存模板,不保存节点和连线
-- 如果是bpmnModel部署,保存节点和连线
drop table if exists `process_template`;
CREATE TABLE `process_template` (
  `template_id` varchar(100) NOT NULL COMMENT '流程模板id',
  `name` varchar(100) DEFAULT NULL COMMENT '流程模板名称',
  `type` int(11) DEFAULT NULL COMMENT '流程模板类型 1:审批流程',
  `description` varchar(100) DEFAULT NULL COMMENT '流程描述',
  `xml_data` text COMMENT '流程xml内容',
  `status` int(2) COMMENT '1: 草稿 2: 发布 3: 删除',
  `version` int(11) COMMENT '版本号',
  `is_del` int(2) COMMENT '0: 正常 1: 删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程模板表';

drop table if exists `process_line`;
CREATE TABLE `process_line` (
  `id` varchar(100) NOT NULL COMMENT '线条id',
  `name` varchar(100) DEFAULT NULL COMMENT '线条名称',
  `template_id` varchar(100) DEFAULT NULL COMMENT '流程模板id',
  `source_ref` varchar(100) DEFAULT NULL COMMENT '源节点id',
  `target_ref` varchar(100) DEFAULT NULL COMMENT '目标节点id',
  `condition_expression` varchar(100) DEFAULT NULL COMMENT '流传条件表达式',
  `version` int(11) COMMENT '版本号',
  `is_del` int(2) COMMENT '0: 正常 1: 删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程线条表';

drop table if exists `process_node`;
CREATE TABLE `process_node` (
  `id` varchar(100) NOT NULL COMMENT '节点id',
  `name` varchar(100) DEFAULT NULL COMMENT '节点名称',
  `template_id` varchar(100) DEFAULT NULL COMMENT '流程模板id',
  `type` int(11) DEFAULT NULL COMMENT '节点类型 1:开始节点 2:结束节点 3:用户任务 4:自动执行任务 5:排他网关 6:并行网关 7:等待事件',
  `category` varchar(20) DEFAULT NULL COMMENT '节点标签 approve:审批 notice 知会',
  `implementation_type` varchar(100) DEFAULT NULL COMMENT '任务实现类型，默认class,针对ServiceTask',
  `implementation` varchar(100) DEFAULT NULL COMMENT '任务实现类，全限定java类名,针对ServiceTask',
  `assignee` varchar(100) DEFAULT NULL COMMENT '处理人，针对UserTask',
  `candidate_users` varchar(200) DEFAULT NULL COMMENT '候选人',
  `listeners` varchar(100) DEFAULT NULL COMMENT '节点监听器',
  `lines` varchar(100) DEFAULT NULL COMMENT '节点关联线条',
  `cron` varchar(100) DEFAULT NULL COMMENT 'cron 表达式',
  `height` int(11) DEFAULT NULL COMMENT '节点高度',
  `width` int(11) DEFAULT NULL COMMENT '节点宽度',
  `axis_x` int(11) DEFAULT NULL COMMENT 'x轴坐标',
  `axis_y` int(11) DEFAULT NULL COMMENT 'y轴坐标',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `is_del` int(2) DEFAULT NULL COMMENT '0: 正常 1: 删除',
  `create_time` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程节点表'

drop table if exists `process_form`;
CREATE TABLE `process_form` (
  `id` varchar(100) NOT NULL COMMENT '表单id',
  `key` varchar(100) NOT NULL COMMENT '表单key',
  `name` varchar(100) DEFAULT NULL COMMENT '表单名称',
  `content` varchar(500) DEFAULT NULL COMMENT '表单内容',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `is_del` int(2) DEFAULT NULL COMMENT '0: 正常 1: 删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单表';

drop table if exists `process_form_field`;
CREATE TABLE `process_form_field` (
  `id` varchar(100) NOT NULL COMMENT '字段id',
  `key` varchar(100) NOT NULL COMMENT '字段key',
  `name` varchar(100) DEFAULT NULL COMMENT '字段名称',
  `type` varchar(100) DEFAULT NULL COMMENT '字段类型',
  `required` bool DEFAULT false COMMENT '是否必填',
  `form_id` varchar(100) DEFAULT NULL COMMENT '表单id',
  `placeholder` varchar(100) DEFAULT NULL COMMENT '占位符',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `is_del` int(2) DEFAULT NULL COMMENT '0: 正常 1: 删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段表';

-- 坑一: 时间类型转换bug, 解决方法修改字段类型(执行下面sql)
ALTER TABLE ACT_APP_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CMMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_DMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_FO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE FLW_EV_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;

-- 坑二: 启动时数据库名称为flowable,才会创建表结构和初始话数据, 解决方法新建库flowable初始化完后,再新建需要的数据库并把flowable库的表结构和数据导过来,同时修改配置文件里的数据库名称
-- 以mysql为例
-- 创建新数据库并把flowable表结构和数据导入到新数据库(需要在MySQL服务器上执行)
mysqladmin -uroot -p123456 create new_database
mysqldump -uroot -p123456 flowable | mysql -uroot -p123456 new_database