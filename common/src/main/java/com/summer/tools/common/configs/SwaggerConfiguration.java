package com.summer.tools.common.configs;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.summer.tools.common.properties.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@EnableSwagger2WebMvc
@EnableKnife4j
@Configuration
public class SwaggerConfiguration {
	@Resource
	private SwaggerProperties properties;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
				.paths(PathSelectors.any())
				.build().globalOperationParameters(buildGlobalParameters());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("SUMMER-TOOLS接口文档")
				.description(properties.getDescription())
				.version("1.0")
				.build();
	}

	private List<Parameter> buildGlobalParameters() {
		List<Parameter> globalParams = new ArrayList<>();
		properties.getGlobalParams().forEach(param -> {
			ParameterBuilder tokenPar = new ParameterBuilder();
			Parameter parameter = tokenPar.name(param.getName())
					.description(param.getDescription())
					.modelRef(new ModelRef(param.getJavaType()))
					.parameterType(param.getParamType())
					.required(true)
					.order(properties.getGlobalParams().indexOf(param))
					.build();
			globalParams.add(parameter);
		});
		return globalParams;
	}
}
