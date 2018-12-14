package com.jeesite.modules.fz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @Auther: len
 * @Date: 2018/8/20 14:21
 * @Description:
 */
@Configuration
public class FzUrlWebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }
    @Bean
    public FzAllUrlInterceptor fzAllInterceptor() {
        return new FzAllUrlInterceptor();
    }

    @Bean
    public FzNeigouInterceptor fzNeigouInterceptor(){
        return new FzNeigouInterceptor();
    }

    /**
     * 配置FzAllUrlInterceptor这个类拦截什么路径和放行什么路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //我这里把奥斯丁之前那个梵赞登陆的取消拦截的给取消了
        InterceptorRegistration registration = registry.addInterceptor(fzAllInterceptor());
        registration.addPathPatterns("/a/fz/**");
        registration.addPathPatterns("/a/ach/**");
        //添加拦截器并且设置拦截路径
        registry.addInterceptor(fzNeigouInterceptor()).addPathPatterns("/a/fz/**");
//        registry.addInterceptor(fgcAllInterceptor()).addPathPatterns("/a/fgc/**");
//        registry.addInterceptor(fgcAllInterceptor()).excludePathPatterns("/a/fgc/fgcUser/**");
//        registration.excludePathPatterns("/a/fgc/fgcUser/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
