package com.ende.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ende.domain.Contactor;

public interface ContactorRepository  extends CrudRepository<Contactor, Long>{
	
	List<Contactor> findByAccountid(Long aid);
	Contactor findByIsdefault(boolean isdefault);
}
