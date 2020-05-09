package br.com.janadev.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ResponseMessage msg201 = customMessage();
    private final ResponseMessage msg204put = simpleMessage(204, "Atualização ok");
    private final ResponseMessage msg204del = simpleMessage(204, "Deleção ok");
    private final ResponseMessage msg403 = simpleMessage(403, "Não autorizado");
    private final ResponseMessage msg404 = simpleMessage(404, "Não encontrado");
    private final ResponseMessage msg422 = simpleMessage(422, "Erro de validação");
    private final ResponseMessage msg500 = simpleMessage(500, "Erro inesperado");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(msg403, msg404, msg500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(msg201, msg403, msg422, msg500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(msg204put, msg403, msg404, msg422, msg500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(msg204del, msg403, msg404, msg500))
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.janadev.ecommerce.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API Ecommerce Monolito",
                "API da Aplicação de ecommerce para estudo do Spring Boot",
                "Versão 1.0",
                "www.janadev.com.br/ecommerce-monolito/terms",
                new Contact("Janaina Paula", "www.janadev.com.br", "janadev@gmail.com"),
                "Permitido uso para estudantes",
                "www.janadev.com.br/ecommerce-monolito/terms",
                Collections.emptyList() // Vendor Extensions
        );
    }

    public ResponseMessage simpleMessage(int code, String message){
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    private ResponseMessage customMessage() {
        Map<String, Header> map = new HashMap<>();
        map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
        return new ResponseMessageBuilder()
                .code(201)
                .message("Recurso criado")
                .headersWithDescription(map)
                .build();
    }
}
