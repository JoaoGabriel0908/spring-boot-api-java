package med.voll.api.infra.security.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

//Classe responsável por configurar o header no swagger
@Configuration
public class SpringDocConfiguration {
	
//	Expondo um Bean que configura um componente do OpenAPI
	@Bean
	 public OpenAPI customOpenAPI() {
	   return new OpenAPI()
	          .components(new Components()
	          .addSecuritySchemes("bearer-key",
	          new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}


}
