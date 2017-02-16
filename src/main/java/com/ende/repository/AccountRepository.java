package com.ende.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ende.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	Account findByUsernameAndPassword(String username, String password);

	Account findByUsername(String username);

	@Modifying
	@Query("UPDATE Account c SET c.password = :password WHERE c.username = :username")
	int updatePassword(@Param("username") String username, @Param("password") String password);

}
