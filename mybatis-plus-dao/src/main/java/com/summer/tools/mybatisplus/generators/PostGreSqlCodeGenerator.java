package com.summer.tools.mybatisplus.generators;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
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
public class PostGreSqlCodeGenerator {

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
				.setOutputDir(outputDir);

        //给生成器添加数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.POSTGRE_SQL)
				.setDriverName("org.postgresql.Driver").setUsername("postgres").setPassword("admin")
				.setUrl("jdbc:postgresql://175.24.121.139:7003/summer_boss")
		;

		//给生成器添加策略配置
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill("create_user_id", FieldFill.INSERT));
		tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
		tableFillList.add(new TableFill("update_user_id", FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));

		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix("summer").setNaming(NamingStrategy.underline_to_camel).setEntityLombokModel(true)
				.setInclude(tables).setTableFillList(tableFillList).setVersionFieldName("version");

		//给生成器添加包配置
		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(null).setEntity(String.format(basePackage, "model"))
				.setMapper(String.format(basePackage, "dao"))
				.setXml(String.format(basePackage, "dao.mapping"))
				.setService("ignore").setServiceImpl("ignore").setController("ignore");

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
				"summer_boss_data_auth",
				"summer_boss_department",
				"summer_boss_dict",
				"summer_boss_menu",
				"summer_boss_operation_log",
				"summer_boss_role",
				"summer_boss_role_menu",
				"summer_boss_user_department",
				"summer_boss_user_role",
				"summer_platform_tenant",
				"summer_platform_db_resource",
				"summer_platform_user"
				 };
		String outputDir = "D:\\demo\\summer_boss\\src\\main\\java";
		String basePackage = "com.william.boss.orm.%s";
		execute(tables,outputDir,basePackage, "john");
	}
}