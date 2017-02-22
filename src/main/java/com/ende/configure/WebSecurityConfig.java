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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
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
		auth.userDetailsService(authorityUserDetailsService);
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
		http.authorizeRequests().antMatchers("/","/index","/upload/","/upload/**","/verifyCode/**","/login","/personinfo",
				"/register", "/resetpwd","/food/search*", "/food/meatList", "/food/milkList", "/food/view*","/css/**", "/js/**", "/img/**").permitAll()
		//.antMatchers("/do**").hasRole("USER") 
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(new RecordUrlLoginAuthenticationEntryPoint("/login"))
		.and().formLogin().loginPage("/login").successHandler(new RedirectAuthenticationSuccessHandler()).failureUrl("/login?error").permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index").permitAll().invalidateHttpSession(true)
		.and().rememberMe().tokenValiditySeconds(1209600).tokenRepository(tokenRepository())
		.and().csrf().disable();
	}

	private PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl imp = new JdbcTokenRepositoryImpl();
		imp.setDataSource(dataSource);
		return imp;
	} 
}
