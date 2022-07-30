package com.prototypo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MsgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator routers(RouteLocatorBuilder builder) {
		return builder
				.routes()
					//configurando as urls para os loand balance e o serviço específico da url
					.route(r -> r.path("/clientes/**").uri("lb://msclientes")) // <url de acesso > e nome dado no application.yml do serviço
					.route(r -> r.path("/cartoes/**").uri("lb://mscartoes")) 
					.route(r -> r.path("/avaliacoes-credito/**").uri("lb://msavaliadorcredito")) 
				.build();
	}
}
