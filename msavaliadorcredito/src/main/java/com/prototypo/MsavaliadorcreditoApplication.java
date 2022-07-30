package com.prototypo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients // essa dependencia habilita o projeto que atravez do openfeign  para realizar a com sincrona com outros serviços
// que ira scanear e chamar os metodos que possuam a anotação EFeingClients
@EnableRabbit
public class MsavaliadorcreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsavaliadorcreditoApplication.class, args);
	}

}
