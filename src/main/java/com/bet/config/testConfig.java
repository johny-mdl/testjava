package com.bet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bet.services.DBService;

@Configuration
@Profile("test")
public class testConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instatiateDatabase() {

		dbService.instantiateTestDatabase();
		return true;
	}

}
