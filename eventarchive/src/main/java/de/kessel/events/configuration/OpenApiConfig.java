package eventarchieve.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI publicApi(BuildProperties buildInfo) {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName)
                        .description("API Documentation")
                        .version(buildInfo.getVersion()))
                .externalDocs(new ExternalDocumentation()
                        .description(applicationName + "API Documentation"));
    }
}
