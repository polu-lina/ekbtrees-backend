package ru.naumen.ectmauth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EkbTrees Auth API", version = "2.0", description = "Authorization Information"))
public class Application {




    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
