package com.share2renew.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域处理的配置文件 kevin
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            //这个不能是"*",一定需要有一个端口号 -> 对于有端口号的，不是html直接启动
            corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8086/"));  // or specific domain e.g. "http://localhost:8080"
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            //对于前端直接html启动没有端口的，得false.
            corsConfiguration.setAllowCredentials(true);//允许cookie跨域

            UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
            urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

            return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
