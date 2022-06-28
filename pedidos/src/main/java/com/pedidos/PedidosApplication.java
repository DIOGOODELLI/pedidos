package com.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"com.pedidos"}) 
@ComponentScan(basePackages = {"com.pedidos"}) 
@EnableJpaRepositories(basePackages = {"com.pedidos"}) 
@EnableAutoConfiguration
@EnableScheduling
public class PedidosApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}
}




