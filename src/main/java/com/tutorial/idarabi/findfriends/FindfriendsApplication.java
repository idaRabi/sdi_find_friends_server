package com.tutorial.idarabi.findfriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableCaching
@EnableMongoAuditing
@SpringBootApplication
public class FindfriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindfriendsApplication.class, args);
	}

}
