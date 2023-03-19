//package com.ubox.gryffindor.authentication.config;
//
//import io.swagger.v3.oas.models.ExternalDocumentation;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.boot.info.BuildProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI setupOpenApi(BuildProperties buildProperties) {
//        return new OpenAPI()
//                .info(new Info().title("Authentication Apis")
//                        .description("Includes authenticate users...")
//                        .version(buildProperties.getVersion())
//                        .license(new License().name("Bifiin")
//                                .url("https://www.bifiin.com.vn"))
//                )
//                .externalDocs(new ExternalDocumentation()
//                        .description("Wiki Documentation")
//                        .url("https://www.bifiin.com.vn")
//                );
//    }
//
//    @Bean
//    GroupedOpenApi userApis() {
//        return GroupedOpenApi.builder().group("Otp Apis").pathsToMatch("/otp/**").build();
//    }
//}
