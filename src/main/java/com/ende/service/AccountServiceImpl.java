package com.ende.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ende.domain.Account;
import com.ende.domain.Contactor;
import com.ende.domain.SecurityUser;
import com.ende.form.AccountForm;
import com.ende.repository.AccountRepository;
import com.ende.repository.ContactorRepository;
import com.ende.util.PasswordEncoderUtil;
@Component("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;
	private final ContactorRepository contactorRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository, ContactorRepository contactorRepository) {
		super();
		this.accountRepository = accountRepository;
		this.contactorRepository = contactorRepository;
	}


	@Override
	public Long saveAccount(AccountForm af) {
		 String pwd = PasswordEncoderUtil.encodePassWord(af.getPassword());
		 Account a = new Account(af.getUsername(), pwd);
		 Timestamp t = new Timestamp(System.currentTimeMillis());
		 a.setCreatetime(t);
		 
		 a = this.accountRepository.save(a);
		 return a.getId();
	}
	
	@Override
	public Account findAccountById(Long id) {
		return this.accountRepository.findOne(id);
	}
	
	@Override
	public Account findAccountByUsernameAndPassword(String username, String password) {
		return this.accountRepository.findByUsernameAndPassword(username, password);
	}


	@Override
	public Account findAccountByUsername(String username) {
		return this.accountRepository.findByUsername(username);
	}
	
	@Override
	public List<Contactor> findContactors(Long accountId) {
		return this.contactorRepository.findByAccountid(accountId);
	}

	@Override
	public void saveContactor(Contactor c) {
	}

	@Override
	public void deleteContactorById(Long id) {

	}

	@Override
	public void setDefaultContactor(Long aid, Long c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 try{
			SecurityUser u = (SecurityUser)auth.getPrincipal() ;
			return u.getId();
		 }catch (java.lang.ClassCastException e) {
			 return 0L;
		}
		
	}

}
