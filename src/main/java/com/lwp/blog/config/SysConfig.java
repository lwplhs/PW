package com.lwp.blog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/15/10:58
 * @Description:
 */
@Configuration
public class SysConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfig.class);
    private static Map configMap = new HashMap();

    private void init(String name){
        try {
            Properties properties = new Properties();
            InputStream in = SysConfig.class.getClassLoader().getResourceAsStream(name + ".properties");
            properties.load(in);
            LOGGER.info("加载{}.properties参数", name);

            for (String keyName : properties.stringPropertyNames()) {
                String value = properties.getProperty(keyName);
                configMap.put(keyName,value);
                LOGGER.info("{}.properties---------key:{},value:{}", name, keyName, value);
            }
            LOGGER.info("{}.properties参数加载完毕", name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getParamer(String key){
        String result = "";
        try {
            result = configMap.get(key).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Bean
    public void getConfigMap() {
        init("SysConfig");
    }

}
