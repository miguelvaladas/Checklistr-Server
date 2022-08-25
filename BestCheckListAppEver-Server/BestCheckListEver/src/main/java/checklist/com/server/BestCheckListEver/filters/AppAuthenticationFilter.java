package checklist.com.server.BestCheckListEver.filters;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import java.util.Date;
import java.util.stream.Collectors;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.context.annotation.Bean;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter { 

	private final AuthenticationManager authenticationManager;

	public AppAuthenticationFilter (AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}

	@Override 
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{ 
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
	return authenticationManager.authenticate(authenticationToken);
	} 

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException{
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("mychecklistsecret".getBytes());
		String access_token = JWT.create()
			.withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
			.withIssuer(request.getRequestURL().toString())
			.withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.sign(algorithm);

		//Não meti claims porque por agora ainda não estou a usar ROLES diferentes.
		String refresh_token = JWT.create()
			.withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
			.withIssuer(request.getRequestURL().toString())
			.sign(algorithm);
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}

} 
