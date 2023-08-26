package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;

//Classe que concentra as configurações de segurança
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private SecurityFilter securityFilter;
	
//	Método que retorna um método do Spring que configura processo de autenticação
//	Desabilitando o csrf de segurança, pois a autenticação de Token já traz essa proteção
//	Trazendo a autenticação para o projeto Statelees, já que estamos utilizando uma Api Rest para se comunicar fora do JAVA
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.
		csrf(csrf->csrf.disable()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().
		 authorizeHttpRequests((authz) -> {authz
	                .requestMatchers(HttpMethod.POST, "/login").permitAll()
	                .requestMatchers("/v3/api-docs/**", "swagger-ui.html", "/swagger-ui/**").permitAll()
	                .anyRequest().authenticated();
		 }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	        
	}
	
//	Classe responsável por saber criar um authentication Manager
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
//	Método que hash da senha vão ser criptografadas por BCrypt
	@Bean
	public PasswordEncoder password() {
		return new BCryptPasswordEncoder();
	}
}
