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
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(100) DEFAULT NULL COMMENT '更新人',
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
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程线条表';

drop table if exists `process_node`;
CREATE TABLE `process_node` (
  `id` varchar(100) NOT NULL COMMENT '节点id',
  `name` varchar(100) DEFAULT NULL COMMENT '节点名称',
  `template_id` varchar(100) DEFAULT NULL COMMENT '流程模板id',
  `type` int(11) DEFAULT NULL COMMENT '节点类型',
  `implementation_type` varchar(100) DEFAULT NULL COMMENT '任务实现类型，默认class,针对ServiceTask',
  `implementation` varchar(100) DEFAULT NULL COMMENT '任务实现类，全限定java类名,针对ServiceTask',
  `assignee` varchar(100) DEFAULT NULL COMMENT '处理人，针对UserTask',
  `listeners` varchar(100) DEFAULT NULL COMMENT '节点监听器',
  `lines` varchar(100) DEFAULT NULL COMMENT '节点关联线条',
  `height` int(11) DEFAULT NULL COMMENT '节点高度',
  `width` int(11) DEFAULT NULL COMMENT '节点宽度',
  `axis_x` int(11) DEFAULT NULL COMMENT 'x轴坐标',
  `axis_y` int(11) DEFAULT NULL COMMENT 'y轴坐标',
  `create_time` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程节点表';

-- 解决项目启动(非第一次)失败bug
ALTER TABLE ACT_APP_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CMMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_DMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_FO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE FLW_EV_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
