//package com.summer.tools.mybatisplus.generators;
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baomidou.mybatisplus.generator.IFill;
//import com.baomidou.mybatisplus.generator.SimpleAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.fill.Property;
//import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
//import com.summer.tools.mybatisplus.orm.model.AbstractModel;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * mybatis-plus代码生成器(用于生成entity) 3.5.0版本
// * 如果不重写配置,默认通过交互方式生成代码
// * 坑一: entity默认不继承父类 {@link com.baomidou.mybatisplus.extension.activerecord.Model}, 手动设置父类丢泛型
// * 坑二: 无法设置主键生成策略
// * @author john
// */
//public class NewPostGreSqlCodeGenerator extends SimpleAutoGenerator {
//
//	public static void main(String[] args) {
//		new NewPostGreSqlCodeGenerator().execute();
//	}
//
//	// 添加数据源配置
//	@Override
//	public IConfigBuilder<DataSourceConfig> dataSourceConfigBuilder() {
//		return new DataSourceConfig
//				.Builder("jdbc:postgresql://1.15.132.121:7003/mybatis_demo", "postgres", "admin")
//				.typeConvert(PostgreSqlTypeConvert.INSTANCE).keyWordsHandler(new PostgreSqlKeyWordsHandler()).schema("public");
//	}
//
//	@Override
//	public IConfigBuilder<GlobalConfig> globalConfigBuilder() {
//		String outputDir = new File(System.getProperty("user.dir")) + File.separator + "build" + File.separator + "code";
//      System.out.println(outputDir);
//		return new GlobalConfig.Builder().fileOverride().openDir(false).enableSwagger()
//				.author("john.wang").outputDir(outputDir);
//	}
//
//	@Override
//	public IConfigBuilder<PackageConfig> packageConfigBuilder() {
//		return new PackageConfig.Builder().parent("com.summer.tools.mybatisplus")
//				.entity("orm.model").mapper("orm.dao").xml("orm.xml")
//				.service("service").serviceImpl("service.impl")
//				.controller("controller");
//	}
//
//	@Override
//	public IConfigBuilder<StrategyConfig> strategyConfigBuilder() {
//		String[] tables = new String[]{
////				"summer_boss_data_auth",
////				"summer_boss_department",
////				"summer_boss_dict",
////				"summer_boss_menu",
////				"summer_boss_operation_log",
////				"summer_boss_role",
////				"summer_boss_role_menu",
////				"summer_boss_user_department",
////				"summer_boss_user_role",
////				"summer_platform_tenant",
////				"summer_platform_db_resource",
//				"summer_platform_user"
//		};
//
//		//给生成器添加策略配置
//		List<IFill> tableFillList = new ArrayList<>();
//		tableFillList.add(new Property("create_user_id", FieldFill.INSERT));
//		tableFillList.add(new Property("create_time", FieldFill.INSERT));
//		tableFillList.add(new Property("creator", FieldFill.INSERT));
//		tableFillList.add(new Property("update_user_id", FieldFill.INSERT_UPDATE));
//		tableFillList.add(new Property("update_time", FieldFill.INSERT_UPDATE));
//		tableFillList.add(new Property("updater", FieldFill.UPDATE));
//
//		return new StrategyConfig.Builder().addTablePrefix("summer").addInclude(tables)
//				.entityBuilder().naming(NamingStrategy.underline_to_camel).addTableFills(tableFillList)
//					.enableColumnConstant().superClass(AbstractModel.class).enableLombok().versionColumnName("version")
//					.addSuperEntityColumns("create_time", "update_time")
//				.mapperBuilder().enableBaseResultMap().superClass(BaseMapper.class)
//				.serviceBuilder().superServiceClass(IService.class).superServiceImplClass(ServiceImpl.class)
//				.controllerBuilder().enableRestStyle();
//	}
//
//	@Override
//	public IConfigBuilder<InjectionConfig> injectionConfigBuilder() {
//		return new InjectionConfig.Builder().beforeOutputFile((t, map) -> map.put("abc","this is a updateTest"));
//	}
//}