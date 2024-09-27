package br.com.bcb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI(@Value("${version:latest}") String version) {
        return new OpenAPI().info(info(version));
    }

    public Info info (String version) {
        return new Info()
                .title("BigChatBrasil")
                .version(version);
    }
}
