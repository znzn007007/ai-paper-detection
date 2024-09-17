package com.nemo.detection.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 本地调试时的cors配置
 *
 * @author nemo
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
		        .allowedOrigins("*")
		        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
}

