package ca.sheridancollege.pate3275.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//import from Spring security core
		Authentication auth = SecurityContextHolder
				.getContext().getAuthentication();

		if(auth != null) {
			System.out.println(auth.getName()
					+ " was trying to access resource: "
					+request.getRequestURI());
		}
		//return "redirect:/access-denied"
		response.sendRedirect(request.getContextPath()+"/access-denied");
		
	}

}
