package com.summer.tools.mybatisplus.generators;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatis-plus代码生成器(用于生成entity)
 * @author john
 */
public class MysqlCodeGenerator {

	public static void execute(String[] tables, String outputDir, String basePackage, String author) {

        //给生成器添加全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setFileOverride(true)
				.setActiveRecord(true)
				.setEnableCache(false)
				.setBaseResultMap(true)
				.setBaseColumnList(false)
				.setIdType(IdType.AUTO)
				.setAuthor(author)
				.setOutputDir(outputDir)
				.setOpen(false).setFileOverride(true);

        //给生成器添加数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL)
				.setDriverName("com.mysql.cj.jdbc.Driver").setUsername("root").setPassword("123456")
				.setUrl("jdbc:mysql://1.15.132.121:7004/flowable-my?serverTimezone=Asia/Shanghai&&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true");

		//给生成器添加策略配置
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill("create_user_id", FieldFill.INSERT));
		tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
		tableFillList.add(new TableFill("creator", FieldFill.INSERT));
		tableFillList.add(new TableFill("update_user_id", FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill("updater", FieldFill.UPDATE));

		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix("summer").setNaming(NamingStrategy.underline_to_camel).setEntityLombokModel(true)
				.setInclude(tables).setTableFillList(tableFillList).setRestControllerStyle(true)
				.setVersionFieldName("version").setLogicDeleteFieldName("is_del")
				.setSuperServiceClass(IService.class).setSuperServiceImplClass(ServiceImpl.class);

		//给生成器添加包配置
		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(basePackage).setEntity("orm.model")
				.setMapper("orm.dao").setXml("orm.dao.mapping")
				.setService("service").setServiceImpl("service.impl").setController("controller");

		//给生成器添加参数注入配置
		InjectionConfig ic = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<>(10);
				map.put("abc","this is a updateTest");
				this.setMap(map);
			}
		};
		//创建代码生成器
		AutoGenerator mpg = new AutoGenerator();
		mpg.setGlobalConfig(gc).setDataSource(dsc).setStrategy(strategy).setPackageInfo(pc).setCfg(ic).execute();
		// 打印注入设置
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

	public static void main(String[] args) {
		String[] tables = new String[]{
				"process_template"
				 };
		String outputDir = "E:\\springboot\\spring-boot-tools\\mybatis-plus-dao\\src\\main\\java";
		String basePackage = "com.summer.tools.mybatisplus";
		execute(tables,outputDir,basePackage, "john.wang");
	}
}