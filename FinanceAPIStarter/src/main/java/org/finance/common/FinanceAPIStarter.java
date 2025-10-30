package org.finance.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("org.finance")
@EnableJpaRepositories("org.finance.infra.database.repository")
@EntityScan("org.finance.infra.database.entity")
@OpenAPIDefinition(info = @Info(title = "Financial Services API", version="1.0"))
public class FinanceAPIStarter {
    public static void main(String[] args) {
        SpringApplication springBootApplication = new SpringApplication(FinanceAPIStarter.class);
        springBootApplication.run();
    }
}