package hu.fodortech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Construction SaaS API")
                        .version("1.0")
                        .description("SaaS platform for construction contractors")
                        .contact(new Contact()
                                .name("Construction Team")
                                .email("support@fodortech.hu")));
    }
}
