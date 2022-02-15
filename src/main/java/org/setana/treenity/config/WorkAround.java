package org.setana.treenity.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

@Component
public class WorkAround implements WebMvcOpenApiTransformationFilter {

    @Value("${spring.baseUrl:${user.home}}")
    private String serverUrl;

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openAPI = context.getSpecification();

        Server server = new Server();
        server.setDescription("base URL");
        server.setUrl(serverUrl);

        openAPI.setServers(List.of(server));
        return openAPI;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }
}
