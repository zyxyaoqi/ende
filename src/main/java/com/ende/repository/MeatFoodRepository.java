package com.ende.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ende.domain.MeatFood;
 

public interface MeatFoodRepository extends CrudRepository<MeatFood, Long>, JpaSpecificationExecutor<MeatFood>{
	Page<MeatFood> findByAccountid(Long aid, Pageable pageable);
	
}
