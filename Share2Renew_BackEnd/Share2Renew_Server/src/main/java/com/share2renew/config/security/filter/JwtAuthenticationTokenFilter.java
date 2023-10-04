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
	 * FilterChain是Servlet API中的一个接口，它代表了一个过滤器链。在Java的web应用中，请求和响应可以经过一系列的过滤器，每一个过滤器可以对请求或响应做一些处理，然后传递给下一个过滤器，直到最终到达目标的Servlet或JSP页面。这种模式的好处是它提供了一种模块化和可重用的方式来处理请求和响应。
	 *
	 * FilterChain的主要职责是管理并执行这些过滤器。当一个过滤器完成其任务后，它会调用filterChain.doFilter(request, response)来传递请求和响应给链中的下一个过滤器或最终的Servlet/JSP。
	 *
	 * 简单地说，每当你在doFilter方法中调用filterChain.doFilter，你都是在说：“我已经完成了我的工作，现在让下一个过滤器或Servlet来处理这个请求。”
	 *
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