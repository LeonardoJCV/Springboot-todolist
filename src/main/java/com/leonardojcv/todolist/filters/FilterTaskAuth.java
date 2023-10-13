package com.leonardojcv.todolist.filters;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leonardojcv.todolist.repositories.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var servletPath = request.getServletPath();
		//servletPath começa com /tasks
		if (servletPath.startsWith("/tasks")) {
			// Pega a autenticação (Usuario e senha)
			var authorization = request.getHeader("Authorization");
			// Como a authorization gera a palavra "Basic" é necessário remove-la através do
			// substring().
			var authEncoded = authorization.substring("Basic".length()).trim();
			// Decode do base64
			byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
			// Conversão do array de bytes para String
			var authString = new String(authDecoded);
			// A String é gerada da seguinte forma usuario:senha, então é necessario o split
			// (em array para acessar as posições).
			String[] credentials = authString.split(":");
			String username = credentials[0];
			String password = credentials[1];

			// Valida usúario
			var user = this.userRepository.findByUsername(username);
			if (user == null) {
				response.sendError(401);
			} else {
				// Valida senha
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
				if (passwordVerify.verified) {
					// Atributo "idUser" com o valor do user.getId() para envio do parametro para o controller.
					request.setAttribute("idUser", user.getId());
					filterChain.doFilter(request, response);
				} else {
					response.sendError(401);
				}

			}
		}
		// Segue viagem
		else {
			filterChain.doFilter(request, response);
		}
	}
}
