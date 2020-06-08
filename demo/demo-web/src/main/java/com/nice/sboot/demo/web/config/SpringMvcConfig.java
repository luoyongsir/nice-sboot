package com.nice.sboot.demo.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luoyong
 * @date 2019/7/27 11:28
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//		// fast json 循环引用
//		FastJsonConfig config = new FastJsonConfig();
//		config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
//		converter.setFastJsonConfig(config);
//
//		converters.add(0, converter);
//	}

}
