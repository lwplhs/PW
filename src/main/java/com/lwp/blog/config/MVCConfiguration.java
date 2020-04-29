package com.lwp.blog.config;

import com.lwp.blog.interceptor.BaseInterceptor;
import com.lwp.blog.interceptor.PageInterceptor;
import com.lwp.blog.utils.TaleUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Resource
    private PageInterceptor pageInterceptor;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("forward:/index.html");

        //registry.addViewController("/chat").setViewName("forward:/chat_room.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor).excludePathPatterns("/static/**").excludePathPatterns("/media/**");
        /*registry.addInterceptor(pageInterceptor).excludePathPatterns("/static/**").excludePathPatterns("/media/**");*/
    }

    /**
     * 添加静态资源文件 ，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/media/**").addResourceLocations(TaleUtils.getUEditorPath()+"/media/");
        registry.addResourceHandler("/upload/**").addResourceLocations(TaleUtils.getUploadFilePath()+"upload/");
    }
}
