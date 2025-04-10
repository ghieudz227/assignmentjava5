package edu.poly.assjava5banhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("edu.poly.assjava5banhang.dao")
public class Assjava5banhangApplication {

	public static void main(String[] args) {
		SpringApplication.run(Assjava5banhangApplication.class, args);
	}

}
