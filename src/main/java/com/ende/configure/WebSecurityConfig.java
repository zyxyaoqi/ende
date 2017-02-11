package com.ende.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ende.security.RecordUrlLoginAuthenticationEntryPoint;
import com.ende.security.RedirectAuthenticationSuccessHandler;
import com.ende.service.AuthorityUserDetailsService;
import com.ende.util.PasswordEncoderUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
	
 	@Autowired
	private AuthorityUserDetailsService authorityUserDetailsService;
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
		auth.authenticationProvider(customAuthenticationProvider());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderUtil.getPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider customAuthenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(authorityUserDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/index","/upload/","/upload/**","/sendVerifyCode","/login","/personinfo","/register", "/food/search*", "/food/meatList", "/food/milkList", "/css/**", "/js/**", "/img/**").permitAll()
		//.antMatchers("/do**").hasRole("USER") 
		.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(new RecordUrlLoginAuthenticationEntryPoint("/login"))
		.and().formLogin().loginPage("/login").successHandler(new RedirectAuthenticationSuccessHandler()).failureUrl("/login?error") 
		.permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().and().csrf().disable();
	} 
}
