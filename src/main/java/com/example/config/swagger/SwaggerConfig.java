//package com.example.config.swagger;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@OpenAPIDefinition
//@Configuration
//@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
//public class SwaggerConfig {
//    @Value("${server.host}")
//    private String url;
//
//    @Bean
//    public OpenAPI myOpenAPI() {
//        Server devServer = new Server();
//        devServer.setUrl(url);
//        devServer.setDescription("Server URL");
//
//        Contact contact = new Contact();
//        contact.setEmail("scolaro.uz");
//        contact.setName("BezKoder");
//        contact.setUrl("https://www.bezkoder.com");
//
//
//        Info info = new Info()
//                .title("Scolaro.uz Management API")
//                .version("1.0")
//                .contact(contact)
//                .description("This API exposes endpoints to manage tutorials.")
//                .termsOfService("https://www.bezkoder.com/terms")
//                .license(null);
//
//        return new OpenAPI().info(info).servers(List.of(devServer));
//    }
//}
