package com.share2renew.config.security;

import com.share2renew.config.security.filter.JwtAuthenticationTokenFilter;
import com.share2renew.config.security.filter.RestAuthorizationEntryPoint;
import com.share2renew.config.security.filter.RestfulAccessDeniedHandler;
import com.share2renew.pojo.User;
import com.share2renew.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: Share2Renew_BackEnd
 * @description: For the configuration of Spring Security
 * @author: Junxian Cai
 **/

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUserService userService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    //执行自己重写的UserDetailsService，通过getUserByUserName获取用户名
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //Override UserDetailsService method
        return userName -> {
            User user = userService.getUserByUserName(userName);
            if (null != user) {
                return user;
            }
            return null;
        };
    }

    /**
     * Spring Security configuration
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Using JWT, no need for csrf.
        http.csrf()
                .disable()
                //It's based on token, no need for the session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .cacheControl();

        // Add jwt. login authorisation filter(JWT登陆授权器)
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // Add custom unauthorised and return for login results
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    //放行路径
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/register",
                "/css/**",
                "/js/**",
                "/index.html",
                "/doc.html",
                "/webjars/**",
                "/doc.html",
                "/webjars/**",
                "/localhost:8085/doc.html",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
         return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
