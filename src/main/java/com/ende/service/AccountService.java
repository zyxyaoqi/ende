package com.ende.service;

import java.util.List;

import com.ende.domain.Account;
import com.ende.domain.Contactor;
import com.ende.form.AccountForm;

public interface AccountService {
	
	Long saveAccount(AccountForm a);
	Account findAccountById(Long id);	
	Account findAccountByUsername(String username);	
	Account findAccountByUsernameAndPassword(String username, String password);
	
	void saveContactor(Contactor c);
	void deleteContactorById(Long id);
	void setDefaultContactor(Long aid, Long c);
	List<Contactor> findContactors(Long accountId);
	
	Long getCurrentUser();
	
	int resetPassword(String username, String pwd);
}
