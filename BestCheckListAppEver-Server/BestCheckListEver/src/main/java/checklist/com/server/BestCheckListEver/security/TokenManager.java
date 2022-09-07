package checklist.com.server.BestCheckListEver.security;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import checklist.com.server.BestCheckListEver.services.*;

public interface TokenManager {

	String createAccessToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

	String createRefreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

	void refreshAccessToken(HttpServletRequest request, HttpServletResponse response, UserService userService)
			throws IOException;
}
