package checklist.com.server.BestCheckListEver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import javax.servlet.http.*;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.stream.Collectors;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class TokenManagerImpl implements TokenManager {

	public String createAccessToken(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("mychecklistsecret".getBytes());
		return JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("role",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
	}

	public String createRefreshToken(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("mychecklistsecret".getBytes());

		return JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("role",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
	}

	public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response, UserService userService)
			throws IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("mychecklistsecret".getBytes());

				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();

				AppUser user = userService.getByName(username);
				List<Role> userRoles = new ArrayList<>();
				userRoles.add(user.getRole());

				String access_token = JWT.create().withSubject(user.getName())
						.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("role", userRoles.stream().map(Role::toString).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());

				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());

				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		}
	}

}
