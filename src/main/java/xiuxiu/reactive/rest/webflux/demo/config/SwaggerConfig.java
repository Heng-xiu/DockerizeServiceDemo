package xiuxiu.reactive.rest.webflux.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableSwagger2WebFlux
@Configuration
public class SwaggerConfig {

    private static final String GROUP_NAME = "CSCI";
    private static final String BASE_PACKAGE = "xiuxiu.reactive.rest.webflux.demo.controllers";
    private static final String TITLE = "CSCI4050/6050 TERM project";
    private static final String DESCRIPTION = "AMC";
    private static final String VERSION = "0.0.1-SNAPSHOT";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

}
