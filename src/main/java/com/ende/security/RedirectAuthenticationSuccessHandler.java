package com.ende.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ende.domain.SecurityUser;

@Component
public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SecurityUser u = (SecurityUser)authentication.getPrincipal() ;
		request.getSession().setAttribute("username", u.getUsername());
		String f = request.getParameter("mobile");
        if (!StringUtils.isEmpty(f)) {
            if(f.equals("android")){
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Hi");
            }
            
        }else{
			String ru = (String)request.getSession().getAttribute("BACK_URL");
	        request.getSession().removeAttribute("BACK_URL");
	        if(!StringUtils.isEmpty(ru)){
	            response.sendRedirect(ru);
	            //request.getRequestDispatcher(ru).forward(request, response);
	        }else{
	        	 response.sendRedirect("/personinfo");
	            //request.getRequestDispatcher("/hello").forward(request, response);
	        }

	}
}

}
