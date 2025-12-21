package com.sportshub.team.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI teamServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("팀 서비스 API")
                        .description("Sports Hub 팀 관리 API 문서")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Sports Hub Team")
                                .email("support@sportshub.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8083").description("로컬 개발 서버"),
                        new Server().url("http://43.202.219.106:8083").description("운영 서버")
                ));
    }
}
