package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {
	
//	Passando a chave secreta que está no application.properties
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
//		Fazendo um algoritimo com uma chave secreta para gerar um token
		try {
		    Algorithm algoritimo = Algorithm.HMAC256(secret);
		    String token = JWT.create()
		        .withIssuer("API Voll Med")
//		        Identificando o login
		        .withSubject(usuario.getLogin())
//		        Retornando o id do usuário
		        .withClaim("id", usuario.getId())
//		        Configurando o token do usuário
		        .withExpiresAt(dataExpires())
		        .sign(algoritimo);
		    return token;
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro ao gerar o token", exception);
			
		}
	}

//	Adicionando duas horas no Local atual com GMT do Brasil
	private Instant dataExpires() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
