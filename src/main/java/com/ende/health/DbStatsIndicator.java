package com.ende.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ende.repository.AccountRepository;
import com.ende.repository.MeatFoodRepository;
import com.ende.repository.MilkFoodRepository;

public class DbStatsIndicator implements HealthIndicator {
	
	private CrudRepository repository;
	   
	public DbStatsIndicator(CrudRepository rep) {
		  this.repository = rep;
	}


	@Override
	  public Health health() {
	      try {
	          long count = repository.count();
	          if (count >= 0) {
	              return Health.up().withDetail("count", count).build();
	          } else {
	              return Health.unknown().withDetail("count", count).build();
	          }
	      } catch (Exception e) {
	          return Health.down(e).build();
	      }
  	  }
}
