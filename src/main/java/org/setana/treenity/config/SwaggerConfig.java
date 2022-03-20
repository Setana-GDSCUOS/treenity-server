package org.setana.treenity.config;

import com.fasterxml.classmate.TypeResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.config.swagger.Page;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .alternateTypeRules(AlternateTypeRules
                .newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class))
            )
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("org.setana.treenity.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securityContexts(List.of(securityContext()))
            .securitySchemes(List.of(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Treenity API Swagger")
            .description("API Swagger for Treenity")
            .version("0.0.1")
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
            "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

}
