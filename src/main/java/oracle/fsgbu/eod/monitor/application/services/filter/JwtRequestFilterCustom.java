package oracle.fsgbu.eod.monitor.application.services.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import oracle.fsgbu.eod.monitor.application.services.service.CustomUserDetailsService;
import oracle.fsgbu.eod.monitor.application.services.utils.security.JwtUtils;

@Component
public class JwtRequestFilterCustom extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(JwtRequestFilterCustom.class);
	
	private void debug(String msg) {
		log.debug(this.getClass().getName()+"-->"+msg);
		System.out.println(this.getClass().getName()+"-->"+msg);
	}
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		final String requestUsernameHeader = request.getHeader("username");
		String username = null;
		String jwtToken = null;

		debug("Authorization:"+request.getHeader("Authorization"));
		debug("Username:"+request.getHeader("username"));
		debug("Origin:"+request.getHeader("Origin"));	
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = this.jwtUtils.getUsernameFromToken(jwtToken);
				if (null == requestUsernameHeader || !username.equalsIgnoreCase(requestUsernameHeader)) {
					throw new UsernameNotFoundException(
							"Username in request header is null or token passed does not belong to the user id "
									+ requestUsernameHeader);
				}
			} catch (IllegalArgumentException e) {
				debug("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				debug("JWT Token has expired");
			} catch (UsernameNotFoundException e) {
				debug(e.getMessage());
				username=null;
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
			debug("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (this.jwtUtils.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, username, Content-Type, Accept, X-Requested-With, remember-me");

	    
		filterChain.doFilter(request, response);
	}

}
