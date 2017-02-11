package com.ende.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ende.domain.Account;
import com.ende.domain.SecurityUser;

//@Service
@Component
public class AuthorityUserDetailsService implements UserDetailsService {

	@Autowired  //数据库服务类
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account a = accountService.findAccountByUsername(username);
		if(null == a)
			throw new UsernameNotFoundException("UserName " + username + " not found");
		return new SecurityUser(a);
	}

}
