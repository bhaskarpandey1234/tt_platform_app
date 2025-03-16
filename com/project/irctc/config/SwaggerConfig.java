package com.project.irctc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SCHEMA_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IRCTC API Documentation")
//                        .version("1.0.0")
                        .description("API documentation for the IRCTC project")
//                        .contact(new Contact()
//                                .name("Bhaskar Pandey")
//                                .email("your.email@example.com")
//                                .url("https://your-website.com"))
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("http://springdoc.org")
//                                )
                                )
                .addSecurityItem(new SecurityRequirement().addList(SCHEMA_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SCHEMA_NAME, new SecurityScheme()
                                .name(SCHEMA_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}










//package com.project.irctc.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
//
//    private SecurityScheme createAPIKeyScheme() {
//        return new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT");
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, createAPIKeyScheme()))
//                .info(new Info()
//                        .title("IRCTC API")
//                        .description("API documentation with JWT Authorization")
//                        .version("1.0.0")
//                        .contact(new Contact()
//                                .name("Your Name")
//                                .email("your.email@example.com")
//                                .url("https://your-website.com"))
//                        .license(new License()
//                                .name("API License")
//                                .url("https://api-license-url.com")));
//    }
//}


//package com.project.irctc.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    private static final String SECURITY_SCHEME_NAME = "BearerAuth";
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Spring Boot API")
//                        .version("1.0.0")
//                        .description("API documentation with Swagger and JWT Token Support"))
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                                new SecurityScheme()
//                                        .name(SECURITY_SCHEME_NAME)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//    }
//}
//
