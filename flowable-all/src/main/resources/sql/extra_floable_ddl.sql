-- 坑一: 时间类型转换bug, 解决方法修改字段类型(执行下面sql)
ALTER TABLE ACT_APP_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CMMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_CO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_DMN_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_FO_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE FLW_EV_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;
ALTER TABLE ACT_DE_DATABASECHANGELOG MODIFY COLUMN DATEEXECUTED varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL;

-- 坑二: 启动时数据库名称为flowable,才会创建表结构和初始话数据, 解决方法新建库flowable初始化完后,再新建需要的数据库并把flowable库的表结构和数据导过来,同时修改配置文件里的数据库名称
-- modeler依赖的库可以不是flowable,但是也存在坑一的bug. 以mysql为例
-- 创建新数据库并把flowable表结构和数据导入到新数据库(需要在MySQL服务器上执行)
mysqladmin -uroot -p123456 create flowabl-all
mysqldump -uroot -p123456 flowable | mysql -uroot -p123456 flowable-all



