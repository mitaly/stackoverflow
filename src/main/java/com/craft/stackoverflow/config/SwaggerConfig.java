package com.craft.stackoverflow.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI baseOpenAPI() {
        ApiResponse badRequest= new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "    \"statusCode\": 400,\n" +
                                        "    \"message\": \"string\",\n" +
                                        "    \"timestamp\": \"2024-08-27T11:53:11.859034\",\n" +
                                        "    \"additionalInfo\": {\n" +
                                        "        \"string\": \"string\"\n" +
                                        "    }\n" +
                                        "}"))));
        ApiResponse internalServerError= new ApiResponse().content(
                new Content().addMediaType("application/json",
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "    \"statusCode\": 500,\n" +
                                        "    \"message\": \"reason\",\n" +
                                        "    \"timestamp\": \"2024-08-27T11:54:28.494569\",\n" +
                                        "    \"additionalInfo\": null\n" +
                                        "}"))));
        Components components = new Components();
        components.addResponses("badRequest",badRequest);
        components.addResponses("internalServerError", internalServerError);
        return new OpenAPI().components(components)
                .info(new Info()
                        .title("Springboot_Swagger Project OpenAPI Docs")
                        .version("1.0.0").description("Doc Description"));
    }
}
