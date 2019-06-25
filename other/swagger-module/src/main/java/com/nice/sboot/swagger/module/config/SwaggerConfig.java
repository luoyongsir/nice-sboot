package com.nice.sboot.swagger.module.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生产环境pro不启用Swagger
 * @author luoyong
 * @date 2019/6/25 12:24
 */
@Configuration
@EnableSwagger2
@Profile({"env", "dev", "test"})
public class SwaggerConfig {

	@Value("${swagger.api.title:接口文档}")
	private String title;
	@Value("${swagger.api.description:}")
	private String description;
	@Value("${swagger.api.base-package:com.ybf}")
	private String basePackage;
	@Value("${swagger.mapping.path:/}")
	private String mappingPath;

	@Bean
	public Docket swaggerDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().title(title).description(description).build()).select()
				.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
	}

	/**
	 * SwaggerUI资源访问
	 * @param servletContext
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext,
			@Value("${swagger.mapping.order:10}") int order) throws Exception {
		SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
		if ("/".equals(mappingPath)) {
			return urlHandlerMapping;
		}
		Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
		{
			PathResourceResolver pathResourceResolver = new PathResourceResolver();
			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
			resourceHttpRequestHandler
					.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
			resourceHttpRequestHandler.setServletContext(servletContext);
			resourceHttpRequestHandler.afterPropertiesSet();
			//设置新的路径
			urlMap.put(mappingPath + "/webjars/**", resourceHttpRequestHandler);
		}
		{
			PathResourceResolver pathResourceResolver = new PathResourceResolver();
			pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
			pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

			ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
			resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
			resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
			resourceHttpRequestHandler.setServletContext(servletContext);
			resourceHttpRequestHandler.afterPropertiesSet();
			//设置新的路径
			urlMap.put(mappingPath + "/**", resourceHttpRequestHandler);
		}
		urlHandlerMapping.setUrlMap(urlMap);
		//调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
		urlHandlerMapping.setOrder(order);
		return urlHandlerMapping;

	}

	@Controller
	@ApiIgnore
	@RequestMapping("/")
	@Profile({"env", "dev", "test"})
	public class SwaggerResourceController implements InitializingBean {

		@Autowired
		private ApiResourceController apiResourceController;

		@Autowired
		private Environment environment;

		@Autowired
		private DocumentationCache documentationCache;

		@Autowired
		private ServiceModelToSwagger2Mapper mapper;

		@Autowired
		private JsonSerializer jsonSerializer;

		private Swagger2Controller swagger2Controller;

		@Override
		public void afterPropertiesSet() {
			swagger2Controller = new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
		}

		@RequestMapping("/**/swagger-resources/configuration/security")
		@ResponseBody
		public ResponseEntity<?> securityConfiguration(HttpServletRequest request) {
			String swaggerPath = getSwaggerPath(request, "/swagger-resources/configuration/security");
			if (mappingPath.equals(swaggerPath)) {
				return apiResourceController.securityConfiguration();
			}
			return null;
		}

		@RequestMapping("/**/swagger-resources/configuration/ui")
		@ResponseBody
		public ResponseEntity<UiConfiguration> uiConfiguration(HttpServletRequest request) {
			String swaggerPath = getSwaggerPath(request, "/swagger-resources/configuration/ui");
			if (mappingPath.equals(swaggerPath)) {
				return apiResourceController.uiConfiguration();
			}
			return null;
		}

		@RequestMapping("/**/swagger-resources")
		@ResponseBody
		public ResponseEntity<List<SwaggerResource>> swaggerResources(HttpServletRequest request) {
			String swaggerPath = getSwaggerPath(request, "/swagger-resources");
			if (mappingPath.equals(swaggerPath)) {
				return apiResourceController.swaggerResources();
			}
			return null;
		}

		@RequestMapping(value = "/**/v2/api-docs", method = RequestMethod.GET, produces = {"application/json",
				"application/hal+json"})
		@ResponseBody
		public ResponseEntity<Json> getDocumentation(
				@RequestParam(value = "group", required = false) String swaggerGroup, HttpServletRequest request) {
			String swaggerPath = getSwaggerPath(request, "/v2/api-docs");
			if (mappingPath.equals(swaggerPath)) {
				return swagger2Controller.getDocumentation(swaggerGroup, request);
			}
			return null;
		}

		private String getSwaggerPath(HttpServletRequest request, String delStr) {
			String uri = request.getRequestURI();
			return uri.replace(delStr, "");
		}
	}

}
