package com.ende.configure;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import com.ende.health.DbStatsIndicator;
@Configuration
public class DBStatsConfiguration {
	@Autowired
	private HealthAggregator healthAggregator;
	@Bean
	public HealthIndicator dbStatsIndicator(Collection<CrudRepository> repositories) {
	  CompositeHealthIndicator compositeHealthIndicator = new
	          CompositeHealthIndicator(healthAggregator);
	  for (CrudRepository repository: repositories) {
	      String name = getRepositoryName(repository.getClass());
	      compositeHealthIndicator.addHealthIndicator(name, new DbStatsIndicator(repository));
	  }
	  return compositeHealthIndicator;
	}
	
	
	private static String getRepositoryName(Class crudRepositoryClass) {
        for (Class repositoryInterface : crudRepositoryClass.getInterfaces()) {
            if (repositoryInterface.getName().startsWith("com.ende.repository")) {
                return repositoryInterface.getSimpleName();
            }
        }
        return "UnknownRepository";
    }
}
