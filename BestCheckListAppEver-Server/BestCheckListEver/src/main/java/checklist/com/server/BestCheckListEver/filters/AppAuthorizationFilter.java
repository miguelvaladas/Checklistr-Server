package checklist.com.server.BestCheckListEver.filters;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.*;
import javax.servlet.ServletException;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppAuthorizationFilter extends OncePerRequestFilter{


	private Collection<SimpleGrantedAuthority> getAuthoritiesFromToken(DecodedJWT decodedJWT){
			Collection<SimpleGrantedAuthority> collection = new ArrayList<>();
			collection.add(new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString()));
			return collection;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException {
		if(request.getServletPath().equals("/login")){
			filterChain.doFilter(request, response);

		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);

			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("mychecklistsecret".getBytes());

					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						decodedJWT.getSubject(),
						null,
						getAuthoritiesFromToken(decodedJWT));

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
		
				} catch (Exception e) {
					response.setHeader("error", e.getMessage());
					response.setStatus(FORBIDDEN.value());

					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());

					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}


			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
