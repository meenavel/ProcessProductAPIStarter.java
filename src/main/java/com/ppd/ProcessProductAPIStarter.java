package com.ppd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableScheduling
public class ProcessProductAPIStarter {

	public static void main(String[] args) {

		SpringApplication.run(ProcessProductAPIStarter.class, args);
	}

}
