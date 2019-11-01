package br.com.janadev.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    private final BuildProperties buildProperties;

//    @Autowired
//    public SwaggerConfig(BuildProperties buildProperties) {
//        this.buildProperties = buildProperties;
//    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.janadev.ecommerce.resource"))
                .build()
                .apiInfo(getMavenProjectInfo());
    }

    private ApiInfo getMavenProjectInfo() {

        return new ApiInfoBuilder()
                .title("Ecommerce Monolito")
                .description("Projeto de exemplo para estudar Spring Boot")
                .version("0.1.0")
                .build();
    }

//    private String getDescription() {
//        String description = buildProperties.get("${project.description}");
//        if (StringUtils.isEmpty(description)){
//            description = "Projeto de Ecommerce para estudo de Java com Spring Boot.";
//        }
//        return description;
//    }
}
