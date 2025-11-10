package apptive.nochigima.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(info()).servers(List.of(server()));
    }

    private Info info() {
        return new Info().title("놓치지마");
    }

    private Server server() {
        return new Server().url("/").description("API v1");
    }
}
