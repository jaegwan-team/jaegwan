package com.bwmanager.jaegwan.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("재관둥이 API 명세서")
                .description(
                        "<h2>자율 프로젝트</h2>" +
                                "<h3>Swagger를 이용한 API 명세서</h3><br>" +
                                "<img src=\"/images/Dr_cha_LOGO.png\" alt = '프로젝트 로고'  width=\"200\">" +
                                "<h3>프로젝트 정보</h3>" +
                                "재관둥이")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("재관둥이")
                        .url("http://localhost:8080")
                );

        // JWT 보안 스키마 정의
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER)
//                .name("Authorization");

        // 전역 보안 요구사항 정의
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
//
//        Server server = new Server();
//        server.setUrl("https://k11a501.p.ssafy.io/");
        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
//                .security(Arrays.asList(securityRequirement))
                .info(info);
    }

    // ! auth 관련 API 모음
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/auth/**")
                .build();
    }

    // ! member 관련 API 모음
    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("member")
                .pathsToMatch("/member/**")
                .build();
    }

    // ! receipt 관련 API 모음
    @Bean
    public GroupedOpenApi receiptApi() {
        return GroupedOpenApi.builder()
                .group("receipt")
                .pathsToMatch("/receipt/**")
                .build();
    }

    // ! ingredient 관련 API 모음
    @Bean
    public GroupedOpenApi ingredientApi() {
        return GroupedOpenApi.builder()
                .group("chat")
                .pathsToMatch("/api/ingredient/**")
                .build();
    }

}
