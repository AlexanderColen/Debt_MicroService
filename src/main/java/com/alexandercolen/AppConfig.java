package com.alexandercolen;

import com.alexandercolen.service.DebtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Alexander
 */
@Configuration
public class AppConfig {
    @Bean
    public DebtService debtService() {
        return new DebtService();
    }
}