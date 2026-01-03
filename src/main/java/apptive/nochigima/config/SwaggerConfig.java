package apptive.nochigima.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info())
                .servers(List.of(server()))
                .tags(tags())
                .components(components())
                .addSecurityItem(securityRequirement());
    }

    private Info info() {
        return new Info().title("놓치지마!");
    }

    private Server server() {
        return new Server().url("/").description("API v1");
    }

    private List<Tag> tags() {
        return List.of(
                new Tag().name("인증 API").description("카카오, 구글 OAuth를 통해 회원가입 및 로그인, 로그인 정보 갱신, 로그아웃"),
                new Tag().name("브랜드 API").description("브랜드 id로 상품 목록 조회 (할인중인 상품만 필터링 가능)"),
                new Tag().name("카테고리 API").description("카테고리 id로 브랜드 목록과 현재 할인중인 상품 개수 조회"),
                new Tag().name("상품 API").description("상품 id로 상품 상세정보 조회 및 할인 정보 수정"));
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(
                        "JWT",
                        new SecurityScheme()
                                .name("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList("JWT");
    }
}
