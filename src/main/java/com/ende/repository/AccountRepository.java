package com.ende.repository;

import org.springframework.data.repository.CrudRepository;

import com.ende.domain.Account;

public interface  AccountRepository  extends CrudRepository<Account, Long>{
	Account findByUsernameAndPassword(String username, String password);
	
	Account findByUsername(String username);

}
