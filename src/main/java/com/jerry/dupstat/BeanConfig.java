package com.jerry.dupstat;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.Summary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public Login login(){
        return new Login();
    }
    @Bean
    public Summary summary(){
        return new Summary();
    }
}
