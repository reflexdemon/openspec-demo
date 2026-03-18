package io.vpv.spec.openspec_demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI todoApiOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("TODO Task API")
                .description("REST API for managing TODO tasks with multi-user support")
                .version("1.0.0")
                .contact(new Contact()
                    .name("API Support")
                    .email("support@example.com")));
    }
}
