
package cobranza.v2.pgt.com.config.Swagger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class) public class SwaggerConfig {
  
  @Bean
  public Docket api( ) throws IOException, URISyntaxException {
    final List<ResponseMessage> globalResponse = Arrays.asList(
    // new
    // ResponseMessageBuilder().code(200).message("OK").build(),
    // new
    // ResponseMessageBuilder().code(201).message("Created").build(),
    // new
    // ResponseMessageBuilder().code(400).message("Bad
    // Request").build(),
    // new
    // ResponseMessageBuilder().code(500).message("Internal
    // Error").build()
    );
    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(
      false).globalResponseMessage(RequestMethod.GET, globalResponse).globalResponseMessage(
        RequestMethod.POST,
        globalResponse).select( ).apis(
          RequestHandlerSelectors.basePackage("cobranza.v2.pgt.com.controllers.swagger")).paths(
            PathSelectors.any( )).build( ).apiInfo(apiInfo( ));
  }
  
  private ApiInfo apiInfo( ) {
    return new ApiInfoBuilder( ).title("PAGATODO 360 API Rest").contact(
      new Contact("Pagatodo360", "https://www.pagatodo360.net", "https://www.pagatodo360.net")).description(
        "Descripción personalizada de API Pagatodo 360.").version("1.0").build( );
    // return new ApiInfo("PAGATODO 360 API
    // Rest","Descripción personalizada de API
    // Pagatodo 360.","",
    // "",new
    // Contact("Pagatodo360","https://www.pagatodo360.net","https://www.pagatodo360.net"),"",
    // "",Collections.emptyList());
  }
  
}
