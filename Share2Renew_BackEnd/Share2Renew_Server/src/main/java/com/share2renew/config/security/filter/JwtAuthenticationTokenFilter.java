package com.share2renew.config.security.filter;

import com.share2renew.config.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: Share2Renew_BackEnd
 * @description: JWT filter
 * @author: Junxian Cai
 **/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.tokenHeader}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Resource
	private JwtTokenUtil jwtTokenUtil;
	@Resource
	private UserDetailsService userDetailsService;

	/**
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(tokenHeader);
		// Token exist
		if (null != authHeader && authHeader.startsWith(tokenHead)) {
			String authToken = authHeader.substring(tokenHead.length());
			String username = jwtTokenUtil.getUserNameFromToken(authToken);
			// Token - Username exists but not logged in
			if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
				// Login
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// Verify that the token is valid and reset the user object(一定要设置到全局上下文中)
				if (jwtTokenUtil.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken =
							//null的是密码(凭证)
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}

		}
		filterChain.doFilter(request, response);
	}
}