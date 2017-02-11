package com.ende.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ende.domain.MilkFood;

public interface MilkFoodRepository extends CrudRepository<MilkFood, Long>, JpaSpecificationExecutor<MilkFood>{
	Page<MilkFood> findByAccountid(Long aid, Pageable pageable);  
}
