package com.summer.tools.common.configs;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@EnableSwagger2WebMvc
@EnableKnife4j
@Configuration
public class SwaggerConfiguration {
	@Value("${swagger.basePackage:com.summer.*}")
	private String basePackage;

	@Bean
	public Docket api() {
		List<Parameter> pars = new ArrayList<>();
		ParameterBuilder tokenPar = new ParameterBuilder();
		tokenPar.name("token").description("token").modelRef(new ModelRef("string"))
				.parameterType("header").required(true).order(0).build();
		pars.add(tokenPar.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiInfo())
				.select()
				.apis(basePackage())
				.paths(PathSelectors.any())
				.build().globalOperationParameters(pars);
	}

	private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackage.split(";")) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.ofNullable(input.getClass());
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SUMMER-TOOLS接口文档").version("1.0").build();
	}

	public Predicate<RequestHandler> basePackage() {
		return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
	}

}
