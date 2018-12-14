/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.jeesite.common.io.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.MultipartConfigElement;

/**
 * JeeSite Web
 * @author ThinkGem
 * @version 2018-1-8
 */
@SpringBootApplication(scanBasePackages={"com.jeesite.modules"})
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setDefaultProperties(PropertiesUtils.getInstance().getProperties());
		app.run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		this.setRegisterErrorPageFilter(false); // 错误页面有容器来处理，而不是SpringBoot
		builder.properties(PropertiesUtils.getInstance().getProperties());
		return builder.sources(Application.class);
	}
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//允许上传的文件最大值
		factory.setMaxFileSize("50MB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("1000MB");
		return factory.createMultipartConfig();
	}
	    @Configuration
    public  class CorsConfig{
        private CorsConfiguration buildConfig(){
            CorsConfiguration corsConfiguration=new CorsConfiguration();
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedMethod("*");
            return corsConfiguration;
        }
        @Bean
        public CorsFilter corsFilter(){
            UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",buildConfig());
            return new CorsFilter(source);
        }
    }
}