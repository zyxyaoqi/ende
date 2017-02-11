package com.ende.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.ende.domain.MeatFood;
import com.ende.domain.MilkFood;
import com.ende.form.MeatFoodCriteria;
import com.ende.form.MilkFoodCriteria;

public interface FoodService {
	
	Page<MeatFood> searchMeatFood(MeatFoodCriteria mfc, Pageable pageable);
	Page<MilkFood> searchMilkFood(MilkFoodCriteria mfc, Pageable page);
	MeatFood save(MeatFood mf);
	MilkFood save(MilkFood mf);

	boolean deleteMeat(Long id);
	boolean deleteMilk(Long id);
	
	Page<MeatFood> findMeatFoodByAccountId(Long id,  Pageable pageable);
	Page<MilkFood> findMilkFoodByAccountId(Long id,  Pageable pageable);
}
