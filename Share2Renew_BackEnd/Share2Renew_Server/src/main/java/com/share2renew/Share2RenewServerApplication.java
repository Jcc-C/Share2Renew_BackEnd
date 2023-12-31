package com.share2renew;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动器
 */

@SpringBootApplication
@MapperScan("com.share2renew.mapper")
public class Share2RenewServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Share2RenewServerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
