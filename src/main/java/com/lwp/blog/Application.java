package com.lwp.blog;

import com.lwp.blog.config.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableWebSocket
public class Application implements ServletContextInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private SysConfig sysConfig;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String defaultCookie = sysConfig.getDefaultCookie();
        servletContext.getSessionCookieConfig().setName(defaultCookie);
        try {
            throw new Exception();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

