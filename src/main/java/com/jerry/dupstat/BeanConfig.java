package com.jerry.dupstat;

import com.jerry.dupstat.domain.Login;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public Login login() {
        return new Login();
    }
}
