package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

//Classe criada responsável por interceptar as requisições e realizar o processo de autenticação e autorização
@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository userRepository;

//	É realizado a autorização pelo cabeçalho 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Recuperando token
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			
//			Chama o método do tokenService e verifica se o token está no cabeçalho
			var subject = tokenService.getSubject(tokenJWT);
			
//			Considera que a pessoa está logado e token está valido
			var user = userRepository.findByLogin(subject);
			
//			DTO que representa o usuário
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					
//			Autenticando o usuário nessa requisição
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

//	Verificando se o cabeçalho tem o cabeçalho Authorization
	private String recuperarToken(HttpServletRequest request) {
//	Pegando o cabeçalho
	var authorizationHeader = request.getHeader("Authorization");
	if(authorizationHeader != null) {
		return authorizationHeader.replace("Bearer ", "").trim();
	}
	return null;
}

}
